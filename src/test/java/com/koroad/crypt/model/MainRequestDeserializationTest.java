package com.koroad.crypt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MainRequestDeserializationTest {

    @Test
    void deserializesTestEncryptPayload() throws Exception {
        String json = Files.readString(Paths.get("test_path/test_encrypt.json"), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();

        MainRequest request = mapper.readValue(json, MainRequest.class);

        assertNotNull(request.getPayload());
        assertNotNull(request.getPayload().getLicenses());
        assertEquals(1, request.getPayload().getLicenses().size());

        DriverLicenseInfo license = request.getPayload().getLicenses().get(0);
        assertEquals("1212345612", license.getLicenseNo());
        assertEquals("홍길동", license.getResidentName());
        assertEquals("123456", license.getResidentDate());
        assertEquals("123456", license.getSeqNo());
        assertEquals("11", license.getLicenseConCode());
        assertEquals("20170728", license.getFromDate());
        assertEquals("20170728", license.getToDate());
    }

    @Test
    void deserializesWithGetterVisibilityOnly() throws Exception {
        String json = Files.readString(Paths.get("test_path/test_encrypt.json"), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper()
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY)
                .setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);

        MainRequest request = mapper.readValue(json, MainRequest.class);

        DriverLicenseInfo license = request.getPayload().getLicenses().get(0);
        assertEquals("1212345612", license.getLicenseNo());
        assertEquals("홍길동", license.getResidentName());
    }

    @Test
    void deserializesFromMapLikeLambdaBinding() throws Exception {
        String json = Files.readString(Paths.get("test_path/test_encrypt.json"), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> raw = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        MainRequest request = mapper.convertValue(raw, MainRequest.class);

        DriverLicenseInfo license = request.getPayload().getLicenses().get(0);
        assertEquals("1212345612", license.getLicenseNo());
        assertEquals("홍길동", license.getResidentName());
    }
}
