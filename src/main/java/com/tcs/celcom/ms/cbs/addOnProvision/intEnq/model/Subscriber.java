package com.tcs.celcom.ms.cbs.addOnProvision.intEnq.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class Subscriber {

	@JsonInclude(Include.NON_NULL)
	private String code;
	@JsonInclude(Include.NON_NULL)
	private String brandId;
	@JsonInclude(Include.NON_NULL)
	private String registrationTime;
	@JsonInclude(Include.NON_NULL)
	private String lang;
	@JsonInclude(Include.NON_NULL)
	private String smsLang;
	@JsonInclude(Include.NON_NULL)
	private String paidMode;
	@JsonInclude(Include.NON_NULL)
	private String belToAreaID;
	@JsonInclude(Include.NON_NULL)
	private String mainProductID;
	@JsonInclude(Include.NON_NULL)
	private List<SimpleProperty> simpleProperty;
	@JsonInclude(Include.NON_NULL)
	private String IMSI;
}
