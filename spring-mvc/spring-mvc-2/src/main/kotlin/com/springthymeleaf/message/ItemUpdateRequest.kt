package com.springthymeleaf.message

import org.hibernate.validator.constraints.Range
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class ItemUpdateRequest(
  @field:NotNull
  var id: Long?,
  @field:NotBlank
  var name: String?,
  @field:NotNull
  @field:Range(min = 1_000, max = 1_000_000)
  var price: Int?,
  @field:NotNull
  var quantity: Int?
)

/*
kotlin에서는 @field:NotBlank 처럼 필드에 적용할 것이라고 명시적으로 박아줘야 한다
안 넣어주면 생성자에 애노테이션 붙인 형태로 작동되기 때문!
수정 시에는 수량을 자유롭게 변경 가능하다
 */