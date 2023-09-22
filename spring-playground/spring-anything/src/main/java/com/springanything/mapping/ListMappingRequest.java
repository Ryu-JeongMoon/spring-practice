package com.springanything.mapping;

import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ListMappingRequest {

  private String text;

  @JsonIgnore
  private List<String> texts;

  public ListMappingRequest(String text, List<String> texts) {
    this.text = text;

    if (!StringUtils.hasText(text)) {
      this.text = String.join("/", texts);
      this.texts = texts;
    }
  }
}
