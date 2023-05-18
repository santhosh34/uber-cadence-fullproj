package com.sannette.eis.mega.workflows;


import com.sannette.eis.mega.workflowactivities.IGreetingActivities;
import com.uber.cadence.workflow.Workflow;

public class GreetingWorkflowImpl implements  IGreetingWorkflow{

    private final IGreetingActivities activities =
            Workflow.newActivityStub(IGreetingActivities.class);

    @Override
    public String getGreeting(String name) {
        // This is a blocking call that returns only after the activity has completed.
        return activities.composeGreeting("Hello", name);
    }

}
