package com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class ResponseStatusDTO {
	
	@JsonInclude(Include.NON_NULL)
	private String status;
	@JsonInclude(Include.NON_NULL)
	private String statusDesc;
	@JsonInclude(Include.NON_NULL)
	private String error;
	@JsonInclude(Include.NON_NULL)
	private String errorDesc;
	@JsonInclude(Include.NON_NULL)
	private String commandId;
	@JsonInclude(Include.NON_NULL)
	private String version;
	@JsonInclude(Include.NON_NULL)
	private String transactionId;
	@JsonInclude(Include.NON_NULL)
	private String sequenceId;
	@JsonInclude(Include.NON_NULL)
	private String resultCode;
	@JsonInclude(Include.NON_NULL)
	private String resultDesc;

}
