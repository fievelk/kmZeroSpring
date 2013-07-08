package it.univaq.mwt.j2ee.kmZero.common;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


// Classe utilizzata per la serializzazione delle date in Json (con l'annotazione @JsonSerialize in Product)
public class DateJsonSerializer extends JsonSerializer<Date> {
	
	  @Override
	  public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
	    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	    jgen.writeString(formatter.format(value));
	  }
}

