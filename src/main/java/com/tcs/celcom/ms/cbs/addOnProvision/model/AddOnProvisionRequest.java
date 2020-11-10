package com.tcs.celcom.ms.cbs.addOnProvision.model;

import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddOnProvisionRequest {

    private String transactionId;
    private String msisdn;
    private String offeringId;
    private String action;
    private String source;
    private String adjustmentCode;
    private String price;
    private String startDate;
    private String endDate;
}
