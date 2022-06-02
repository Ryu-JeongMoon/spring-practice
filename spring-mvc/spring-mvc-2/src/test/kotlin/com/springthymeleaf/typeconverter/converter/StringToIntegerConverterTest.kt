package com.springthymeleaf.typeconverter.converter

import com.springthymeleaf.typeconverter.IpPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.core.convert.support.DefaultConversionService

internal class StringToIntegerConverterTest {

  @Test
  fun `문자 to 숫자 변환`() {
    val converter = StringToIntegerConverter()
    val result = converter.convert("10")
    assertThat(result).isEqualTo(10)
  }

  @Test
  fun `숫자 to 문자 변환`() {
    val converter = IntegerToStringConverter()
    val result = converter.convert(10)
    assertThat(result).isEqualTo("10")
  }

  @Test
  fun `IpPort to 문자 변환`() {
    val converter = IpPortToStringConverter()
    val ipPort = IpPort("127.0.0.1", 8080)
    val result = converter.convert(ipPort)
    assertThat(result).isEqualTo("127.0.0.1:8080")
  }

  @Test
  fun `문자 to IpPort 변환`() {
    val converter = StringToIpPortConverter()
    val result = converter.convert("127.0.0.1:8080")
    assertThat(result).isEqualTo(IpPort("127.0.0.1", 8080))
  }

  @Test
  fun `기본 설정 사용`() {
    val conversionService = DefaultConversionService()
    conversionService.addConverter(StringToIntegerConverter())
    conversionService.addConverter(IntegerToStringConverter())
    conversionService.addConverter(IpPortToStringConverter())
    conversionService.addConverter(StringToIpPortConverter())

    val integerResult = conversionService.convert("10", Int::class.java)
    assertThat(integerResult).isEqualTo(10)

    val stringResult = conversionService.convert(10, String::class.java)
    assertThat(stringResult).isEqualTo("10")

    val ipPort = IpPort("127.0.0.1", 8080)
    val ipPortStringResult = conversionService.convert(ipPort, String::class.java)
    assertThat(ipPortStringResult).isEqualTo("127.0.0.1:8080")

    val ipPortResult = conversionService.convert(ipPortStringResult, IpPort::class.java)
    assertThat(ipPortResult).isEqualTo(ipPort)
  }
}

/*
변환까지는 오께이, 다만 얘네를 만들어두고 직접 호출해서 사용하면 변환 과정의 귀찮음만 조금 줄여줄 뿐
Converter 생성하고 변환 메서드를 호출하는 일은 계속해서 반복해야한다, 요건 아니야
어딘가에 등록해두고 parameter 타입에 따라 자동으로 변환해줬으면 좋겠다 -> ConversionService

Spring이 제공하는 DefaultConversionService는 ConversionService를 구현한다
이것의 설계 의도는 컨버터를 등록하는 과정과 사용하는 과정을 분리해 클라이언트는 오직 사용하는 입장에서만 신경 쓰면 된다
즉, ConversionService를 주입 받아 변환하면 되고 어떤 Converter에 의해 변환되는지는 알 바가 아니다
Interface Separation Principle, ISP 라고 하며 사용하는데 지장이 없게 최소한의 정보만 주고 나머지는 모두 감춘다
 */