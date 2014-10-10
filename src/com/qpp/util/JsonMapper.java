package com.qpp.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by SZ_it123 on 2014/9/25.
 */
public class JsonMapper {
    public static ObjectMapper BuildObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX,true);
        return mapper;
    }
}
