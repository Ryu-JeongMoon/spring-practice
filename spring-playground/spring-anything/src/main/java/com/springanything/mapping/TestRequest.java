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
	public static class TestInnerRequest {

		@XmlElement(name = "xml_inner_name")
		private String innerName;
	}
}
