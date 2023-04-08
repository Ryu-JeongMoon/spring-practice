package com.springanything.jpa.nplus1;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.springanything.AbstractRepositoryTest;

class UserTest extends AbstractRepositoryTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ArticleRepository articleRepository;

  private Long firstUserId;

  @BeforeEach
  void setUp() {
    User user1 = User.builder().name("user1").build();
    User user2 = User.builder().name("user2").build();
    User user3 = User.builder().name("user3").build();
    userRepository.saveAll(List.of(user1, user2, user3));

    Article article1 = new Article("title1", "content1", user1);
    Article article2 = new Article("title2", "content2", user1);
    Article article3 = new Article("title3", "content3", user1);
    Article article4 = new Article("title4", "content4", user1);
    Article article5 = new Article("title5", "content5", user2);
    Article article6 = new Article("title6", "content6", user2);
    Article article7 = new Article("title7", "content7", user3);
    Article article8 = new Article("title8", "content8", user3);
    Article article9 = new Article("title9", "content9", user3);
    articleRepository.saveAll(List.of(article1, article2, article3, article4, article5, article6, article7, article8, article9));

    firstUserId = user1.getId();
    flushAndClear();
  }

  /**
   * <pre>{@code
   *  SELECT
   *         USER0_.ID AS ID1_26_0_,
   *         USER0_.NAME AS NAME2_26_0_,
   *         ARTICLES1_.USER_ID AS USER_ID4_2_1_,
   *         ARTICLES1_.ID AS ID1_2_1_,
   *         ARTICLES1_.ID AS ID1_2_2_,
   *         ARTICLES1_.CONTENT AS CONTENT2_2_2_,
   *         ARTICLES1_.TITLE AS TITLE3_2_2_,
   *         ARTICLES1_.USER_ID AS USER_ID4_2_2_
   *     FROM
   *         USERS USER0_
   *     LEFT OUTER JOIN
   *         ARTICLE ARTICLES1_
   *             ON USER0_.ID=ARTICLES1_.USER_ID
   *     WHERE
   *         USER0_.ID=?
   * }</pre>
   */
  @DisplayName("EAGER Type은 User를 단일 조회할 때 left outer join 발생")
  @Test
  void findName() {
    User user = userRepository.findById(firstUserId)
      .orElseGet(User::new);

    log.info("username : {}", user.getName());
  }

  /**
   * <pre>{@code
   *     // User's non-collection fields, 1 time happens
   *     SELECT
   *         USER0_.ID AS ID1_26_,
   *         USER0_.NAME AS NAME2_26_
   *     FROM
   *         USERS USER0_
   *
   *     // User's collection fields, N times happens
   *     SELECT
   *         ARTICLES0_.USER_ID AS USER_ID4_2_0_,
   *         ARTICLES0_.ID AS ID1_2_0_,
   *         ARTICLES0_.ID AS ID1_2_1_,
   *         ARTICLES0_.CONTENT AS CONTENT2_2_1_,
   *         ARTICLES0_.TITLE AS TITLE3_2_1_,
   *         ARTICLES0_.USER_ID AS USER_ID4_2_1_
   *     FROM
   *         ARTICLE ARTICLES0_
   *     WHERE
   *         ARTICLES0_.USER_ID=3
   * }</pre>
   */
  @DisplayName("EAGER Type은 User 전체를 찾을 때, 각각의 User와 연관 관계에 있는 Article 조회하며 N+1 발생")
  @Test
  void findAllByEager() {
    userRepository.findAll()
      .forEach(user -> log.info("username : {}", user.getName()));
  }

  /**
   * <pre>
   * using fetch type LAZY
   * first, fetch entity only in select statement and related field as proxy
   * second, n times query will be executed when accessing the related field</pre>
   */
  @DisplayName("LAZY Type은 User 검색 후 필드 검색을 할 때 N+1 발생")
  @Test
  void findAllByLazy() {
    userRepository.findAll()
      .forEach(user -> log.info("users size = {}", user.getArticles().size()));
  }

  /**
   * <pre>{@code
   *     SELECT
   *         DISTINCT USER0_.ID AS ID1_26_,
   *         USER0_.NAME AS NAME2_26_
   *     FROM
   *         USERS USER0_
   *     LEFT OUTER JOIN
   *         ARTICLE ARTICLES1_
   *             ON USER0_.ID=ARTICLES1_.USER_ID
   *     WHERE
   *         USER0_.NAME='USER1'
   *
   * -------------------------------------------
   *
   *     SELECT
   *         ARTICLES0_.USER_ID AS USER_ID4_2_0_,
   *         ARTICLES0_.ID AS ID1_2_0_,
   *         ARTICLES0_.ID AS ID1_2_1_,
   *         ARTICLES0_.CONTENT AS CONTENT2_2_1_,
   *         ARTICLES0_.TITLE AS TITLE3_2_1_,
   *         ARTICLES0_.USER_ID AS USER_ID4_2_1_
   *     FROM
   *         ARTICLE ARTICLES0_
   *     WHERE
   *         ARTICLES0_.USER_ID=1
   * }</pre>
   */
  @DisplayName("N+1 happens in JPQL")
  @Test
  void findNameByJPQL() {
    userRepository.findByNameJPQL("user1")
      .ifPresentOrElse(
        it -> log.info("articles size = {}", it.getArticles().size()),
        () -> {
          throw new RuntimeException();
        }
      );
  }

  /**
   * <pre>{@code
   *     SELECT
   *         DISTINCT USER0_.ID AS ID1_26_0_,
   *         ARTICLES1_.ID AS ID1_2_1_,
   *         USER0_.NAME AS NAME2_26_0_,
   *         ARTICLES1_.CONTENT AS CONTENT2_2_1_,
   *         ARTICLES1_.TITLE AS TITLE3_2_1_,
   *         ARTICLES1_.USER_ID AS USER_ID4_2_1_,
   *         ARTICLES1_.USER_ID AS USER_ID4_2_0__,
   *         ARTICLES1_.ID AS ID1_2_0__
   *     FROM
   *         USERS USER0_
   *     LEFT OUTER JOIN
   *         ARTICLE ARTICLES1_
   *             ON USER0_.ID=ARTICLES1_.USER_ID
   * }</pre>
   */
  @DisplayName("N+1 do not happen using fetch join")
  @Test
  void fetchJoin() {
    userRepository.findAllByJPQLFetch()
      .forEach(user -> log.info("users size = {}", user.getArticles().size()));
  }

  @DisplayName("N+1 do not happen using entity graph")
  @Test
  void fetchAllByEntityGraph() {
    userRepository.findAllByEntityGraph()
      .forEach(user -> log.info("users size = {}", user.getArticles().size()));
  }

  /**
   * <pre>{@code
   * // o.h.h.internal.ast.QueryTranslatorImpl   : HHH000104:
   * // firstResult/maxResults specified with collection fetch; applying in memory!
   *
   *     SELECT
   *         DISTINCT USER0_.ID AS ID1_26_0_,
   *         ARTICLES1_.ID AS ID1_2_1_,
   *         USER0_.NAME AS NAME2_26_0_,
   *         ARTICLES1_.CONTENT AS CONTENT2_2_1_,
   *         ARTICLES1_.TITLE AS TITLE3_2_1_,
   *         ARTICLES1_.USER_ID AS USER_ID4_2_1_,
   *         ARTICLES1_.USER_ID AS USER_ID4_2_0__,
   *         ARTICLES1_.ID AS ID1_2_0__
   *     FROM
   *         USERS USER0_
   *     LEFT OUTER JOIN
   *         ARTICLE ARTICLES1_
   *             ON USER0_.ID=ARTICLES1_.USER_ID
   *
   * --------------------------------------------
   *
   *     SELECT
   *         COUNT(DISTINCT USER0_.ID) AS COL_0_0_
   *     FROM
   *         USERS USER0_
   *     LEFT OUTER JOIN
   *         ARTICLE ARTICLES1_
   *             ON USER0_.ID=ARTICLES1_.USER_ID
   * }</pre>
   */
  @DisplayName("fetch join paging 처리에서 사용해도 N+1문제가 발생")
  @Test
  void findAllByFetchJoinPage() {
    PageRequest pageRequest = PageRequest.of(0, 2);

    userRepository.findAllByPage(pageRequest)
      .forEach(user -> log.info("users size = {}", user.getArticles().size()));
  }

  /**
   * <pre>{@code
   *     // user query happens once
   *     SELECT
   *         USER0_.ID AS ID1_26_,
   *         USER0_.NAME AS NAME2_26_
   *     FROM
   *         USERS USER0_
   *
   * ----------------------------------
   *     // articles query happens once
   *     SELECT
   *         ARTICLES0_.USER_ID AS USER_ID4_2_1_,
   *         ARTICLES0_.ID AS ID1_2_1_,
   *         ARTICLES0_.ID AS ID1_2_0_,
   *         ARTICLES0_.CONTENT AS CONTENT2_2_0_,
   *         ARTICLES0_.TITLE AS TITLE3_2_0_,
   *         ARTICLES0_.USER_ID AS USER_ID4_2_0_
   *     FROM
   *         ARTICLE ARTICLES0_
   *     WHERE
   *         ARTICLES0_.USER_ID IN (
   *             1, 2, 3
   *         )
   * }</pre>
   */
  @DisplayName("'in query' happens instead of N+1 problem by @BatchSize")
  @Test
  void findAllByBatchSize() {
    userRepository.findAll()
      .forEach(user -> log.info("users size = {}", user.getArticles().size()));
  }
}