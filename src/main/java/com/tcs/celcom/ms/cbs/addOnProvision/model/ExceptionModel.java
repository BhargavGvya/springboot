package com.tcs.celcom.ms.cbs.addOnProvision.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionModel implements ResponseModel{

	@JsonInclude(Include.NON_NULL)
	private RequestErrorDTO requestError;
}
