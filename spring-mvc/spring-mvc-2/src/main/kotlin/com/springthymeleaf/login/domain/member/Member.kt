package com.springthymeleaf.login.domain.member

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotEmpty

@Entity
class Member(
  @Id @GeneratedValue
  var id: Long?,
  @field:NotEmpty
  var loginId: String?,
  @field:NotEmpty
  var name: String?,
  @field:NotEmpty
  var password: String?
) {

  constructor() : this(null, null, null, null)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Member

    if (id != other.id) return false
    if (loginId != other.loginId) return false
    if (name != other.name) return false
    if (password != other.password) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + loginId.hashCode()
    result = 31 * result + name.hashCode()
    result = 31 * result + password.hashCode()
    return result
  }

  override fun toString(): String {
    return "Member(id=$id, loginId=$loginId, name=$name, password=$password)"
  }
}