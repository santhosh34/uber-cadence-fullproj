package com.sannette.eis.mega.workflows;

import com.sannette.eis.mega.common.ServiceConstants;
import com.uber.cadence.workflow.WorkflowMethod;

public interface IGreetingWorkflow {

//    static final String TASK_LIST = "HelloActivity";
    @WorkflowMethod(executionStartToCloseTimeoutSeconds = 10, taskList = ServiceConstants.TASK_LIST)
    String getGreeting(String name);
}
