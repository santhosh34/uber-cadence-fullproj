package com.sannette.eis.mega.customer.starters;

import com.sannette.eis.mega.customer.models.Customer;
import com.sannette.eis.mega.customer.workflows.ICustomerOnboardWorkflow;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import org.apache.commons.lang.math.RandomUtils;

import static com.sannette.eis.mega.common.ServiceConstants.DOMAIN;

public class CustomerOnboardWorkflowSignaler {
    public static void main(String[] args) {

        final WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
                        WorkflowClientOptions.newBuilder().setDomain(DOMAIN).build());
        int id = RandomUtils.nextInt(1)+701;
        Customer customer = Customer.builder().id(id).build();
        // 1. Let's try with Duplicate Allow
//        WorkflowOptions workflowOptions =
//                new WorkflowOptions.Builder()
//                        .setTaskList(ServiceConstants.CUSTOMER_ONBOARDING_TASK_LIST)
//                        .setWorkflowId("customer-"+(customer.getId()))
//                        .build();
//        ICustomerOnboardWorkflow customerOnboardWorkflow =
//                workflowClient.newWorkflowStub(ICustomerOnboardWorkflow.class, workflowOptions);

        // Just Need to send signal from a different process... thats all
        ICustomerOnboardWorkflow customerOnboardWorkflow =
                workflowClient.newWorkflowStub(ICustomerOnboardWorkflow.class, "customer-"+(customer.getId()));
        customerOnboardWorkflow.approveCustomerOnboard();

        System.exit(0);

    }
}
