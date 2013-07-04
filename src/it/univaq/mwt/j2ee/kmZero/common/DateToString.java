package it.univaq.mwt.j2ee.kmZero.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateToString  implements Converter<Date, String> {

	@Override
	public String convert(Date source) {
		String s = null;
		
		s = new SimpleDateFormat("dd/MM/yyyy").format((Date) source);
		return s;
	}

	
}
