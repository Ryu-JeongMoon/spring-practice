package com.example.ioc.service;

import com.example.ioc.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }
}

/*
BookRepository 는 인터페이스이고, 이 인터페이스를 구현한 클래스들이 여러개일 때
@Autowired 갈기면 어떤 녀석이 주입될까?

스프링은 알 수 없어서 에러를 터트린다
해결법은 다음과 같다
1. 주로 사용할 구현 클래스에 @Primary 붙여서 우선 순위가 높음을 알려줌
2. 타입은 인터페이스로 하되 변수명을 받으려는 bean 의 이름으로 한다  (pandaRepository 변수명 작성 ->  pandaRepository 주입)
3. @Qualifier("빈 이름") 으로 주입 받으려는 빈 자체를 적어주면 된다
4. List, Set 등의 컬렉션을 사용해서 모든 빈들을 주입 받아 꺼내서 사용

3번은 인자로 문자열을 받으므로 알고 있을 땐 상관 없지만 그다지 type-safe 하지 못하고 변경 발생 시
얘 때문에 문제가 잘 안 잡힐 수 있다, 그렇다고 쟤를 상수화 시켜서 관리하면 boiler plate 증가
따라서 이 놈보다는 @Primary 쓰는 것이 권장됨

컬렉션으로 받는 것은 굉장히 쓰잘데기 없는 짓이라 할 수 있다
쓰지 않는 것도 초기화해서 주입 받아야 하고 주입 받은 모든 것을 쓰는 것도 아니다
 */