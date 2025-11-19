package com.koroad.crypt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koroad.crypt.model.DriverLicenseInfo;
import com.koroad.crypt.model.DriverLicensePayload;
import kr.or.koroad.dlv.crypt.aria.cipher.ARIACipher256;
import kr.or.koroad.dlv.util.Base64;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class KoroadCryptoServiceTest {

    @Test
    void encryptAndDecryptRoundTrip() {
        String clientSecret = "TEST_CLIENT_SECRET_1234567890";
        KoroadCryptoService service = new KoroadCryptoService(clientSecret);

        DriverLicenseInfo info = new DriverLicenseInfo();
        info.setLicenseNo("1212345612");
        info.setResidentName("홍길동");
        info.setResidentDate("123456");
        info.setSeqNo("123456");
        info.setLicenseConCode("11");
        info.setFromDate("20170728");
        info.setToDate("20170728");

        DriverLicensePayload payload = new DriverLicensePayload();
        payload.setLicenses(Collections.singletonList(info));

        String encrypted = service.encrypt(payload);
        assertNotNull(encrypted);
        assertFalse(encrypted.isEmpty());

        DriverLicensePayload decrypted = service.decrypt(encrypted);
        assertNotNull(decrypted);
        assertNotNull(decrypted.getLicenses());
        assertEquals(1, decrypted.getLicenses().size());

        DriverLicenseInfo decryptedInfo = decrypted.getLicenses().get(0);
        assertEquals(info.getLicenseNo(), decryptedInfo.getLicenseNo());
        assertEquals(info.getResidentName(), decryptedInfo.getResidentName());
        assertEquals(info.getResidentDate(), decryptedInfo.getResidentDate());
        assertEquals(info.getSeqNo(), decryptedInfo.getSeqNo());
        assertEquals(info.getLicenseConCode(), decryptedInfo.getLicenseConCode());
        assertEquals(info.getFromDate(), decryptedInfo.getFromDate());
        assertEquals(info.getToDate(), decryptedInfo.getToDate());
    }

    @Test
    void encryptMatchesDirectAriaCipher256Usage() throws Exception {
        String clientSecret = "TEST_CLIENT_SECRET_1234567890";
        KoroadCryptoService service = new KoroadCryptoService(clientSecret);

        DriverLicenseInfo info = new DriverLicenseInfo();
        info.setLicenseNo("1212345612");
        info.setResidentName("홍길동");
        info.setResidentDate("123456");
        info.setSeqNo("123456");
        info.setLicenseConCode("11");
        info.setFromDate("20170728");
        info.setToDate("20170728");

        DriverLicensePayload payload = new DriverLicensePayload();
        payload.setLicenses(Collections.singletonList(info));

        String encryptedByService = service.encrypt(payload);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(payload.getLicenses());

        String encodedKey = Base64.encode(clientSecret.getBytes());
        ARIACipher256 ariaCipher256 = new ARIACipher256(encodedKey);
        byte[] plainBytes = json.getBytes();
        byte[] encryptedBytes = ariaCipher256.encrypt(plainBytes);
        String encryptedDirect = Base64.encode(encryptedBytes);

        assertEquals(encryptedDirect, encryptedByService);
    }
}
