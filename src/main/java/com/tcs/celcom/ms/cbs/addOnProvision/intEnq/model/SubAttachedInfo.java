package com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubAttachedInfo {

	@JsonInclude(Include.NON_NULL)
	private Integer chgMainProductTimes;
	@JsonInclude(Include.NON_NULL)
	private Integer chgMainPackageTimes;
	@JsonInclude(Include.NON_NULL)
	private Long loanAmout;
	@JsonInclude(Include.NON_NULL)
	private Long loanPoundage;
	@JsonInclude(Include.NON_NULL)
	private Long etuReceiveAmt;
}
