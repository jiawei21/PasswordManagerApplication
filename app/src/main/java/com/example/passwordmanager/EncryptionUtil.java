
package com.example.passwordmanager;

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {

    // Note: Use a 16-character string for AES-128
    private static final String KEY = "1234567812345678"; 
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    public static String encrypt(String data) {
        if (data == null || data.isEmpty()) return "";
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP);
        } catch (Exception e) {
            return data;
        }
    }

    public static String decrypt(String data) {
        if (data == null || data.isEmpty()) return "";
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.decode(data, Base64.NO_WRAP);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            // Return raw data if it's not encrypted yet (for old items)
            return data;
        }
    }
}
