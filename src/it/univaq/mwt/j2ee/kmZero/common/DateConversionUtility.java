package it.univaq.mwt.j2ee.kmZero.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConversionUtility {
	
	// Converte la Data dalla stringa presa dal datepicker all'oggetto Calendar
	public static Calendar stringToCalendar (String s){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(s));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	
	// Converte la Data da un oggetto Calendar ad una stringa dd/MM/yyyy
	public static String calendarDateToString (Calendar c){
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH) + 1;
		int year = c.get(Calendar.YEAR);
		String s = (day + "/" + month + "/" + year);
		return s;
	}
	
	// Converte la Data da un oggetto Calendar ad una stringa hh:mm:ss
	public static String calendarTimeToString (Calendar c){
		int hour = c.get(Calendar.HOUR);
		int minute = c.get(Calendar.MINUTE) + 1;
		int second = c.get(Calendar.SECOND);
		String s = (hour + ":" + minute + ":" + second);
		return s;
	}
	
	// Converte il Timestamp in un oggetto Calendar
	public static Calendar timestampToCalendar (java.sql.Timestamp ts){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(ts.getTime());
		return c;
	}
	
}
