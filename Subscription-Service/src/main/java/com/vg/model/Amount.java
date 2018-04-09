package com.vg.model;

import java.io.Serializable;

/**
 * 
 * @author VG
 *
 */
public class Amount implements Serializable {


	private static final long serialVersionUID = 1L;
	private String value;
	private String currency;

	public Amount(String value, String currency) {
		super();
		this.value = value;
		this.currency = currency;
	}

	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
