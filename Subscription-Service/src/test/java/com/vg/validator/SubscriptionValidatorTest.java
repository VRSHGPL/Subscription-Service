package com.vg.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.vg.model.SubscriptionDetails;

/**
 * 
 * @author VG
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionValidatorTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionValidatorTest.class);
	Errors errors;
	SubscriptionDetails subscriptionDetails;

	@Autowired
	private SubscriptionValidator subscriptionValidator;

	@Before
	public void setUp() {
		subscriptionDetails = new SubscriptionDetails();
		subscriptionDetails.setAmountPerInvoice("20");
		subscriptionDetails.setFrequency("daily");
		subscriptionDetails.setStartDate("06/02/2018");
		subscriptionDetails.setEndDate("12/03/2018");

		errors = new BeanPropertyBindingResult(subscriptionDetails, "subscriptionDetails");
	}

	@Test
	public void testValidation() {
		subscriptionValidator.validate(subscriptionDetails, errors);

		Assert.assertEquals(false, errors.hasErrors());

	}

	@Test
	public void testValidationWithErrors() {
		subscriptionDetails.setFrequency("junk");
		subscriptionDetails.setStartDate("06/02/2018");
		subscriptionDetails.setEndDate("12/09/2018");
		subscriptionValidator.validate(subscriptionDetails, errors);

		Assert.assertEquals(true, errors.hasErrors());

		errors.getAllErrors().forEach(
				item -> LOGGER.info("Error Code: {} ; Error Cause: {}  ", item.getCode(), item.getDefaultMessage()));

	}
}
