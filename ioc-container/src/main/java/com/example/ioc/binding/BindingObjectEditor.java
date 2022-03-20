package com.example.ioc.binding;

import java.beans.PropertyEditorSupport;

public class BindingObjectEditor extends PropertyEditorSupport {

  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    setValue(new BindingObject(Long.valueOf(text)));
  }
}

/*
PropertyEditorSupport 얘는 상태 정보를 가지고 있기 때문에 stateful 하다
따라서 thread-safety 보장되지 않으므로 전역적으로 사용하면 대참사 난다
사용하는 곳에서 @InitBinder 를 이용해 만들어두고 사용해야 한다
Bean 으로 등록해야 겠다면 scope -> thread 로 만들어 사용해야 한다
 */