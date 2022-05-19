package com.springthymeleaf.message

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Item(
  @Id
  @GeneratedValue
  var id: Long? = null,
  var name: String,
  var price: Int,
  var quantity: Int
) {
  constructor() : this(null, "", 0, 0)

  override fun toString(): String {
    return "Item(id=$id, name='$name', price=$price, quantity=$quantity)"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Item

    if (id != other.id) return false
    return true
  }

  override fun hashCode(): Int {
    return id?.hashCode() ?: 0
  }
}