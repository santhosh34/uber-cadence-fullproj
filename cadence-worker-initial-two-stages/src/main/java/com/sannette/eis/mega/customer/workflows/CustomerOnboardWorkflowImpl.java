package com.sannette.eis.mega.customer.workflows;

import com.sannette.eis.mega.customer.activities.ICustomerOnboardActivities;
import com.sannette.eis.mega.customer.models.Customer;
import com.uber.cadence.workflow.Workflow;

public class CustomerOnboardWorkflowImpl implements ICustomerOnboardWorkflow {

    private Customer customer;

    private final ICustomerOnboardActivities activities =
            Workflow.newActivityStub(ICustomerOnboardActivities.class);

    @Override
    public String onboardCustomer(Customer customer) {
        System.out.println("Worker started Activity");
        this.customer = customer;

        this.customer = activities.draftCustomerOnboard(customer);
        System.out.println("Till here its fine");
        this.customer = activities.review1CustomerOnboard(customer);
        // activities.approveCustomerOnboard(customer);
        System.out.println("Worker will pause Activity at next statement until it gets some signal");

//        Workflow.await(()->{
//            return "APPROVED".equalsIgnoreCase(this.customer.getStatus());
//        });

        Workflow.await(() -> "APPROVED".equalsIgnoreCase(this.customer.getStatus()));

        this.customer = activities.sendWelcomeEmail(customer);

        System.out.println("-----------------------------------");

        return "customer kaaka id: " + this.customer.getId() + ":Status:" + this.customer.getStatus();
    }

    @Override
    public void approveCustomerOnboard() {
        this.customer.setStatus("APPROVED");
    }

    @Override
    public void rejectCustomerOnboard() {
        this.customer.setStatus("REJECTED");
    }

    @Override
    public String getCustomerOnboardingStatus() {
        return this.customer.getStatus();
    }
}
