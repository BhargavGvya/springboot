package com.tcs.celcom.ms.cbs.addOnProvision.service;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tcs.celcom.ms.cbs.addOnProvision.csOffering.model.ChangeSubOfferingRequest;
import com.tcs.celcom.ms.cbs.addOnProvision.csOffering.model.ChangeSubOfferingRequestDTO;
import com.tcs.celcom.ms.cbs.addOnProvision.csOffering.model.ChangeSubOfferingResDTO;
import com.tcs.celcom.ms.cbs.addOnProvision.exception.ChangeSubOfferingServiceException;
import com.tcs.celcom.ms.cbs.addOnProvision.exception.IntegrationEnquiryException;
import com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model.IntegrationEnquiryReqDTO;
import com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model.IntegrationEnquiryResDTO;
import com.tcs.celcom.ms.cbs.addOnProvision.model.AddOnProvisionRequest;
import com.tcs.celcom.ms.cbs.addOnProvision.model.AddOnProvisionResponse;
import com.tcs.celcom.ms.cbs.addOnProvision.util.Constants;

/*
 * Invokes IntegrationEnquiry
 * Validates IntegrationEnquiry response
 * If validation successful, invoke changeSubOffering
 * If validation failed, throw customer not eligible exception
 */

@Service
public class AddOnProvisionService {
	
	static Map<String, String> speedBoosterEligibleOfferings = null;
	static Map<String, String> hotspotEligibleOfferings = null;
	static List<String> speedBoosterOfferings = null;
	static List<String> hotspotOfferings = null;
	
	RestTemplate restTemplate = new RestTemplate(); 
	
	IntegrationEnquiryResDTO integrationEnquiryResponse = null;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${integration_enquiry_endpoint}")
	String integration_enquiry_endpoint;
	
	@Value("${change_sub_offering_endpoint}")
	String changeSubOfferingEndpoint;
	
	@Value("${integration_enquiry_context}")
	String integration_enquiry_context;
	
	@Value("${change_sub_offering_context}")
	String changeSubOfferingContext;
	
	@Autowired
	DateUtil dateUtility;
	
	public AddOnProvisionResponse invokeProvision(AddOnProvisionRequest request) throws Exception
	{
		if(logger.isInfoEnabled())
    		logger.info("AddOnProvisionService - invokeProvision");
		AddOnProvisionResponse response = new AddOnProvisionResponse();
		
		integrationEnquiryResponse = new IntegrationEnquiryResDTO();
		
		try
		{
			integrationEnquiryResponse = invokeIntegrationEnquiry(request.getMsisdn());
//			double price = Double.parseDouble(request.getPrice());
			
			if(Validate(integrationEnquiryResponse,request.getOfferingId())) 
			{
				if(logger.isInfoEnabled())
		    		logger.info("AddOnProvisionService - integrationEnquiryResponse Validation Successful");
				ChangeSubOfferingResDTO changeSubOfferingResponse = new ChangeSubOfferingResDTO();
				changeSubOfferingResponse = invokeChangeSubOffering(request, integrationEnquiryResponse);
				response.setResultCode(changeSubOfferingResponse.getChangeSubOfferingResponse().getResultCode());
				response.setResultDescription(changeSubOfferingResponse.getChangeSubOfferingResponse().getResultDesc());
			}
			else
			{
				logger.error("AddOnProvisionService - integrationEnquiryResponse Validation Failed");
				throw new IntegrationEnquiryException(Constants.INT_ENQ_VALIDATION_FAILED_DESC);
			}
		}
		catch(ChangeSubOfferingServiceException | IntegrationEnquiryException ex)
		{
			logger.error("AddOnProvisionService - catch block :"+ex.getMessage());
			if(ex instanceof ChangeSubOfferingServiceException)
				throw new ChangeSubOfferingServiceException(ex.getMessage());
			else
				throw new IntegrationEnquiryException(ex.getMessage());
		}	
		catch(Exception e)
		{
			logger.error("AddOnProvisionService - catch block :"+e.getMessage());
			throw new Exception(e.getMessage());
		}
		return response;
	}
	
