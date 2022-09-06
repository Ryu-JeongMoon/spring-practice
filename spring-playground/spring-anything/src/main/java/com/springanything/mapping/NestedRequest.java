package com.springanything.mapping;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class NestedRequest implements Serializable {

	private InnerRequest innerRequest = new InnerRequest();

	public NestedRequest(String name, String nickname) {
		this.innerRequest = new InnerRequest(name, nickname);
	}

	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	@ToString
	public static class InnerRequest implements Serializable {

		private String name;
		private String nickname;
	}
}

/*
중첩 객체의 경우, 선택이 필요하다
팀의 컨벤션을 따르거나 형태의 전체적인 통일성을 맞추도록 리팩토링해야 한다

1. @Setter 없애고 각각의 필드를 상위 객체의 생성자로 받는다
- 상위 객체 @NoArgsConstructor(access = AccessLevel.PROTECTED), @Getter
- 하위 객체 @NoArgsConstructor(access = AccessLevel.PROTECTED), @AllArgsConstructor(access = AccessLevel.PRIVATE), @Getter
이때 Controller에서 query-string ModelAttribute로 매핑한다면 아래와 같은 형태로 된다
/test/reiterative?name=panda&nickname=bear
즉 중첩 객체지만 innerRequest.name=xxx, innerRequest.nickname=xxx 형태로 매핑하지 않고
name=xxx, nickname=xxx 형태로 매핑한다

2. @NoArgs + @Setter 사용
- @NoArgsConstructor(access = AccessLevel.PROTECTED), @Getter, @Setter
Reflection으로 객체를 생성하므로 @NoArgs의 접근 제한은 private 이어도 가능하므로
이 방식에선 @NoArgsConstructor는 쟁점이 아니다, @Setter가 중요하다
/test/reiterative?innerRequest.name=panda&innerRequest.nickname=bear 형태로 매핑한다
 */