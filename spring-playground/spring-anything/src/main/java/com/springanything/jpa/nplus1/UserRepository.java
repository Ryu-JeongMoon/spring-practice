package com.springanything.jpa.nplus1;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select distinct u from User u left join u.articles where u.name = :name")
  Optional<User> findByNameJPQL(String name);

  @Query("select distinct u from User u left join u.articles")
  List<User> findAllJPQL();

  @Query("select distinct u from User u left join fetch u.articles")
  List<User> findAllByJPQLFetch();

  @EntityGraph(attributePaths = { "articles" }, type = EntityGraph.EntityGraphType.FETCH)
  @Query("select distinct u from User u left join u.articles")
  List<User> findAllByEntityGraph();

  @EntityGraph(attributePaths = { "articles" }, type = EntityGraph.EntityGraphType.FETCH)
  @Query("select distinct u from User u left join u.articles")
  Page<User> findAllByPage(Pageable pageable);

  Page<User> findAll(Pageable pageable);

  @EntityGraph(attributePaths = { "articles", "questions" }, type = EntityGraph.EntityGraphType.FETCH)
  @Query("select distinct u from User u left join u.articles")
  List<User> findMultipleCollection();
}
