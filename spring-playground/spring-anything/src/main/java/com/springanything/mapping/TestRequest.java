package com.springanything.mapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "data")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
// @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TestRequest implements SuperTestRequest {

	@XmlElement(name = "xml_name")
	private String name;

	@XmlElement(name = "xml_age")
	private int age;

	@Setter
	@XmlElement(name = "xml_inner")
	private TestInnerRequest testInnerRequest;

	public static TestRequest empty() {
		return new TestRequest("", 0, new TestInnerRequest());
	}

	public static TestRequest of(String name, int age, TestInnerRequest testInnerRequest) {
		return new TestRequest(name, age, testInnerRequest);
	}

	@XmlAccessorType(XmlAccessType.PROPERTY)
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@Getter
	@ToString
	@EqualsAndHashCode
	public static class TestInnerRequest implements SuperTestRequest {

		@XmlElement(name = "xml_inner_name")
		private String innerName;
	}
}

/*
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
Annotation or Class에 붙일 수 있고 붙인 범위에만 적용된다
TestRequest 클래스 위에 붙였다면 TestInnerRequest 하위 필드에는 적용되지 않는다
애노테이션에 붙일 수 있기 때문에 필요하다면 적절한 네이밍을 가진
SnakeCase로 수신 / 송신하는 전용 인터페이스를 만들어두고 이를 구현해서 사용하면 간편~~
 */