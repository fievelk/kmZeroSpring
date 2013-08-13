package it.univaq.mwt.j2ee.kmZero.common;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

// Classe utilizzata per la serializzazione dei prezzi in Json (con l'annotazione @JsonSerialize in Product)
public class PriceJsonSerializer extends JsonSerializer<BigDecimal> {
	
	  @Override
	  public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
	    jgen.writeString(value.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
	  }
}