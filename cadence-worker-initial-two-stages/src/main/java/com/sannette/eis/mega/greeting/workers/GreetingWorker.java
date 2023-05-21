package com.sannette.eis.mega.greeting.workers;

import com.sannette.eis.mega.greeting.activities.GreetingActivitiesImpl;
import com.sannette.eis.mega.common.ServiceConstants;
import com.sannette.eis.mega.greeting.workflows.GreetingWorkflowImpl;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.internal.compatibility.Thrift2ProtoAdapter;
import com.uber.cadence.internal.compatibility.proto.serviceclient.IGrpcServiceStubs;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;

import static com.sannette.eis.mega.common.ServiceConstants.DOMAIN;

public class GreetingWorker {
    public static void main(String[] args) {
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new Thrift2ProtoAdapter(IGrpcServiceStubs.newInstance()),
                        WorkflowClientOptions.newBuilder().setDomain(DOMAIN).build());
        // Get worker to poll the task list.
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(ServiceConstants.GREETING_TASK_LIST);
        // Workflows are stateful. So you need a type to create instances.
        worker.registerWorkflowImplementationTypes(GreetingWorkflowImpl.class);
        // Activities are stateless and thread safe. So a shared instance is used.
        worker.registerActivitiesImplementations(new GreetingActivitiesImpl());
        // Start listening to the workflow and activity task lists.
        factory.start();
    }
}
