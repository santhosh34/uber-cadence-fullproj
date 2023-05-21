package com.sannette.eis.mega.greeting.starters;

import com.sannette.eis.mega.greeting.workflows.IGreetingWorkflow;
import com.uber.cadence.WorkflowExecution;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.internal.compatibility.Thrift2ProtoAdapter;
import com.uber.cadence.internal.compatibility.proto.serviceclient.IGrpcServiceStubs;

import static com.sannette.eis.mega.common.ServiceConstants.DOMAIN;

public class GreetingWorkflowStarter {

    public static void main(String[] args) {
        // Get a new client
        // NOTE: to set a different options, you can do like this:
        // ClientOptions.newBuilder().setRpcTimeout(5 * 1000).build();
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new Thrift2ProtoAdapter(IGrpcServiceStubs.newInstance()),
                        WorkflowClientOptions.newBuilder().setDomain(DOMAIN).build());
        // Get a workflow stub using the same task list the worker uses.
        IGreetingWorkflow workflow = workflowClient.newWorkflowStub(IGreetingWorkflow.class);
        // Execute a workflow waiting for it to complete.

        WorkflowExecution executionResult = WorkflowClient.start(workflow::getGreeting, "World4321");
       // String greeting = workflow.getGreeting("World1234");
        System.out.println(executionResult);
    }
}
