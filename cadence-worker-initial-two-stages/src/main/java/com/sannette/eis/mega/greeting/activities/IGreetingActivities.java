package com.sannette.eis.mega.greeting.activities;

import com.uber.cadence.activity.ActivityMethod;

public interface IGreetingActivities {

    @ActivityMethod(scheduleToCloseTimeoutSeconds = 2)
    String composeGreeting(String greeting, String name);
}
