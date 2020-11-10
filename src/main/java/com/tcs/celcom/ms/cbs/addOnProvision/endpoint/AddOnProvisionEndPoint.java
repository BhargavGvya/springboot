package com.tcs.celcom.ms.cbs.addOnProvision.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.tcs.celcom.ms.cbs.addOnProvision.exception.ChangeSubOfferingServiceException;
import com.tcs.celcom.ms.cbs.addOnProvision.exception.IntegrationEnquiryException;
import com.tcs.celcom.ms.cbs.addOnProvision.service.AddOnProvisionService;
import com.tcs.celcom.ms.cbs.addOnProvision.util.Constants;
import com.tcs.celcom.ms.soap.addOnProvision.model.AddOnProvisionRequest;
import com.tcs.celcom.ms.soap.addOnProvision.model.AddOnProvisionResponse;
import com.tcs.celcom.ms.soap.addOnProvision.model.Header;

@Endpoint
public class AddOnProvisionEndPoint {
	
	@Autowired
	AddOnProvisionService service;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String NAMESPACE_URI = "http://tcs.ms.soapservices/addonprovision";
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddOnProvisionRequest")
	@ResponsePayload
	public AddOnProvisionResponse invokeAddOnProvision(@RequestPayload AddOnProvisionRequest request) throws Exception
	{
		Header header = null;
		
		logger.info("AddOnProvisionEndPoint - invokeAddOnProvision");
			
		com.tcs.celcom.ms.cbs.addOnProvision.model.AddOnProvisionRequest restRequest = new com.tcs.celcom.ms.cbs.addOnProvision.model.AddOnProvisionRequest();
		
		com.tcs.celcom.ms.cbs.addOnProvision.model.AddOnProvisionResponse restResponse = new com.tcs.celcom.ms.cbs.addOnProvision.model.AddOnProvisionResponse();
		
		AddOnProvisionResponse response = new AddOnProvisionResponse();
		
		if(isValidInput(request))
		{
			logger.info("AddOnProvisionEndPoint - input validation successful");
			restRequest.setAction(request.getAction());
			restRequest.setAdjustmentCode(request.getAdjustmentCode());
			restRequest.setEndDate(request.getEndDate());
			restRequest.setMsisdn(request.getMsisdn());
			restRequest.setOfferingId(request.getOfferingId());
			restRequest.setSource(request.getSource());
			restRequest.setStartDate(request.getStartDate());
			restRequest.setTransactionId(request.getTransactionId());
			restRequest.setPrice(request.getPrice());
			
			try
			{
				restResponse = service.invokeProvision(restRequest);
				header = new Header();
				header.setResultCode(restResponse.getResultCode());
				header.setResultDescription(restResponse.getResultDescription());
			}
			catch(ChangeSubOfferingServiceException | IntegrationEnquiryException ex)
			{
				logger.info("AddOnProvisionEndPoint - in catch block "+ex.getMessage());
				header = new Header();
				if(ex instanceof ChangeSubOfferingServiceException)
					header.setResultCode(Constants.CHANGE_SUB_OFFER_EXEC_STATUS_CODE);
				else
					header.setResultCode(Constants.INTEGRATION_ENQUIRY_EXEC_STATUS_CODE);
				header.setResultDescription(ex.getMessage());
			}
			catch(Exception e)
			{
			  logger.info("AddOnProvisionEndPoint - in catch block "+e.getMessage());
			  header = new Header();
			  header.setResultCode(Constants.EXEC_STATUS_CODE);
			  header.setResultDescription(e.getMessage());
			}				
		}
		else
		{
			logger.error("AddOnProvisionEndPoint - input validation failed");
			header = new Header();
			header.setResultCode(Constants.INVALID_INPUT_STATUS_CODE);
			header.setResultDescription(Constants.INVALID_INPUT_DESC);			
		}
		response.setHeader(header);
		return response;
	}

	private boolean isValidInput(AddOnProvisionRequest request) {
		if(request.getMsisdn()!= null && !request.getMsisdn().isEmpty()
				&&(request.getAction()!=null && !request.getAction().isEmpty())
				&&(request.getOfferingId()!=null && !request.getOfferingId().isEmpty()))
			return true;
		else
			return false;
	}
}
