package com.vg.controller;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.vg.model.InvoiceDetails;
import com.vg.model.SubscriptionDetails;
import com.vg.service.SubscriptionService;
import com.vg.validator.SubscriptionValidator;

/**
 * 
 * @author VG
 *
 */
@RestController
public class SubscriptionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

	@Autowired
	private SubscriptionValidator subscriptionValidator;

	@Autowired
	private SubscriptionService subscriptionService;

	/**
	 * 
	 * @param subscriptionDetails
	 * @param errors
	 * @return
	 */
	@PostMapping("/subscribe") 
	@ResponseBody
	public String subscribe(SubscriptionDetails subscriptionDetails, BindingResult errors) {
		LOGGER.info(
				"In /subscribe, Subscription Details are :- amountPerInvoice : {}, frequency : {},  occurrence: {}, startDate: {}, endDate: {} ",
				subscriptionDetails.getAmountPerInvoice(), subscriptionDetails.getFrequency(),
				subscriptionDetails.getOccurrence(), subscriptionDetails.getStartDate(),
				subscriptionDetails.getEndDate());

		Gson gson = new Gson();

		subscriptionValidator.validate(subscriptionDetails, errors);
		if (errors.hasErrors()) {
			return gson.toJson(gson.toJson(errors.getAllErrors()));
		}

		try {
			InvoiceDetails details = subscriptionService.generateInvoicesForSubscription(subscriptionDetails);
			return gson.toJson(details);

		} catch (ParseException e) {
			LOGGER.error("", e);
			return null;
		}

	}
}
