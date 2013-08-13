package it.univaq.mwt.j2ee.kmZero.common;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateEditor extends PropertyEditorSupport {
	
	public void setAsText(String value) {
        try {
            setValue(new SimpleDateFormat("dd/MM/yyyy").parse(value));
        	//System.out.println("Metodo set Binder" + new SimpleDateFormat("dd/MM/yyyy").parse(value));
        } catch(ParseException e) {
            setValue(null);
        }
    }

    public String getAsText() {
    	String s = "";
    	if (getValue() != null) {
			s = new SimpleDateFormat("dd/MM/yyyy").format((Date) getValue());
        	//System.out.println("Metodo get Binder return " + s);
		}
    	return s;
    }

}