package com.example.ioc;

import com.example.ioc.repository.BookRepository;
import com.example.ioc.scope.Proto;
import com.example.ioc.scope.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Injection {

  private final BookRepository bookRepository;
  @Autowired
  private Single single;
  private Proto proto;

  public Injection(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Autowired
  public void setProto(Proto proto) {
    this.proto = proto;
  }
}

/*
의존성 주입 방법에는 세가지가 있다
1. field 처박기
2. setter 처박기
3. constructor 주입

첫번째 필드 처박기는 말 그대로 생성 시점에 필드에다가 그대로 처박아버린다
일반적으로 빈은 싱글턴으로 관리 되기 때문에 그럴 일은 사실 상 많지 않지만
필드 주입은 의존성이 고정되어서 절대 변경될 수 없는 문제가 있다
의존성 주입을 클라이언트에게 넘기는 경우가 있을텐데
생성자 주입을 사용한다면 어떤 의존성을 주입할 지 클라이언트가 결정하는데
필드 주입의 경우에는 클라이언트에게 책임이 있지 않다

두번째 setter 로 처박는 것은 setter 가 있다는 것부터가 문제다
클래스를 불변으로 만들 수 없고 악의적인 사용자에 의해 멀쩡한 클래스가 개박살날 수 있다
또한 setter 로 의존성 주입을 하기 전까지 순환 참조 발생을 모르기 때문에 애플리케이션이 띄워지는 것이 문제다
생성자 주입을 하는 경우 애플리케이션 자체가 띄워지지 않기 때문에 순환 참조를 바로 알 수 있다
 */