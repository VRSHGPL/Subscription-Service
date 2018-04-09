package com.vg.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.vg.model.Amount;
import com.vg.model.InvoiceDetails;
import com.vg.model.SubscriptionDetails;
import com.vg.utils.Utility;

/**
 * 
 * @author VG
 *
 */
@Service
public class SubscriptionService {
	private static final String DD_MM_YYYY = "dd/MM/yyyy";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DD_MM_YYYY);
	
	/**
	 * 
	 * @param subscriptionDetails
	 * @return
	 * @throws ParseException
	 */
	public InvoiceDetails generateInvoicesForSubscription(SubscriptionDetails subscriptionDetails)
			throws ParseException {

		InvoiceDetails details = new InvoiceDetails();
		details.setId(UUID.randomUUID().toString());

		Amount amount = new Amount(subscriptionDetails.getAmountPerInvoice(), "AUD");
		details.setAmount(amount);

		details.setSubscriptionType(subscriptionDetails.getFrequency().toUpperCase());

		List<String> invoiceDates = getInvoiceDates(subscriptionDetails);
		details.setInvoiceDates(invoiceDates);
			
		return details;
	}

	/**
	 * 
	 * @param subscriptionDetails
	 * @return
	 * @throws ParseException
	 */
	private List<String> getInvoiceDates(SubscriptionDetails subscriptionDetails) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY);
		LocalDate startLocalDate = Utility.toLocalDate(subscriptionDetails.getStartDate(), format);
		LocalDate endLocalDate = Utility.toLocalDate(subscriptionDetails.getEndDate(), format);

		List<String> invoiceDates = new ArrayList<>();

		switch (subscriptionDetails.getFrequency()) {
		case "daily":

			invoiceDates = Stream.iterate(startLocalDate, date -> date.plusDays(1))
					.limit(ChronoUnit.DAYS.between(startLocalDate, endLocalDate) + 1)
					.map(e -> e.format(DATE_TIME_FORMATTER))
					.collect(Collectors.toList());

			break;
		case "weekly":

			LocalDate occurrenceDate = startLocalDate.with(TemporalAdjusters.nextOrSame(DayOfWeek
					.valueOf(subscriptionDetails.getOccurrence().toUpperCase())));

			while (occurrenceDate.isBefore(endLocalDate)) {
				invoiceDates.add(occurrenceDate.format(DATE_TIME_FORMATTER));
				occurrenceDate = occurrenceDate.with(TemporalAdjusters.next(DayOfWeek.valueOf(subscriptionDetails
						.getOccurrence().toUpperCase())));
			}
			break;
		case "monthly":
			invoiceDates = Stream.iterate(startLocalDate, date -> date.plusMonths(1))
					.limit(ChronoUnit.MONTHS.between(startLocalDate, endLocalDate) + 1)
					.map(e -> e.format(DATE_TIME_FORMATTER))
					.collect(Collectors.toList());

			break;

		}

		return invoiceDates;

	}

}
