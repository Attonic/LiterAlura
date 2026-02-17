package io.attonic.literalura.util;

import org.springframework.boot.json.JsonParseException;
import tools.jackson.databind.ObjectMapper;

public class ConvertDados {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T obterDados(String json, Class<T> tClass){
        try {
            return mapper.readValue(json, tClass);
        } catch (JsonParseException e) {
            throw new RuntimeException("Erro ao converter Json", e);
        }
    }

}
