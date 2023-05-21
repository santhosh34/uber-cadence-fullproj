package com.sannette.eis.mega.customer.starters;

import com.sannette.eis.mega.common.ServiceConstants;
import com.sannette.eis.mega.customer.models.Customer;
import com.sannette.eis.mega.customer.workflows.ICustomerOnboardWorkflow;
import com.uber.cadence.WorkflowExecution;
import com.uber.cadence.client.DuplicateWorkflowException;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.client.WorkflowOptions;
import com.uber.cadence.internal.compatibility.Thrift2ProtoAdapter;
import com.uber.cadence.internal.compatibility.proto.serviceclient.IGrpcServiceStubs;
import org.apache.commons.lang.math.RandomUtils;

import java.time.Duration;

import static com.sannette.eis.mega.common.ServiceConstants.DOMAIN;

public class CustomerOnboardWorkflowStarter {
    public static void main(String[] args) {
// Grpc Model
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new Thrift2ProtoAdapter(IGrpcServiceStubs.newInstance()),
                        WorkflowClientOptions.newBuilder().setDomain(DOMAIN).build());
// TChannnel Model
//        final WorkflowClient workflowClient =
//                WorkflowClient.newInstance(
//                        new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
//                        WorkflowClientOptions.newBuilder().setDomain(DOMAIN).build());
        int id = RandomUtils.nextInt(1)+701;

        Customer customer = Customer.builder().id(id).build();
        // 1. Let's try with Duplicate Allow
        WorkflowOptions workflowOptions =
                new WorkflowOptions.Builder()
                        .setTaskList(ServiceConstants.CUSTOMER_ONBOARDING_TASK_LIST)
                        .setWorkflowId("customer-"+(customer.getId()))
                        .setExecutionStartToCloseTimeout(Duration.ofDays(100))
                        .build();
        ICustomerOnboardWorkflow customerOnboardWorkflow =
                workflowClient.newWorkflowStub(ICustomerOnboardWorkflow.class, workflowOptions);
        try {
            WorkflowExecution execution = WorkflowClient.start(customerOnboardWorkflow::onboardCustomer, customer);
            System.out.println("started workflow execution" + execution);
        }catch(DuplicateWorkflowException e){
            // Case1 ... Just to get the workflow status ....
            /**
            WorkflowExecution execution = new WorkflowExecution().setWorkflowId("customer-"+customer.getId());
            ICustomerOnboardWorkflow workflow = workflowClient.newWorkflowStub(ICustomerOnboardWorkflow.class, execution.workflowId);
            String result = workflow.onboardCustomer(customer);
            System.out.println(result);
             */
            // Case 2 : Lets Say workflow changed by adding one more step.... and see how that goes : Not working As Expected
//            WorkflowExecution execution = new WorkflowExecution().setWorkflowId("customer-"+customer.getId());
//            customerOnboardWorkflow = workflowClient.newWorkflowStub(ICustomerOnboardWorkflow.class, execution.workflowId);
//            execution = WorkflowClient.execute(customerOnboardWorkflow::onboardCustomer, customer);
//            System.out.println(execution);
        }
        System.exit(0);

    }
}
