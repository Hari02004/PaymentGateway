package org.JamesWeb.util;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

@Component
public class ApiKeyUtil {
    private static String generateRandomString() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes).substring(0, 32);
    }

    public static String generatePublicKey() {
        return "pk_test_" + generateRandomString();
    }
    public static String generateSecretKey() {
        return "sk_test_" + generateRandomString();
    }
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    public static String hashSecretKey(String data,String secretKey) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);

            SecretKeySpec secretKeySpec =
                    new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
                            HMAC_ALGORITHM);

            mac.init(secretKeySpec);

            byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hmacBytes);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC", e);
        }
    }
    private static final String AES_KEY = "1234567890123456";

    public static String encrypt(String value)  {
        try{
            SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String decrypt(String encryptedValue)  {
        try{
            SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            byte[] decoded = Base64.getDecoder().decode(encryptedValue);

            return new String(cipher.doFinal(decoded));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

