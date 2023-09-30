package com.springanything.json.mapping;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonNullRequest {

  private List<String> names = new ArrayList<>();

}
