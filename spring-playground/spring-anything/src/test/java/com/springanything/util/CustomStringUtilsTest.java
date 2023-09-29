package com.springanything.util;

import static org.assertj.core.api.Assertions.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.apache.tomcat.util.http.fileupload.util.mime.RFC2231Utility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomStringUtilsTest {

  @DisplayName("filename 매개 변수에 *가 포함 되는 경우 RFC2231에 따라 인코딩 된 값이 있다고 판단")
  @Test
  void hasEncodedValue() {
    // given, when
    boolean hasEncodedValue = RFC2231Utility.hasEncodedValue("filename*");

    // then
    assertThat(hasEncodedValue).isTrue();
  }

  @DisplayName("RFC2231에 따라 인코딩 후 multipart/form-data 요청을 보내고 서버는 RFC2231에 따라 다시 디코딩 후 처리")
  @Test
  void toRFC2231() throws UnsupportedEncodingException {
    // given
    String originalFilename = "헬로월드.txt";
    String encoded = CustomStringUtils.toRFC2231(originalFilename);

    // when
    String decoded = RFC2231Utility.decodeText(encoded);

    // then
    assertThat(decoded).isEqualTo(originalFilename);
  }

  @DisplayName("rfc2231, rfc5987 spring 구현체 같은 결과를 내는지 확인")
  @Test
  void compareEncoding() {
    // given
    String originalFilename = "헬로월드.txt";

    // when
    String encoded1 = CustomStringUtils.toRFC2231(originalFilename);
    String encoded2 = CustomStringUtils.encodeRfc5987Filename(originalFilename, StandardCharsets.UTF_8);

    // then
    assertThat(encoded1).isEqualTo(encoded2);
  }
}
