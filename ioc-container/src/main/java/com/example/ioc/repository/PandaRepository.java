package com.example.ioc.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class PandaRepository implements BookRepository {
}
