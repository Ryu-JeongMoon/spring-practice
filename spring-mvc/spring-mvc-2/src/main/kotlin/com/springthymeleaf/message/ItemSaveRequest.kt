package com.springthymeleaf.message

import org.hibernate.validator.constraints.Range
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class ItemSaveRequest(
  @field:NotBlank
  var name: String?,
  @field:NotNull
  @field:Range(min = 1_000, max = 1_000_000)
  var price: Int?,
  @field:NotNull
  @field:Max(9999)
  var quantity: Int?
) {
  constructor() : this(null, null, null)
}

/*
GenericMapper<D, E> 사용 불가
kapt 문제인 듯 싶음, 코틀린 바보 자식
직접 타입 박아줘서 만들어야 하는디 실제로 쓰기에는 중복이 너무 많을듯
 */