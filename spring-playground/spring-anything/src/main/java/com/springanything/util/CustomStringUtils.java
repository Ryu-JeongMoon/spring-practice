package com.springanything.util;

import static java.nio.charset.StandardCharsets.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.http.ContentDisposition;
import org.springframework.util.Assert;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <a href="https://datatracker.ietf.org/doc/html/rfc2231">rfc2231</a>
 * <a href="https://datatracker.ietf.org/doc/html/rfc5987">rfc5987</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomStringUtils {

  public static String toRFC2231(String str) {
    StringBuilder sb = new StringBuilder("UTF-8''");
    for (byte b : str.getBytes(StandardCharsets.UTF_8)) {
      if (b >= 0x20 && b <= 0x7E) {
        if (b == '"' || b == '%' || b == '\\') {
          sb.append('\\');
        }
        sb.append((char) b);
      } else {
        sb.append('%').append(String.format("%02X", b));
      }
    }
    return sb.toString();
  }

  /**
   * @see ContentDisposition#encodeRfc5987Filename(String, Charset)
   */
  public static String encodeRfc5987Filename(String input, Charset charset) {
    Assert.notNull(input, "'input' must not be null");
    Assert.notNull(charset, "'charset' must not be null");
    Assert.isTrue(!StandardCharsets.US_ASCII.equals(charset), "ASCII does not require encoding");
    Assert.isTrue(UTF_8.equals(charset) || ISO_8859_1.equals(charset), "Only UTF-8 and ISO-8859-1 are supported");

    byte[] source = input.getBytes(charset);
    StringBuilder sb = new StringBuilder(source.length << 1)
      .append(charset.name())
      .append("''");

    for (byte b : source) {
      if (isRFC5987AttrChar(b)) {
        sb.append((char) b);
      } else {
        sb.append('%')
          .append(hexDigit(b >> 4))
          .append(hexDigit(b));
      }
    }
    return sb.toString();
  }

  private static boolean isRFC5987AttrChar(byte c) {
    return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
      || c == '!' || c == '#' || c == '$' || c == '&' || c == '+' || c == '-'
      || c == '.' || c == '^' || c == '_' || c == '`' || c == '|' || c == '~';
  }

  private static char hexDigit(int b) {
    return Character.toUpperCase(Character.forDigit(b & 0xF, 16));
  }
}
