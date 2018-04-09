package com.vg.controller;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import com.vg.model.SubscriptionDetails;
import com.vg.validator.SubscriptionValidatorTest;

/**
 * 
 * @author VG
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionControllerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionValidatorTest.class);
	
	SubscriptionDetails subscriptionDetails;
	BindingResult br;

	@Autowired
	SubscriptionController subscriptionController;

	@Before
	public void setUp() {
		subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setAmountPerInvoice("20");
		subscriptionDetails.setFrequency("daily");
		subscriptionDetails.setStartDate("06/02/2018");
		subscriptionDetails.setEndDate("06/03/2018");

		br = new DataBinder(this.subscriptionDetails).getBindingResult();
	}

	@Test
	public void testSubscription() {
		String response = subscriptionController.subscribe(subscriptionDetails, br);

		LOGGER.info("Daily Invoices : {} ", response);
	}

	@Test
	public void testSubscriptionDailyWithDateError() {
		subscriptionDetails.setEndDate("06/03/2019");
		String response = subscriptionController.subscribe(subscriptionDetails, br);

		//LOGGER.info(response);
		Assert.assertThat(
				response,
				CoreMatchers.containsString("start and end date of the entire subscription should be of a maximum duration of 3 months"));
	}

	@Test
	public void testSubscriptionMontly() {
		subscriptionDetails.setFrequency("monthly");
		subscriptionDetails.setOccurrence("20");

		subscriptionDetails.setStartDate("01/02/2018");
		subscriptionDetails.setEndDate("01/05/2018");

		String response = subscriptionController.subscribe(subscriptionDetails, br);

		LOGGER.info("Montly Invoices : {} ", response);

	}

	@Test
	public void testSubscriptionMontlyWithNoOccurrence() {
		subscriptionDetails.setFrequency("monthly");
		subscriptionDetails.setOccurrence(null);

		subscriptionDetails.setStartDate("01/02/2018");
		subscriptionDetails.setEndDate("01/05/2018");

		String response = subscriptionController.subscribe(subscriptionDetails, br);

		//LOGGER.info(response);
		Assert.assertThat(response, CoreMatchers.containsString("field.required"));

	}

	@Test
	public void testSubscriptionWeekly() {
		subscriptionDetails.setFrequency("weekly");
		subscriptionDetails.setOccurrence("TUESDAY");

		String response = subscriptionController.subscribe(subscriptionDetails, br);
		LOGGER.info("Weekly Invoices : {} ", response);

	}

}
