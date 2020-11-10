package com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class SubscriberState {

	@JsonInclude(Include.NON_NULL)
	private String activeStop;
	@JsonInclude(Include.NON_NULL)
	private String disableStop;
	@JsonInclude(Include.NON_NULL)
	private Integer dpFlag;
	@JsonInclude(Include.NON_NULL)
	private String firstActiveDate;
	@JsonInclude(Include.NON_NULL)
	private Integer fraudState;
	@JsonInclude(Include.NON_NULL)
	private Integer lifeCycleState;
	@JsonInclude(Include.NON_NULL)
	private Integer lockedFlag;
	@JsonInclude(Include.NON_NULL)
	private Integer lossFlag;
	@JsonInclude(Include.NON_NULL)
	private String suspendStop;
	@JsonInclude(Include.NON_NULL)
	private Integer etuFraudState;
	@JsonInclude(Include.NON_NULL)
	private Long oldbalance;
	@JsonInclude(Include.NON_NULL)
	private String registrationTime;
}
