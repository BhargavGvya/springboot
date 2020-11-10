package com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class BalanceRecord {

	@JsonInclude(Include.NON_NULL)
	private String accountKey;
	@JsonInclude(Include.NON_NULL)
	private String accountType;
	@JsonInclude(Include.NON_NULL)
	private Long balance;
	@JsonInclude(Include.NON_NULL)
	private String balanceDesc;
	@JsonInclude(Include.NON_NULL)
	private String expireTime;
	@JsonInclude(Include.NON_NULL)
	private Integer minMeasureId;
	@JsonInclude(Include.NON_NULL)
	private Integer unitType;
	@JsonInclude(Include.NON_NULL)
	private String productID;
}
