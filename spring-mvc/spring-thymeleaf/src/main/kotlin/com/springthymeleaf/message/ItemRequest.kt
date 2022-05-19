package com.springthymeleaf.message

data class ItemRequest(var id: Long?, var name: String?, var price: Int, var quantity: Int) {
  constructor() : this(null, null, 0, 0)
}

/*
GenericMapper<D, E> 사용 불가
kapt 문제인 듯 싶음, 코틀린 바보 자식
직접 타입 박아줘서 만들어야 하는디 실제로 쓰기에는 중복이 너무 많을듯
 */