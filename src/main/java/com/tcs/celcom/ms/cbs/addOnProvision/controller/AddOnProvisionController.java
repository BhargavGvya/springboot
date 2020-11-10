package com.tcs.celcom.ms.cbs.addOnProvision.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tcs.celcom.ms.cbs.addOnProvision.exception.AddOnProvisionException;
import com.tcs.celcom.ms.cbs.addOnProvision.exception.ChangeSubOfferingServiceException;
import com.tcs.celcom.ms.cbs.addOnProvision.exception.IntegrationEnquiryException;
import com.tcs.celcom.ms.cbs.addOnProvision.exception.InvalidInputException;
import com.tcs.celcom.ms.cbs.addOnProvision.model.AddOnProvisionRequest;
import com.tcs.celcom.ms.cbs.addOnProvision.model.AddOnProvisionResponse;
import com.tcs.celcom.ms.cbs.addOnProvision.model.AddOnProvisionResponseDTO;
import com.tcs.celcom.ms.cbs.addOnProvision.model.ExceptionModel;
import com.tcs.celcom.ms.cbs.addOnProvision.model.ResponseModel;
import com.tcs.celcom.ms.cbs.addOnProvision.service.AddOnProvisionService;
import com.tcs.celcom.ms.cbs.addOnProvision.util.Constants;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class AddOnProvisionController {
	
	@Autowired
	AddOnProvisionService service;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
				
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = AddOnProvisionResponseDTO.class, message = "OK - Request successfully executed"),
			@ApiResponse(code = 500, response = ExceptionModel.class, message = "Internal Server Error"),
			@ApiResponse(code = 400, response = ExceptionModel.class, message = "Bad Request"),
			@ApiResponse(code = 503, response = ExceptionModel.class, message = "Service Unavailable")})
	@HystrixCommand(fallbackMethod = "callFallBackMethod", commandProperties = {
			@HystrixProperty(name = Constants.HYSTRIX_PROPERTY_TIMEOUT_THREAD, value = Constants.REQ_TIMEOUT_LIMIT)}, ignoreExceptions = {
					ChangeSubOfferingServiceException.class,IntegrationEnquiryException.class,InvalidInputException.class,Exception.class })
    @RequestMapping(method=RequestMethod.POST, value="/AddOnProvision")	
	public ResponseEntity<? extends ResponseModel> InvokeAddOnProvision(@RequestBody AddOnProvisionRequest request) throws Exception
	{
    	if(logger.isInfoEnabled())
    		logger.info("AddOnProvisionController - InvokeAddOnProvision");
    	
		AddOnProvisionResponseDTO response = new AddOnProvisionResponseDTO();
		AddOnProvisionResponse res = new AddOnProvisionResponse();
		HttpStatus status = null;
		
		if(isValidInput(request))
		{
			if(logger.isInfoEnabled())
	    		logger.info("AddOnProvisionController - input Validation Success");
			try
			{
				res = service.invokeProvision(request);
				status = HttpStatus.OK;
			}
			catch(ChangeSubOfferingServiceException | IntegrationEnquiryException ex)
			{
				if(ex instanceof ChangeSubOfferingServiceException)
					throw new ChangeSubOfferingServiceException(ex.getMessage());
				else
					throw new IntegrationEnquiryException(ex.getMessage());
			}			
		}
		else
		{
			if(logger.isInfoEnabled())
	    		logger.info("AddOnProvisionController - input validation failed");
			
			status = HttpStatus.BAD_REQUEST;
			throw new InvalidInputException("Invalid Input");
		}
		
		response.setAddOnProvisionresponse(res);
		
		return new ResponseEntity<AddOnProvisionResponseDTO>(response,status);
	}

	private boolean isValidInput(AddOnProvisionRequest request) {
		if(request.getMsisdn()!= null && !request.getMsisdn().isEmpty()
				&&(request.getAction()!=null && !request.getAction().isEmpty())
				&&(request.getOfferingId()!=null && !request.getOfferingId().isEmpty()))
			return true;
		else
			return false;
			
	}
	
	public ResponseEntity<? extends ResponseModel> callFallBackMethod(@RequestBody AddOnProvisionRequest request) throws AddOnProvisionException
	{
		logger.error("AddOnProvisionController Fall Back Method invoked");
		throw new AddOnProvisionException(Constants.STATUS_DESC_REQ_TIMEOUT);
	}
	

}
