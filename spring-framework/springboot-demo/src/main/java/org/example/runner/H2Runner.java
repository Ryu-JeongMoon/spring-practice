package org.example.runner;

import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class H2Runner implements ApplicationRunner {

  private final DataSource dataSource;
  private final JdbcTemplate jdbcTemplate;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    try (Connection connection = dataSource.getConnection()) {
      System.out.println("connection.getMetaData().getURL() = " + connection.getMetaData().getURL());
      System.out.println("connection.getMetaData().getUserName() = " + connection.getMetaData().getUserName());

      Statement statement = connection.createStatement();
      String sql = "create table user (id integer not null primary key, name varchar(255));";
      statement.executeUpdate(sql);
    }

    jdbcTemplate.execute("insert into user values(1, 'panda')");
  }
}
