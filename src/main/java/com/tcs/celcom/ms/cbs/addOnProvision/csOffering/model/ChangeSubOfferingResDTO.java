package com.tcs.celcom.ms.cbs.addOnProvision.csOffering.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tcs.celcom.ms.cbs.addOnProvision.model.RequestErrorDTO;

public class ChangeSubOfferingResDTO {

	@JsonInclude(Include.NON_NULL)
	private ChangeSubOfferingResponse changeSubOfferingResponse;

	@JsonInclude(Include.NON_NULL)
	private RequestErrorDTO reqError;

	public RequestErrorDTO getReqError() {
		return reqError;
	}

	public void setReqError(RequestErrorDTO reqError) {
		this.reqError = reqError;
	}

	public ChangeSubOfferingResponse getChangeSubOfferingResponse() {
		return changeSubOfferingResponse;
	}

	public void setChangeSubOfferingResponse(ChangeSubOfferingResponse changeSubOfferingResponse) {
		this.changeSubOfferingResponse = changeSubOfferingResponse;
	}

}
