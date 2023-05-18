package com.sannette.eis.mega.workflowactivities;



public class GreetingActivitiesImpl implements IGreetingActivities {
    @Override
    public String composeGreeting(String greeting, String name) {
        return greeting + " " + name + "!";
    }
}


