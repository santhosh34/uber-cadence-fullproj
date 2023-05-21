package com.sannette.eis.mega.customer.activities;

import com.sannette.eis.mega.customer.models.Customer;

public class CustomerOnboardActivitiesImpl implements ICustomerOnboardActivities{
    @Override
    public Customer draftCustomerOnboard(Customer customer) {
        customer.setName(customer.getName());
        customer.setStatus("Draft");// Later please remove.
        System.out.println("Customer in Draft Status by Activity:draftCustomerOnboard:"+customer.getId());
        return customer;
    }

    @Override
    public Customer review1CustomerOnboard(Customer customer) {

        customer.setStatus("Reviewed1");// Later please remove.
        customer.setReviewComment("Need to correct grammatical Mistakes");
        System.out.println("Customer in Review Done Status by Activity:reviewCustomerOnboard:"+customer.getId());
        return customer;
    }

    @Override
    public Customer review2CustomerOnboard(Customer customer) {
        customer.setStatus("Reviewed2");// Later please remove.
        customer.setReviewComment("Need to correct grammatical Mistakes");
        System.out.println("Customer in Review2 Done Status by Activity:review2CustomerOnboard:"+customer.getId());
        return customer;
    }

    @Override
    public Customer sendWelcomeEmail(Customer customer) {
        customer.setStatus("Onboarded");
        return customer;
    }
}
