package com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class SubscriberInfo {
	
	@JsonInclude(Include.NON_NULL)
	private Subscriber subscriber;
	@JsonInclude(Include.NON_NULL)
	private List<Product> product;

}
