package com.springanything.mapping.nested;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class NestedSetterRequest implements Serializable {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Embedded
	private final InnerRequest innerRequest = new InnerRequest();

	public NestedSetterRequest update(NestedSetterRequest request) {
		this.innerRequest.setName(request.innerRequest.getName());
		this.innerRequest.setNickname(request.innerRequest.getNickname());
		return this;
	}
}

/*
NestedRequest 참고, Entity - Embeddable 활용

1. @NoArgs + @Getter + @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) 사용
Outer 객체 @NoArgsConstructor(access = AccessLevel.PROTECTED), @Getter
Inner 객체 @NoArgsConstructor(access = AccessLevel.PROTECTED), @Getter, @Setter

Outer 객체에서 InnerRequest 객체를 미리 생성 (불변 or 가변 둘 다 가능), 미리 생성해두지 않으면 null 매핑되거나 에러 발생
Inner 객체는 변경 지점 줄이기 위해 가시성을 protected or default 설정
즉 도메인이 아닌 다른 Layer에서 InnerRequest 자체를 get / set 불가
mapping 시에는 @JsonAutoDetect + @Setter에 의해 innerRequest.name, innerRequest.nickname 으로 매핑됨
 */