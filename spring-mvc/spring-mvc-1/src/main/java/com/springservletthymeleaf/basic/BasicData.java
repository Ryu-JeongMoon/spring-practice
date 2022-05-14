package com.springservletthymeleaf.basic;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicData {

	private String username;
	private int age;
}

/*
- @ModelAttribute
ModelAttributeMethodProcessor의 resolveArgument 호출에 의해 흐름이 시작된다
resolveArgument -> constructAttribute -> BeanUtils의 instantiateClass -> Constructor의 newInstanceWithCaller 호출
newInstanceWithCaller 메서드는 바인딩 시킬 parameter 존재하는 생성자를 이용한다
여기서는 Reflection을 사용해 가져온 @AllArgsConstructor를 호출하기 때문에
getter & setter & default constructor 필요 없다

- @RequestBody
jackson lib, ObjectMapper를 사용하여 직렬화 & 역직렬화 때린다
getter, setter 통해 필드를 가져올 수 있으나 실제 값을 주입하는 것은 reflection api
BeanDeserializer의 deserializeFromObject 메서드에서 SettableBeanProperty를 찾고
FieldProperty의 deserializeAndSet 메서드에서 Reflection을 사용해 값을 박아버린다
즉, getter & setter & default constructor 없어도 @RequestBody 사용한 DTO 주입 가능

@NoArgsConstructor 없을 때, @RequestBody 바인딩 실패
@NoArgsConstructor Access Level -> PUBLIC 일 때, @ModelAttribute 바인딩 실패
@NoArgsConstructor Access Level -> PROTECTED 일 때, 바인딩 둘다 성공

ModelAttributeMethodProcessor에서 생성자 찾을 때 Class.getConstructors()를 사용하는데
얘는 Public 접근 제한인 생성자만 반환하기 때문에 기본 생성자를 반환해주지 않는다
@ModelAttribute에서 기본 생성자를 사용하기로 했다면 반드시 setter 존재해야 하는데
여기서 기본 생성자의 접근 제한을 막아뒀기 때문에 인자가 있는 생성자를 사용해 매핑이 성공 됐다

@RequestBody는 getter, setter 프로퍼티가 존재하면 얘를 가져와 prefix 없애고 필드에 접근한다
필드에 reflection 주입 방식으로 동작하기 때문에 기본 생성자만 있다면 오께이라 성공했다
 */