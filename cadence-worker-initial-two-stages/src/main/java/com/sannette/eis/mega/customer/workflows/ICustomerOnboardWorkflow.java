package com.sannette.eis.mega.customer.workflows;

import com.sannette.eis.mega.common.ServiceConstants;
import com.sannette.eis.mega.customer.models.Customer;
import com.uber.cadence.workflow.QueryMethod;
import com.uber.cadence.workflow.SignalMethod;
import com.uber.cadence.workflow.WorkflowMethod;

public interface ICustomerOnboardWorkflow {

    @WorkflowMethod(executionStartToCloseTimeoutSeconds = 10, taskList = ServiceConstants.GREETING_TASK_LIST)
    String onboardCustomer(Customer customer);

    @SignalMethod
    void approveCustomerOnboard();

    @SignalMethod
    void rejectCustomerOnboard();

    @QueryMethod
    String getCustomerOnboardingStatus();
}
