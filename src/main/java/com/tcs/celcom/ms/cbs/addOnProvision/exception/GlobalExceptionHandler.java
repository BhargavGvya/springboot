package com.tcs.celcom.ms.cbs.addOnProvision.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.tcs.celcom.ms.cbs.addOnProvision.model.AddOnProvisionResponseDTO;
import com.tcs.celcom.ms.cbs.addOnProvision.model.ExceptionModel;
import com.tcs.celcom.ms.cbs.addOnProvision.model.RequestErrorDTO;
import com.tcs.celcom.ms.cbs.addOnProvision.model.ResponseModel;
import com.tcs.celcom.ms.cbs.addOnProvision.model.ServiceExceptionDTO;
import com.tcs.celcom.ms.cbs.addOnProvision.util.Constants;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	AddOnProvisionResponseDTO response = null;
	ServiceExceptionDTO serviceException = null;
	RequestErrorDTO requestError = null;

	@ExceptionHandler({InvalidInputException.class,ChangeSubOfferingServiceException.class,IntegrationEnquiryException.class,AddOnProvisionException.class,Exception.class})
	public ResponseEntity<? extends ResponseModel> handleAllExceptions(Exception ex, WebRequest request)
	{
		logger.error("Handling " + ex.getClass().getSimpleName() + " due to " + ex.getMessage());
		ExceptionModel execModel = new ExceptionModel();
		serviceException = new ServiceExceptionDTO();
		requestError = new RequestErrorDTO();
		response = new AddOnProvisionResponseDTO();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if(ex instanceof InvalidInputException)
		{
			serviceException.setMessageId(Constants.INVALID_INPUT_STATUS_CODE);
			serviceException.setText(Constants.INVALID_INPUT_DESC);
		}
		else if(ex instanceof ChangeSubOfferingServiceException)
		{
			serviceException.setMessageId(Constants.CHANGE_SUB_OFFER_EXEC_STATUS_CODE);
			serviceException.setText(ex.getMessage());
		}
		else if(ex instanceof IntegrationEnquiryException)
		{
			serviceException.setMessageId(Constants.INTEGRATION_ENQUIRY_EXEC_STATUS_CODE);
			serviceException.setText(ex.getMessage());
		}
		else if(ex instanceof AddOnProvisionException)
		{
			serviceException.setMessageId(Constants.HYSTRIX_TIMEOUT_STATUS_CODE);
			serviceException.setText(ex.getMessage());
		}
		else
		{
			serviceException.setMessageId(Constants.EXEC_STATUS_CODE);
			serviceException.setText(ex.getMessage());
		}
		requestError.setServiceException(serviceException);
		execModel.setRequestError(requestError);
		return new ResponseEntity<ExceptionModel>(execModel, status);		
	}

}
