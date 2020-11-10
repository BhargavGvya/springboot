package com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntegrationEnquiryResDTO {
	
	@JsonInclude(Include.NON_NULL)
	private ResponseStatusDTO responseStatus;
	@JsonInclude(Include.NON_NULL)
	private List<BalanceRecord> balanceRecordList = new ArrayList<BalanceRecord>();
	@JsonInclude(Include.NON_NULL)
	private SubscriberState subscriberState;
	@JsonInclude(Include.NON_NULL)
	private BillingCycleDate billingCycleDate;
	@JsonInclude(Include.NON_NULL)
	private SubscriberInfo subscriberInfo;
	@JsonInclude(Include.NON_NULL)
	private List<CumulativeItem> cumulativeItemList = new ArrayList<CumulativeItem>();
	@JsonInclude(Include.NON_NULL)
	private SubAttachedInfo subAttachedInfo;

}
