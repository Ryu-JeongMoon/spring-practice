package com.springanything.json.mapping;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JsonNullRequest {

  private List<String> names = new ArrayList<>();

}
