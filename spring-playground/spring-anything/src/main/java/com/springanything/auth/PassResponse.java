package com.springanything.auth;

public record PassResponse(
  String requestSequence,
  String responseSequence,
  String authType,
  String cipherDateTime,
  String name,
  String birth,
  String gender,
  String di,
  String ci,
  String phone,
  String mobileCompany
) {

}