	private ChangeSubOfferingResDTO invokeChangeSubOffering(AddOnProvisionRequest request,
			IntegrationEnquiryResDTO integrationEnquiryResponse2) throws ChangeSubOfferingServiceException{
		
		if(logger.isInfoEnabled())
    		logger.info("AddOnProvisionService - invokeChangeSubOffering method invoked");
 
		ChangeSubOfferingResDTO response = new ChangeSubOfferingResDTO();
		ChangeSubOfferingRequestDTO csRequest = new ChangeSubOfferingRequestDTO();
		ChangeSubOfferingRequest req = new ChangeSubOfferingRequest();
		
		String effectiveTime = dateUtility.getEffectiveTime(request.getOfferingId(),integrationEnquiryResponse2);
		String endDateTime = dateUtility.getEndDateTime(request.getOfferingId(),integrationEnquiryResponse2);
		
		req.setAction(request.getAction());
		req.setAdjustmentCode(request.getAdjustmentCode());
		req.setOfferingId(request.getOfferingId());
		req.setServiceNumber(request.getMsisdn());
		if(effectiveTime!=null)
			req.setEffectiveTime(effectiveTime);
		if(endDateTime!=null)
			req.setEndDate(endDateTime);
		else
			req.setEndDate(request.getEndDate());
		csRequest.setChangeSubOfferingRequest(req);
		try
		{
			response = restTemplate.postForObject(URI.create(changeSubOfferingEndpoint+changeSubOfferingContext), csRequest, ChangeSubOfferingResDTO.class);
		}
		catch (Exception e) {
			logger.error("invokeChangeSubOffering - catch block : "+e.getMessage());
			throw new ChangeSubOfferingServiceException(e.getMessage());
		}
		
		return response;
	}

	
	private boolean Validate(IntegrationEnquiryResDTO integrationEnquiryResponse2, String offeringId) {

		if(logger.isInfoEnabled())
    		logger.info("AddOnProvisionService - Validating integrationEnquiryResponse");
		boolean offeringIdCheck = false;
//		long mainBalance = integrationEnquiryResponse2.getBalanceRecordList().stream().filter(record -> record.getBalanceDesc().contentEquals("PPS_MainAccount")).mapToLong(record -> record.getBalance()).sum();
		
		if(speedBoosterOfferings.contains(offeringId))
		{
			for(Map.Entry<String, String> entry: speedBoosterEligibleOfferings.entrySet())
			{
				if(!offeringIdCheck)
					offeringIdCheck = integrationEnquiryResponse2.getSubscriberInfo().getProduct().stream().anyMatch(product -> product.getId().contentEquals(entry.getKey()));
			}
		}
		else if(hotspotOfferings.contains(offeringId))
		{
			for(Map.Entry<String, String> entry: hotspotEligibleOfferings.entrySet())
			{
				if(!offeringIdCheck)
					offeringIdCheck = integrationEnquiryResponse2.getSubscriberInfo().getProduct().stream().anyMatch(product -> product.getId().contentEquals(entry.getKey()));
			}
		}
		else
			offeringIdCheck = false;
			
		System.out.println("offeringIdCheck :"+offeringIdCheck);
		if(offeringIdCheck)
		{
			return true;
		}
		else
			return false;
	}

	private IntegrationEnquiryResDTO invokeIntegrationEnquiry(String msisdn) throws IntegrationEnquiryException {
		
		if(logger.isInfoEnabled())
    		logger.info("AddOnProvisionService - invokeIntegrationEnquiry method invoked");
		
		IntegrationEnquiryReqDTO request = new IntegrationEnquiryReqDTO();
		IntegrationEnquiryResDTO response = new IntegrationEnquiryResDTO();
		
		try
		{
			request.setSubscriberNo(msisdn);
			response = restTemplate.postForObject(URI.create(integration_enquiry_endpoint+integration_enquiry_context), request, IntegrationEnquiryResDTO.class);	
		}
		catch(Exception e)
		{
			logger.error("invokeIntegrationEnquiry - catch block : "+e.getMessage());
			throw new IntegrationEnquiryException(e.getMessage());
		}
		
		return response;
	}

	@PostConstruct
	public void init()
	{
		speedBoosterEligibleOfferings = new HashMap<String, String>();
		hotspotEligibleOfferings = new HashMap<String, String>();
		
		/*
		 * Speed Booster Eligible Offerings
		 * IntegrationEnquiryResponse should contain at least one of the below offerings
		 * else customer not eligible for speed booster products
		 */
		
		speedBoosterEligibleOfferings.put("2060102", "3Mbps MI Red RM12");
		speedBoosterEligibleOfferings.put("2060103", "3Mbps MI Red RM35");
		speedBoosterEligibleOfferings.put("2060158", "3Mbps MI Red RM12 FOC");
		speedBoosterEligibleOfferings.put("2060160", "3Mbps MI Red RM35 FOC");
		
		/*
		 * Hotspot Eligible Offerings
		 * IntegrationEnquiryResponse should contain at least one of the below offerings
		 * else customer not eligible for hotspot products
		 */
		
		hotspotEligibleOfferings.put("2060102","3Mbps MI Red RM12"); 			 
		hotspotEligibleOfferings.put("2060103","3Mbps MI Red RM35"); 			
		hotspotEligibleOfferings.put("2060158","3Mbps MI Red RM12 FOC"); 		
		hotspotEligibleOfferings.put("2060160","3Mbps MI Red RM35 FOC"); 		
		hotspotEligibleOfferings.put("2060163","3Mbps Just4ME Unlimited RM12");
		hotspotEligibleOfferings.put("2060164","3Mbps Just4ME Unlimited RM35"); 
		hotspotEligibleOfferings.put("2060084","3Mbps MI Diamond RM12"); 		
		hotspotEligibleOfferings.put("2060085","3Mbps MI Diamond RM38"); 		
		hotspotEligibleOfferings.put("2060086","3Mbps MI Diamond RM2"); 		 
		hotspotEligibleOfferings.put("2060087","3Mbps MI Diamond RM5"); 		 
		hotspotEligibleOfferings.put("2060208","3Mbps MI Diamond RM12 FOC");    
		hotspotEligibleOfferings.put("2060210","3Mbps MI Diamond RM38 FOC");    
		hotspotEligibleOfferings.put("2060100","1.5 Mbps MI Revive");
		
		/*
		 * Speed Booster offerings
		 */
		speedBoosterOfferings = Arrays.asList("2060239","2060240","2060241","2060242","2060243");
		
		/*
		 * Hotspot offerings
		 */
		hotspotOfferings = Arrays.asList("2060104","2060105","2060244","2060245","2060246");
	}
}
