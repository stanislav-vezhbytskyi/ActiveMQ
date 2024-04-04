package shpp.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PojoToJson {
    private ObjectMapper objectMapper;

    public PojoToJson() {
        this.objectMapper = new ObjectMapper();
    }
    public String pojoToJson(POJO pojo) throws JsonProcessingException {
        return objectMapper.writeValueAsString(pojo);
    }
}
