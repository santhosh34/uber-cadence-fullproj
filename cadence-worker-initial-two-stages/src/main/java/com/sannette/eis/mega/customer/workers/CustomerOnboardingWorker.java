package com.sannette.eis.mega.customer.workers;

import com.sannette.eis.mega.common.ServiceConstants;
import com.sannette.eis.mega.customer.activities.CustomerOnboardActivitiesImpl;
import com.sannette.eis.mega.customer.workflows.CustomerOnboardWorkflowImpl;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.internal.compatibility.Thrift2ProtoAdapter;
import com.uber.cadence.internal.compatibility.proto.serviceclient.IGrpcServiceStubs;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;

import static com.sannette.eis.mega.common.ServiceConstants.DOMAIN;

public class CustomerOnboardingWorker {
    public static void main(String[] args) {
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new Thrift2ProtoAdapter(IGrpcServiceStubs.newInstance()),
                        WorkflowClientOptions.newBuilder().setDomain(DOMAIN).build());
        // Get worker to poll the task list.
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(ServiceConstants.CUSTOMER_ONBOARDING_TASK_LIST);
        // Workflows are stateful. So you need a type to create instances.
        worker.registerWorkflowImplementationTypes(CustomerOnboardWorkflowImpl.class);
        // Activities are stateless and thread safe. So a shared instance is used.
        worker.registerActivitiesImplementations(new CustomerOnboardActivitiesImpl());
        // Start listening to the workflow and activity task lists.
        factory.start();
    }
}
