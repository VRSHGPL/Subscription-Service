package com.vg.model;

/**
 * 
 * @author VG
 *
 */
public class SubscriptionDetails {

	private String amountPerInvoice;
	private String frequency;
	private String occurrence;
	private String startDate;
	private String endDate;

	public String getAmountPerInvoice() {
		return amountPerInvoice;
	}

	public void setAmountPerInvoice(String amountPerInvoice) {
		this.amountPerInvoice = amountPerInvoice;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(String occurrence) {
		this.occurrence = occurrence;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
