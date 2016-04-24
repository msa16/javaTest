/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.diasoft;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
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
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);
        return secretKeySpec;
    }

    private void test1() throws Exception {
//        KeyGenerator keygen = KeyGenerator.getInstance("AES");
//        SecretKey aesKey = keygen.generateKey();

        Cipher aesCipher;

        // Create the cipher
        aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        //We use the generated aesKey from above to initialize the Cipher object for encryption
        // Initialize the cipher for encryption
        aesCipher.init(Cipher.ENCRYPT_MODE, generateKey());

        // Our cleartext
        byte[] cleartext = "This is just an example2".getBytes();

        // Encrypt the cleartext
        byte[] ciphertext = aesCipher.doFinal(cleartext);

        // Initialize the same cipher for decryption
        aesCipher.init(Cipher.DECRYPT_MODE, generateKey());

        // Decrypt the ciphertext
        byte[] cleartext1 = aesCipher.doFinal(ciphertext);
        String s = new String(cleartext1);
        System.out.println(s);
        
        // String encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
        // byte [] decryptedValue64 = new BASE64Decoder().decodeBuffer(value);
    }

    public static void main(String[] args) throws Exception {
        new CryptDecrypt1().test1();
    }

}
