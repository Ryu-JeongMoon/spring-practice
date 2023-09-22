package com.springanything.crypto;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CBC256Cipher {

  private static final String ALGORITHM = "AES";
  private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
  private static final String SECRET_KEY = "12345678123456781234567812345678"; //32bit
  private static final String IV = SECRET_KEY.substring(0, 16);

  //암호화
  public static String encrypt(String str) {
    byte[] keyData = SECRET_KEY.getBytes();
    SecretKey secureKey = new SecretKeySpec(keyData, ALGORITHM);

    try {
      Cipher c = Cipher.getInstance(TRANSFORMATION);
      c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));
      byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));
      return new String(Base64.encodeBase64(encrypted, false));
    } catch (Exception e) {
      log.error("cannot encipher");
      throw new IllegalStateException();
    }
  }

  //복호화
  public static String decrypt(String str) {
    byte[] keyData = SECRET_KEY.getBytes();
    SecretKey secureKey = new SecretKeySpec(keyData, ALGORITHM);

    try {
      Cipher c = Cipher.getInstance(TRANSFORMATION);
      c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));

      byte[] byteStr = Base64.decodeBase64(str);

      return new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
    } catch (Exception e) {
      log.error("cannot decipher");
      throw new IllegalStateException();
    }
  }
}
