package com.sannette.eis.mega.test;

import com.sannette.eis.mega.common.ServiceConstants;
import com.sannette.eis.mega.workflowactivities.GreetingActivitiesImpl;
import com.sannette.eis.mega.workflows.GreetingWorkflowImpl;
import com.sannette.eis.mega.workflows.IGreetingWorkflow;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.internal.compatibility.Thrift2ProtoAdapter;
import com.uber.cadence.internal.compatibility.proto.serviceclient.IGrpcServiceStubs;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;

import static com.sannette.eis.mega.common.ServiceConstants.DOMAIN;

public class Tester {



    public static void main(String[] args) {
        // Get a new client
        // NOTE: to set a different options, you can do like this:
        // ClientOptions.newBuilder().setRpcTimeout(5 * 1000).build();
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new Thrift2ProtoAdapter(IGrpcServiceStubs.newInstance()),
                        WorkflowClientOptions.newBuilder().setDomain(DOMAIN).build());
        // Get worker to poll the task list.
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(ServiceConstants.TASK_LIST);
        // Workflows are stateful. So you need a type to create instances.
        worker.registerWorkflowImplementationTypes(GreetingWorkflowImpl.class);
        // Activities are stateless and thread safe. So a shared instance is used.
        worker.registerActivitiesImplementations(new GreetingActivitiesImpl());
        // Start listening to the workflow and activity task lists.
        factory.start();

        // Get a workflow stub using the same task list the worker uses.
        IGreetingWorkflow workflow = workflowClient.newWorkflowStub(IGreetingWorkflow.class);
        // Execute a workflow waiting for it to complete.
        String greeting = workflow.getGreeting("World1234");
        System.out.println(greeting);
        System.exit(0);
    }
}
