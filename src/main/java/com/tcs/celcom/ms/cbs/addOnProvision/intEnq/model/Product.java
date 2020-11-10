package com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Product {
	
	@JsonInclude(Include.NON_NULL)
	private String id;
	@JsonInclude(Include.NON_NULL)
	private String productOrderKey;
	@JsonInclude(Include.NON_NULL)
	private String effectiveDate;
	@JsonInclude(Include.NON_NULL)
	private String expiredDate;
	@JsonInclude(Include.NON_NULL)
	private Integer status;
	@JsonInclude(Include.NON_NULL)
	private String curCycleStartTime;
	@JsonInclude(Include.NON_NULL)
	private String curCycleEndTime;
	@JsonInclude(Include.NON_NULL)
	private Integer billStatus;

}
