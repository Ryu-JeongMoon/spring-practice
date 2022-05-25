package com.springthymeleaf.message

data class ItemResponse(var id: Long?, var name: String?, var price: Int, var quantity: Int) {
  constructor() : this(null, null, 0, 0)
}