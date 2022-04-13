package org.springaop.member;

import org.springaop.member.annotation.ClassAop;
import org.springaop.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService{

  @MethodAop("test value")
  @Override
  public String hello(String param) {
    return "ok";
  }

  public String internal(String param) {
    return "ok";
  }
}
