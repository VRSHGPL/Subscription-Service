package com.vg.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.vg.model.SubscriptionDetails;
import com.vg.utils.Utility;

/**
 * 
 * @author VG
 *
 */

@Service
public class SubscriptionValidator implements Validator {
	private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionValidator.class);

	private static final String REGEX = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return SubscriptionDetails.class.isAssignableFrom(clazz);
	}

	/**
	 * Basic Validation of Subscription Details. Validation Messages are updated in Errors
	 */
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amountPerInvoice", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "frequency", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "field.required");

		SubscriptionDetails subscriptionDetails = (SubscriptionDetails) target;

		if (! (subscriptionDetails.getFrequency().equals("daily") || subscriptionDetails.getFrequency().equals("weekly")
				|| subscriptionDetails.getFrequency().equals("monthly"))) {
			errors.reject("frequency", "Incorrect Subscription Frequency specified.");
		}

		if (subscriptionDetails.getFrequency().equals("weekly") || subscriptionDetails.getFrequency().equals("monthly")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "occurrence", "field.required");
		}

		if (!isValidDate(subscriptionDetails.getStartDate())) {
			errors.reject("startDate", "Incorrect Date");
		}

		if (!isValidDate(subscriptionDetails.getEndDate())) {
			errors.reject("endDate", "Incorrect Date");
		}

		try {
			if (validateMonthDifference(subscriptionDetails)) {
				errors.reject("Dates",
						"start and end date of the entire subscription should be of a maximum duration of 3 months");
			}
		} catch (ParseException e) {
			LOGGER.error("Error Parsing Dates", e);
			errors.rejectValue("Date", "Incorrect Dates");
		}

	}

	/**
	 * Finds the number of months between two dates
	 * 
	 * @param subscriptionDetails
	 * @return
	 * @throws ParseException
	 */
	private boolean validateMonthDifference(SubscriptionDetails subscriptionDetails) throws ParseException {

		LocalDate startLocalDate = Utility.toLocalDate(subscriptionDetails.getStartDate(), FORMAT);
		LocalDate endLocalDate = Utility.toLocalDate(subscriptionDetails.getEndDate(), FORMAT);

		long monthDifference = ChronoUnit.MONTHS.between(startLocalDate, endLocalDate);

		LOGGER.debug("Month Difference between StartDate : {} EndDate: {} is {}", startLocalDate, endLocalDate,
				monthDifference);

		return monthDifference > 3;

	}

	/**
	 * Validates if a date matches the pattern specified
	 * 
	 * @param date
	 * @return
	 */
	private static boolean isValidDate(String date) {
		Pattern pattern = Pattern.compile(REGEX);
		return pattern.matcher(date).matches();
	}
}
