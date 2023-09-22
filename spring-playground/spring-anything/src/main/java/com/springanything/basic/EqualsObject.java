package com.springanything.basic;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class EqualsObject {

  private String id;

  private final List<String> list = new ArrayList<>();

  public EqualsObject(String id, List<String> list) {
    this.id = id;
    this.list.addAll(list);
  }
}

/*
hashCode 구현?!

public int hashCode() {
	int PRIME = true;
	int result = 1;
	Object $id = this.getId();
	result = result * 59 + ($id == null ? 43 : $id.hashCode());
	Object $list = this.getList();
	result = result * 59 + ($list == null ? 43 : $list.hashCode());
	return result;
}
 */
