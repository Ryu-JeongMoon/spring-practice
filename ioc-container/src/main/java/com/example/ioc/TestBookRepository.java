package com.example.ioc;

import com.example.ioc.repository.BookRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test | (develop & prod)")
public class TestBookRepository implements BookRepository {
}

/*
profile 표현식에는 아래와 같은 표현 가능
1. | or
2. & and
3. ! not

!test | develop & prod -> 오류 뜸
!test | (develop & prod) 묶어주면 오께이
현재 요구사항을 간단하게 만족시키는 방법이 가장 좋은 방법

PROFILE 다를 시 DI 받을 인스턴스 찾지 못 하기에 애플리케이션 띄워지지 않음
Field testBookRepository in com.example.ioc.runner.AppRunner
required a bean of type 'com.example.ioc.TestBookRepository' that could not be found.
 */