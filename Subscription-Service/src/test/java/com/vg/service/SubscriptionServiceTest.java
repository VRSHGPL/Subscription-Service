package com.vg.service;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.vg.model.InvoiceDetails;
import com.vg.model.SubscriptionDetails;
import com.vg.validator.SubscriptionValidatorTest;

/**
 * 
 * @author VG
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionValidatorTest.class);

	@Autowired
	private SubscriptionService subscriptionService;

	SubscriptionDetails subscriptionDetails;

	@Before
	public void setUp() {
		subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setAmountPerInvoice("20");

		subscriptionDetails.setStartDate("01/02/2018");
		subscriptionDetails.setEndDate("01/03/2018");

	}

	@Test
	public void testDailyInvoicing() {
		subscriptionDetails.setFrequency("daily");
		try {
			InvoiceDetails details = subscriptionService.generateInvoicesForSubscription(subscriptionDetails);

			Gson gson = new Gson();
			LOGGER.info("Daily Invoices : {} ", gson.toJson(details));

		} catch (ParseException e) {
		}

	}

	@Test
	public void testWeeklyInvoicing() {
		subscriptionDetails.setFrequency("weekly");
		subscriptionDetails.setOccurrence("TUESDAY");
		try {
			InvoiceDetails details = subscriptionService.generateInvoicesForSubscription(subscriptionDetails);

			Gson gson = new Gson();
			LOGGER.info("Weekly Invoices : {} ", gson.toJson(details));

		} catch (ParseException e) {
		}

	}

	@Test
	public void testMontlyInvoicing() {
		subscriptionDetails.setFrequency("monthly");
		subscriptionDetails.setOccurrence("20");

		subscriptionDetails.setStartDate("01/02/2018");
		subscriptionDetails.setEndDate("01/05/2018");

		try {
			InvoiceDetails details = subscriptionService.generateInvoicesForSubscription(subscriptionDetails);

			Gson gson = new Gson();
			LOGGER.info("Montly Invoices : {} ", gson.toJson(details));

		} catch (ParseException e) {
		}

	}
}
