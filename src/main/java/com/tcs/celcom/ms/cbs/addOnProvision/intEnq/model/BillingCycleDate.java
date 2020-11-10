package com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class BillingCycleDate {
	
	@JsonInclude(Include.NON_NULL)
	private String billCycleEndDate;
	@JsonInclude(Include.NON_NULL)
	private String billCycleOpenDate;
	@JsonInclude(Include.NON_NULL)
	private Integer billCycleType;

}
