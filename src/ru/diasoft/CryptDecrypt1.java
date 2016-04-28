/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.diasoft;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Serega
 */
public class CryptDecrypt1 {

    private static final String ALGORITHM = "AES";

    private static Key generateKey() throws Exception {
        byte[] key = "password".getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // use only first 128 bit
        for (int i = 0; i < 16; i++) {
            key[i] = (byte) (i + 1);
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);
        return secretKeySpec;
    }

    private void test1() throws Exception {
//        KeyGenerator keygen = KeyGenerator.getInstance("AES");
//        SecretKey aesKey = keygen.generateKey();

        Cipher aesCipher;

        // Create the cipher AES/ECB/PKCS5Padding AES/CBC/NoPadding
        aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        //We use the generated aesKey from above to initialize the Cipher object for encryption
        // Initialize the cipher for encryption
        aesCipher.init(Cipher.ENCRYPT_MODE, generateKey());
        // Our cleartext
        byte[] cleartext = "1234567890123456".getBytes("utf-8");
        // Encrypt the cleartext
        byte[] ciphertext = aesCipher.doFinal(cleartext);

        for (int i = 0; i < ciphertext.length; i++) {
            System.out.println(ciphertext[i]);
        }

        // Initialize the same cipher for decryption
        aesCipher.init(Cipher.DECRYPT_MODE, generateKey());

        // Decrypt the ciphertext
        byte[] cleartext1 = aesCipher.doFinal(ciphertext);
        String s = new String(cleartext1);
        System.out.println(s);
        
        // String encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
        // byte [] decryptedValue64 = new BASE64Decoder().decodeBuffer(value);
    }

   public static byte[] encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return encrypted;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String key, String initVector, byte[] encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(encrypted);

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }    
    
    private void test2() {
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV

        byte[] enc = encrypt(key, initVector, "Hello World");
        for (int i = 0; i < enc.length; i++) {
            System.out.println(enc[i]);
        }
        System.out.println(decrypt(key, initVector, enc));
    }
    
    public static void main(String[] args) throws Exception {
//        new CryptDecrypt1().test1();
        new CryptDecrypt1().test2();
    }

}
