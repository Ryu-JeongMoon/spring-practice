package com.springservlet.domain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRepository {

  private static final AtomicLong SEQUENCE = new AtomicLong(2);
  private static final Map<Long, Member> STORE = new ConcurrentHashMap<>(
    Map.of(1L, Member.builder().id(1L).username("panda").age(55).build(),
      2L, Member.builder().id(2L).username("bear").age(66).build()
    ));

  public static MemberRepository getInstance() {
    return LazyHolder.INSTANCE;
  }

  private static class LazyHolder {

    private static final MemberRepository INSTANCE = new MemberRepository();
  }

  public Member save(Member member) {
    long nextId = SEQUENCE.incrementAndGet();
    member.setId(nextId);
    STORE.put(nextId, member);

    return member;
  }

  public Member findById(Long id) {
    return STORE.get(id);
  }

  public List<Member> findAll() {
    return STORE.values()
      .stream()
      .collect(Collectors.toUnmodifiableList());
  }

  public void clear() {
    STORE.clear();
  }
}
