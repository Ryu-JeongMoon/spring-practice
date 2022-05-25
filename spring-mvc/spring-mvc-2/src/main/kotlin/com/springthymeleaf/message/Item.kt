package com.springthymeleaf.message

import org.hibernate.validator.constraints.Range
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Item(
  @Id
  @GeneratedValue
  var id: Long? = null,

  @field:NotBlank
  var name: String,

  @field:NotNull
  @field:Range(min = 1_000, max = 1_000_000)
  var price: Int,

  @field:NotNull
  @field:Max(9999)
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