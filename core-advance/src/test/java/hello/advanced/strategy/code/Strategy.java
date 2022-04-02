package hello.advanced.strategy.code;

public interface Strategy {

  void call();
}

/*
전략 패턴, 상속 보다 위임을 사용하게 해준다
사용할 전략을 필드로 가지고 있고 생성자를 통해 주입 받는다
비지니스 로직 호출은 전략이 제공하는 메서드를 이용하기에 위임한다

상속은 컴파일 시 강결합 된다
Subclass 에서 사용하지 않을 코드라도 상속을 받았기 때문에 전부 알게 되는 것
 */