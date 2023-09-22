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
  @Autowired
  private QuestionRepository questionRepository;

  private Long firstUserId;

  @BeforeEach
  void setUp() {
    // user1 exists twice
    User user0 = User.builder().name("user1").build();
    User user1 = User.builder().name("user1").build();
    User user2 = User.builder().name("user2").build();
    User user3 = User.builder().name("user3").build();
    userRepository.saveAll(List.of(user0, user1, user2, user3));

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

    Question question1 = new Question("question1", user1);
    Question question2 = new Question("question2", user1);
    Question question3 = new Question("question3", user1);
    Question question4 = new Question("question4", user1);
    questionRepository.saveAll(List.of(question1, question2, question3, question4));

    firstUserId = user1.getId();
    flushAndClear();
  }

  /**
   * <pre>{@code
   *     SELECT
   *         U1_0.ID,
   *         A1_0.USER_ID,
   *         A1_0.ID,
   *         A1_0.CONTENT,
   *         A1_0.TITLE,
   *         U1_0.NAME
   *     FROM
   *         USERS U1_0
   *     LEFT JOIN
   *         ARTICLE A1_0
   *             ON U1_0.ID=A1_0.USER_ID
   *     WHERE
   *         U1_0.ID=1
   * }</pre>
   */
  @DisplayName("EAGER Type은 User를 단일 조회할 때 left join 발생")
  @Test
  void findName() {
    User user = userRepository.findById(firstUserId)
      .orElseGet(User::new);

    log.info("username : {}", user.getName());
    log.info("articles : {}", user.getArticles());
  }

  /**
   * <pre>{@code
   *     // User's non-collection fields, 1 time happens
   *     SELECT
   *         U1_0.ID,
   *         U1_0.NAME
   *     FROM
   *         USERS U1_0
   *
   *     // User's collection fields, N times happens
   *     SELECT
   *         A1_0.USER_ID,
   *         A1_0.ID,
   *         A1_0.CONTENT,
   *         A1_0.TITLE
   *     FROM
   *         ARTICLE A1_0
   *     WHERE
   *         A1_0.USER_ID=1, 2, 3 ...
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
      .forEach(user -> log.info("user's articles size = {}", user.getArticles().size()));
  }

  /**
   * <pre>{@code
   *     SELECT
   *         DISTINCT U1_0.ID,
   *         U1_0.NAME
   *     FROM
   *         USERS U1_0
   *     LEFT JOIN
   *         ARTICLE A1_0
   *             ON U1_0.ID=A1_0.USER_ID
   *     WHERE
   *         U1_0.NAME='USER1'
   *
   * -------------------------------------------
   *
   *     SELECT
   *         A1_0.USER_ID,
   *         A1_0.ID,
   *         A1_0.CONTENT,
   *         A1_0.TITLE
   *     FROM
   *         ARTICLE A1_0
   *     WHERE
   *         A1_0.USER_ID=1, 2 ...
   * }</pre>
   */
  @DisplayName("N+1 happens in JPQL")
  @Test
  void findNameByJPQL() {
    userRepository.findByNameJPQL("user1")
      .forEach(user -> log.info("user's articles size = {}", user.getArticles().size()));
  }

  /**
   * <pre>{@code
   *     SELECT
   *         DISTINCT U1_0.ID,
   *         A1_0.USER_ID,
   *         A1_0.ID,
   *         A1_0.CONTENT,
   *         A1_0.TITLE,
   *         U1_0.NAME
   *     FROM
   *         USERS U1_0
   *     LEFT JOIN
   *         ARTICLE A1_0
   *             ON U1_0.ID=A1_0.USER_ID
   * }</pre>
   */
  @DisplayName("N+1 do not happen using fetch join")
  @Test
  void fetchJoin() {
    userRepository.findAllByJPQLFetch()
      .forEach(user -> log.info("user's articles size = {}", user.getArticles().size()));
  }

  @DisplayName("N+1 do not happen using entity graph")
  @Test
  void fetchAllByEntityGraph() {
    userRepository.findAllByEntityGraph()
      .forEach(user -> log.info("user's articles size = {}", user.getArticles().size()));
  }

  /**
   * <pre>{@code
   *     // o.h.h.internal.ast.QueryTranslatorImpl   : HHH000104:
   *     // firstResult/maxResults specified with collection fetch; applying in memory!
   *     SELECT
   *         DISTINCT U1_0.ID,
   *         A2_0.USER_ID,
   *         A2_0.ID,
   *         A2_0.CONTENT,
   *         A2_0.TITLE,
   *         U1_0.NAME
   *     FROM
   *         USERS U1_0
   *     LEFT JOIN
   *         ARTICLE A1_0
   *             ON U1_0.ID=A1_0.USER_ID
   *     LEFT JOIN
   *         ARTICLE A2_0
   *             ON U1_0.ID=A2_0.USER_ID
   *
   * --------------------------------------------
   *
   *     SELECT
   *         COUNT(DISTINCT U1_0.ID)
   *     FROM
   *         USERS U1_0
   *     LEFT JOIN
   *         ARTICLE A1_0
   *             ON U1_0.ID=A1_0.USER_ID
   * }</pre>
   */
  @DisplayName("fetch join paging 처리에서 사용해도 N+1문제가 발생")
  @Test
  void findAllByFetchJoinPage() {
    PageRequest pageRequest = PageRequest.of(0, 2);

    userRepository.findAllByPage(pageRequest)
      .forEach(user -> log.info("user's articles = {}", user.getArticles()));
  }

  /**
   * <pre>{@code
   *     // user query happens once
   *     SELECT
   *         U1_0.ID,
   *         U1_0.NAME
   *     FROM
   *         USERS U1_0
   *
   * ----------------------------------
   *     // questions query happens once
   *     SELECT
   *         Q1_0.USER_ID,
   *         Q1_0.ID,
   *         Q1_0.TITLE
   *     FROM
   *         QUESTION Q1_0
   *     WHERE
   *         Q1_0.USER_ID IN (1,2,3,4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL)
   *
   * }</pre>
   */
  @DisplayName("'in query' happens instead of N+1 problem by @BatchSize")
  @Test
  void findAllByBatchSize() {
    userRepository.findAll()
      .forEach(user -> log.info("user's questions size = {}", user.getQuestions().size()));
  }

  /**
   * more than one bag as List type fetching, <u>MultipleBagFetchException</u> happens
   * <p>org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags: [com.springanything.jpa.nplus1.User.articles, com.springanything.jpa.nplus1.User.questions]; nested exception is java.lang.IllegalArgumentException: org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags: [com.springanything.jpa.nplus1.User.articles, com.springanything.jpa.nplus1.User.questions]</p>
   * <pre>{@code
   * ----------------------------------
   *     // using Set type fetching, MultipleBagFetchException doesn't happen
   *     SELECT
   *         DISTINCT USER0_.ID AS ID1_30_0_,
   *         ARTICLES1_.ID AS ID1_2_1_,
   *         QUESTIONS2_.ID AS ID1_23_2_,
   *         USER0_.NAME AS NAME2_30_0_,
   *         ARTICLES1_.CONTENT AS CONTENT2_2_1_,
   *         ARTICLES1_.TITLE AS TITLE3_2_1_,
   *         ARTICLES1_.USER_ID AS USER_ID4_2_1_,
   *         ARTICLES1_.USER_ID AS USER_ID4_2_0__,
   *         ARTICLES1_.ID AS ID1_2_0__,
   *         QUESTIONS2_.TITLE AS TITLE2_23_2_,
   *         QUESTIONS2_.USER_ID AS USER_ID3_23_2_,
   *         QUESTIONS2_.USER_ID AS USER_ID3_23_1__,
   *         QUESTIONS2_.ID AS ID1_23_1__
   *     FROM
   *         USERS USER0_
   *     LEFT OUTER JOIN
   *         ARTICLE ARTICLES1_
   *             ON USER0_.ID=ARTICLES1_.USER_ID
   *     LEFT OUTER JOIN
   *         QUESTION QUESTIONS2_
   *             ON USER0_.ID=QUESTIONS2_.USER_ID
   *
   * }</pre>
   */
  @DisplayName("join fetch collections more than one")
  @Test
  void findDoubleCollection() {
    userRepository.findMultipleCollection()
      .forEach(user -> {
        log.info("user's articles size = {}", user.getArticles().size());
        log.info("user's questions size = {}", user.getQuestions().size());
      });
  }
}
