package com.tcs.celcom.ms.cbs.addOnProvision.csOffering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ChangeSubOfferingResponse {

	private String resultCode;
	private String resultDesc;
	//@JsonInclude(Include.NON_NULL)
	private String purchaseSeq;
	
	/**
	 * @return the purchaseSeq
	 */
	public String getPurchaseSeq() {
		return purchaseSeq;
	}
	/**
	 * @param purchaseSeq the purchaseSeq to set
	 */
	public void setPurchaseSeq(String purchaseSeq) {
		this.purchaseSeq = purchaseSeq;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
}
