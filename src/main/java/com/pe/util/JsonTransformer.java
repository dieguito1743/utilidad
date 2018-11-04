/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pe.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.interfaces.IJsonTransformer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 *
 * @author dbermudez
 */
@Component(UtilDefines.INSTANCE_JSONTRANSFORMER)
public class JsonTransformer implements IJsonTransformer {

    public JsonTransformer() {
    }

    @Override
    public String toJson(Object data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        } // terminar control de errores
    }

    @Override
    public <Clase> Clase fromJSON(String json, Class<Clase> clase) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(json, clase);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } // terminar control de errores
    }

    @Override
    public <Clase> List<Clase> fromListJSON(String json, Class<Clase> clase) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clase));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}