package com.springanything.crypto;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class EncryptorAESGCM {

  private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
  private static final int TAG_LENGTH_BIT = 128;
  private static final int IV_LENGTH_BYTE = 12;
  private static final int AES_KEY_BIT = 256;

  private static final Charset UTF_8 = StandardCharsets.UTF_8;

  // AES-GCM needs GCMParameterSpec
  public static byte[] encrypt(byte[] pText, SecretKey secret, byte[] iv) throws Exception {

    Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
    cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
    return cipher.doFinal(pText);

  }

  // prefix IV length + IV bytes to cipher text
  public static byte[] encryptWithPrefixIV(byte[] pText, SecretKey secret, byte[] iv) throws Exception {
    byte[] cipherText = encrypt(pText, secret, iv);

    return ByteBuffer.allocate(iv.length + cipherText.length)
      .put(iv)
      .put(cipherText)
      .array();

  }

  public static String decrypt(byte[] cText, SecretKey secret, byte[] iv) throws Exception {

    Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
    cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
    byte[] plainText = cipher.doFinal(cText);
    return new String(plainText, UTF_8);

  }

  public static String decryptWithPrefixIV(byte[] cText, SecretKey secret) throws Exception {

    ByteBuffer bb = ByteBuffer.wrap(cText);

    byte[] iv = new byte[IV_LENGTH_BYTE];
    bb.get(iv);
    //bb.get(iv, 0, iv.length);

    byte[] cipherText = new byte[bb.remaining()];
    bb.get(cipherText);

    return decrypt(cipherText, secret, iv);

  }

  public static void main(String[] args) throws Exception {
    String OUTPUT_FORMAT = "%-30s:%s";

    String pText = "Hello World AES-GCM, Welcome to Cryptography!";

    // encrypt and decrypt need the same key.
    // get AES 256 bits (32 bytes) key
    SecretKey secretKey = CryptoUtils.getAESKey(AES_KEY_BIT);

    // encrypt and decrypt need the same IV.
    // AES-GCM needs IV 96-bit (12 bytes)
    byte[] iv = CryptoUtils.getRandomNonce(IV_LENGTH_BYTE);

    byte[] encryptedText = EncryptorAESGCM.encryptWithPrefixIV(pText.getBytes(UTF_8), secretKey, iv);

    System.out.println("\n------ AES GCM Encryption ------");
    System.out.printf(OUTPUT_FORMAT + "%n", "Input (plain text)", pText);
    System.out.printf(OUTPUT_FORMAT + "%n", "Key (hex)", CryptoUtils.hex(secretKey.getEncoded()));
    System.out.printf(OUTPUT_FORMAT + "%n", "IV  (hex)", CryptoUtils.hex(iv));
    System.out.printf(OUTPUT_FORMAT + "%n", "Encrypted (hex) ", CryptoUtils.hex(encryptedText));
    System.out.printf(OUTPUT_FORMAT + "%n", "Encrypted (hex) (block = 16)", CryptoUtils.hexWithBlockSize(encryptedText, 16));

    System.out.println("\n------ AES GCM Decryption ------");
    System.out.printf(OUTPUT_FORMAT + "%n", "Input (hex)", CryptoUtils.hex(encryptedText));
    System.out.printf(OUTPUT_FORMAT + "%n", "Input (hex) (block = 16)", CryptoUtils.hexWithBlockSize(encryptedText, 16));
    System.out.printf(OUTPUT_FORMAT + "%n", "Key (hex)", CryptoUtils.hex(secretKey.getEncoded()));

    String decryptedText = EncryptorAESGCM.decryptWithPrefixIV(encryptedText, secretKey);
    System.out.printf((OUTPUT_FORMAT) + "%n", "Decrypted (plain text)", decryptedText);

  }

}
