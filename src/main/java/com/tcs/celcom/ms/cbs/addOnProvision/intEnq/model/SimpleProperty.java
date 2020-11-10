package com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class SimpleProperty {
	
	@JsonInclude(Include.NON_NULL)
	private String id;
	@JsonInclude(Include.NON_NULL)
	private String value;

}
