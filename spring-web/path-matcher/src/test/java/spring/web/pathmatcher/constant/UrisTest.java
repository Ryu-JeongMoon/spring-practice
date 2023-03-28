package spring.web.pathmatcher.constant;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

class UrisTest {

  @ParameterizedTest
  @ValueSource(strings = { "/ko", "/en", "/jp", "/uk" })
  void test(String localePath) {
    // given
    PathPatternParser parser = new PathPatternParser();
    PathPattern pattern = parser.parse(Uris.LOCALE);

    // when
    boolean matches = pattern.matches(PathContainer.parsePath(localePath));

    // then
    assertThat(matches).isTrue();
  }
}
