package com.springanything.jpa.nplus1;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u left join u.articles where u.firstName = :firstName")
  List<User> findByFirstNameJPQL(String firstName);

  @Query("select u from User u left join u.articles")
  List<User> findAllJPQL();

  @Query("select u from User u left join fetch u.articles")
  List<User> findAllByJPQLFetch();

  @EntityGraph(attributePaths = {"articles"}, type = EntityGraph.EntityGraphType.FETCH)
  @Query("select u from User u left join u.articles")
  List<User> findAllByEntityGraph();

  @Query("select u from User u join fetch u.questions")
  Page<User> findAllQuestionsPageByFetchJoin(Pageable pageable);

  @EntityGraph(attributePaths = {"questions"}, type = EntityGraph.EntityGraphType.FETCH)
  @Query("select u from User u left join u.questions")
  Page<User> findAllQuestionsPageByEntityGraph(Pageable pageable);

  Page<User> findAll(Pageable pageable);

  @EntityGraph(attributePaths = { "articles", "questions" }, type = EntityGraph.EntityGraphType.FETCH)
  @Query("select distinct u from User u left join u.articles")
  List<User> findMultipleCollection();
}
