package com.springanything.auth;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.RequiredArgsConstructor;

import NiceID.Check.CPClient;

/**
 * Nice library needs to be installed in the 'libs' directory.
 */
@Controller
@RequiredArgsConstructor
public class PassController {

  private static final String SITE_CODE = "{{site_code}}";
  private static final String SITE_PASSWORD = "{{site_password}}";
  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  //  authType        - 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서
  //  hasCancelButton - Y: 취소버튼 있음, N: 취소버튼 없음
  //  customize       - 없으면 기본 웹페이지 / Mobile : 모바일
  //  gender          - 없으면 기본 선택 값, 0 : 여자, 1 : 남자
  @GetMapping(value = "/pass/test")
  public String getPassTestPage(
    HttpServletRequest request,
    HttpServletResponse response,
    Model model
  ) {
    CPClient niceCheck = new CPClient();

    String requestSequence = niceCheck.getRequestNO(SITE_CODE);
    String rawData = createRawData(requestSequence);
    String encrypted = "";
    String errorMessage = "";

    int isEncryptSuccessful = niceCheck.fnEncode(SITE_CODE, SITE_PASSWORD, rawData);
    switch (isEncryptSuccessful) {
      case 0 -> encrypted = niceCheck.getCipherData();
      case -1 -> errorMessage = "암호화 시스템 에러입니다.";
      case -2 -> errorMessage = "암호화 처리오류입니다.";
      case -3 -> errorMessage = "암호화 데이터 오류입니다.";
      case -9 -> errorMessage = "입력 데이터 오류입니다.";
      default -> errorMessage = "알수 없는 에러 입니다.";
    }

    request.getSession().setAttribute("requestSequence", requestSequence);
    response.setHeader("Content-Security-Policy", "script-src 'self';");

    model.addAttribute("scriptNonce", createNonce());
    model.addAttribute("encrypted", encrypted);
    model.addAttribute("errorMessage", errorMessage);
    return "pass_test";
  }

  private String createRawData(String requestSequence) {
    return "7:REQ_SEQ" + createPlainData(requestSequence)
      + "8:SITECODE" + createPlainData(SITE_CODE)
      + "9:AUTH_TYPE" + createPlainData(StringUtils.EMPTY)
      + "7:RTN_URL" + createPlainData("http://localhost:8080/pass/success")
      + "7:ERR_URL" + createPlainData("http://localhost:8080/pass/fail")
      + "11:POPUP_GUBUN" + createPlainData("Y")
      + "9:CUSTOMIZE" + createPlainData(StringUtils.EMPTY)
      + "6:GENDER" + createPlainData(StringUtils.EMPTY);
  }

  private String createPlainData(String value) {
    return value.getBytes().length + ":" + value;
  }

  private String createNonce() {
    byte[] nonceBytes = new byte[16];
    SECURE_RANDOM.nextBytes(nonceBytes);
    return Base64.getEncoder().encodeToString(nonceBytes);
  }

  // web    -> get
  // mobile -> post
  @RequestMapping(
    value = "/pass/success",
    method = {RequestMethod.GET, RequestMethod.POST})
  public String verify(
    HttpServletRequest request,
    Model model
  ) {
    CPClient niceCheck = new CPClient();
    String errorMessage = "";

    int isDecryptionSuccessful = niceCheck.fnDecode(SITE_CODE, SITE_PASSWORD, request.getParameter("EncodeData"));
    switch (isDecryptionSuccessful) {
      case 0 -> {
        @SuppressWarnings("unchecked")
        Map<String, String> result = niceCheck.fnParse(niceCheck.getPlainData());

        PassResponse passResponse = new PassResponse(
          result.get("REQ_SEQ"),
          result.get("RES_SEQ"),
          result.get("AUTH_TYPE"),
          niceCheck.getCipherDateTime(),
          result.get("NAME"),
          result.get("BIRTHDATE"),
          result.get("GENDER"),
          result.get("DI"),
          result.get("CI"),
          result.get("MOBILE_NO"),
          result.get("MOBILE_CO")
        );

        String sessionRequestSequence = (String) request.getSession().getAttribute("requestSequence");
        if (!StringUtils.equals(passResponse.requestSequence(), sessionRequestSequence)) {
          errorMessage = "세션값 불일치 오류입니다.";
        }
        model.addAttribute("passResponse", passResponse);
      }

      case -1 -> errorMessage = "복호화 시스템 오류입니다.";
      case -4 -> errorMessage = "복호화 처리 오류입니다.";
      case -5 -> errorMessage = "복호화 해쉬 오류입니다.";
      case -6 -> errorMessage = "복호화 데이터 오류입니다.";
      case -9 -> errorMessage = "입력 데이터 오류입니다.";
      case -12 -> errorMessage = "사이트 패스워드 오류입니다.";
      default -> errorMessage = "알수 없는 에러 입니다 : " + isDecryptionSuccessful;
    }

    model.addAttribute("errorMessage", errorMessage);
    return "pass_success";
  }
}
