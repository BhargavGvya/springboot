package com.tcs.celcom.ms.cbs.addOnProvision.util;

public interface Constants {
	
	String INVALID_INPUT_DESC = "Invalid input request, msisdn, offeringId and action are mandatory fields, please try with valid request";
	String INVALID_INPUT_STATUS_CODE = "SVC0001";
	String CHANGE_SUB_OFFER_EXEC_STATUS_CODE = "SVC0002";
	String INTEGRATION_ENQUIRY_EXEC_STATUS_CODE = "SVC0003";
	String HYSTRIX_TIMEOUT_STATUS_CODE = "SVC0004";
	String EXEC_STATUS_CODE = "SVC0005";
	String INT_ENQ_VALIDATION_FAILED_DESC = "Validation failed - Customer not eligible to purchase this offering";
	String HYSTRIX_PROPERTY_TIMEOUT_THREAD = "execution.isolation.thread.timeoutInMilliseconds";
	String REQ_TIMEOUT_LIMIT = "10000";
	String STATUS_DESC_REQ_TIMEOUT = "The service could be temporarily unavailable or too busy. Try again in a few moments.";
	
}
