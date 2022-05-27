package com.springthymeleaf.login.web.login

import com.springthymeleaf.login.domain.member.Member
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LoginRequestMapper {

  fun toDto(entity: Member): LoginRequest
  fun toEntity(dto: LoginRequest): Member
  fun toDtoList(entityList: List<Member>?): List<LoginRequest>?
  fun toEntityList(dtoList: List<LoginRequest>?): List<Member>?
}