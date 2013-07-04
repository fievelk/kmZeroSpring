package it.univaq.mwt.j2ee.kmZero.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;


public class StringToDate implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		System.out.println("Sono nel metodo convert in stringtodate");
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
}
