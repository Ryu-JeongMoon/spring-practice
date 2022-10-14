package com.springanything.jpa.nplus1;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

import com.springanything.config.TestConfig;
import com.springanything.config.p6spy.P6spyConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@Import({ TestConfig.class, P6spyConfig.class })
class UserTest {

	@Autowired
	private EntityManager em;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ArticleRepository articleRepository;

	@BeforeEach
	void setUp() {
		User user1 = new User("user1");
		User user2 = new User("user2");
		User user3 = new User("user3");
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

		em.flush();
		em.clear();
	}

	@DisplayName("EAGER Type은 User를 단일 조회할 때 left outer join 발생")
	@Test
	void userSingleFindTest() {
		User user = userRepository.findById(1L)
			.orElseThrow(RuntimeException::new);

		log.info("username : {}", user.getName());
	}

	@DisplayName("EAGER Type은 User 전체를 찾을 때, 각각의 User와 연관 관계에 있는 Article 조회하며 N+1 발생")
	@Test
	void userFindTestByEager() {
		userRepository.findAll()
			.forEach(user -> log.info("username : {}", user.getName()));
	}

	/**
	 * Lazy 설정된 연관 관계 객체를 조회할 때, 프록시로 가져오고 실제 사용할 때 쿼리를 날린다.
	 */
	@DisplayName("LAZY Type은 User 검색 후 필드 검색을 할 때 N+1 발생")
	@Test
	void userFindTestByLazy() {
		userRepository.findAll()
			.forEach(user -> log.info("users size = {}", user.getArticles().size()));
	}

	@DisplayName("JPQL 쿼리문은 N+1 발생")
	@Test
	void normalJpqlTest() {
		userRepository.findByNameJPQL("user1")
			.ifPresentOrElse(
				it -> log.info("articles size = {}", it.getArticles().size()),
				() -> {
					throw new RuntimeException();
				}
			);
	}

	@DisplayName("fetch join 하면 N+1문제가 발생하지 않는다.")
	@Test
	void fetchJoinTest() {
		userRepository.findAllJPQLFetch()
			.forEach(user -> log.info("users size = {}", user.getArticles().size()));
	}

	/**
	 * firstResult/maxResults specified with collection fetch; applying in memory!
	 */
	@DisplayName("fetch join paging 처리에서 사용해도 N+1문제가 발생")
	@Test
	void pagingFetchJoinTest() {
		PageRequest pageRequest = PageRequest.of(0, 2);

		userRepository.findAllPage(pageRequest)
			.forEach(user -> log.info("users size = {}", user.getArticles().size()));
	}
}