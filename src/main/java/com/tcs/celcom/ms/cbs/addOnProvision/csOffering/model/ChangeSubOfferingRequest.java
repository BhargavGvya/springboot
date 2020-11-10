package com.tcs.celcom.ms.cbs.addOnProvision.csOffering.model;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeSubOfferingRequest {

	private String action;
	private String offeringId;
	private String serviceNumber;
	private String adjustmentCode;
	private List<CampaignAttributes> listOfAttributes;
	private String purchaseSeq;
	private String endDate;
	private String effectiveTime;
		
}
