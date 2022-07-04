package com.springanything.mapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "data")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class TestRequest {

	@XmlElement(name = "xml_name")
	private String name;

	@XmlElement(name = "xml_age")
	private int age;

	@XmlElement(name = "xml_inner")
	private TestInnerRequest testInnerRequest = new TestInnerRequest();

	@XmlAccessorType(XmlAccessType.PROPERTY)
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@Getter
	@ToString
	public static class TestInnerRequest {

		@XmlElement(name = "xml_inner_name")
		private String innerName;
	}
}
