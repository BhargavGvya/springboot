package com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CumulativeItem {

	@JsonInclude(Include.NON_NULL)
	private String cumulateBeginTime;
	@JsonInclude(Include.NON_NULL)
	private String cumulateEndTime;
	@JsonInclude(Include.NON_NULL)
	private Integer cumulateID;
	@JsonInclude(Include.NON_NULL)
	private Long cumulativeAmt;
}
