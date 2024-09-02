package io.city.hospital;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestUtil {

    public static final String TEST_ENVIRONMENT = "test";
    public static final String TEST_TRACE_ID = "test_trace_id";
    private static final ObjectMapper mapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    /**
     * Convert an object to JSON byte array.
     *''
     * @param object the object to convert.
     * @return the JSON byte array.
     * @throws IOException the io exception
     */
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }


    public static <T> T convertBytesJsonToObject(File bytes, Class<T> c) throws IOException {
        return mapper.readValue(bytes, c);
    }

    public static String buildGetEansUrl(List<String> eans, String country, String URL) {
        StringBuilder urlBuilder = new StringBuilder(URL);

        for (String ean : eans) {
            urlBuilder.append("ean=").append(ean).append("&");
        }

        urlBuilder.append("country=").append(country);
        return urlBuilder.toString();
    }

}

