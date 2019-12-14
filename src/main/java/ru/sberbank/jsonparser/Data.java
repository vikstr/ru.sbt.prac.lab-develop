package ru.sberbank.jsonparser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;

import java.io.IOException;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private Item[] data;

    public Item[] getData() { return this.data;}
    public void setData(Item[] data){ this.data = data;}

}
