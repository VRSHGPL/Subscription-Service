package com.vg.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.joda.time.DateTime;

/**
 * 
 * @author VG
 *
 */
public class Utility {

	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static LocalDate toLocalDate(String dateString, SimpleDateFormat format) throws ParseException {
		Date date = format.parse(dateString);
		DateTime dateTime = new DateTime(date);
		return LocalDate.of(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
	}

}
