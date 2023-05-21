package com.sannette.eis.mega.greeting.activities;



public class GreetingActivitiesImpl implements IGreetingActivities {
    @Override
    public String composeGreeting(String greeting, String name) {
        System.out.println("Debug this"+name);
        return greeting + " " + name + "!";
    }
}


