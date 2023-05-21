package com.sannette.eis.mega.greeting.workflows;


import com.sannette.eis.mega.greeting.activities.IGreetingActivities;
import com.uber.cadence.workflow.Workflow;

public class GreetingWorkflowImpl implements  IGreetingWorkflow{

    private final IGreetingActivities activities =
            Workflow.newActivityStub(IGreetingActivities.class);

    @Override
    public String getGreeting(String name) {
        // This is a blocking call that returns only after the activity has completed.
        String result = activities.composeGreeting("hellp",name);
        System.out.println(result);
        return result; //activities.composeGreeting("Hello", name);
    }

}
