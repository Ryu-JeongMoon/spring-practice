package com.example.inflearnbatch.batch;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;

import com.example.inflearnbatch.TestBatchConfig;
import com.example.inflearnbatch.domain.Panda;
import com.example.inflearnbatch.domain.PandaBackup;
import com.example.inflearnbatch.domain.repo.PandaBackupRepository;
import com.example.inflearnbatch.domain.repo.PandaRepository;

@SpringBootTest(classes = { TestBatchConfig.class, PandaBackupBatch.class })
@SpringBatchTest
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
class PandaBackupBatchTest {

  private static final DateTimeFormatter FORMATTER = ofPattern("yyyy-MM-dd'T'HH:mm:ss");

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Autowired
  private PandaRepository pandaRepository;

  @Autowired
  private PandaBackupRepository pandaBackupRepository;

  @BeforeEach
  void setUp() {
    for (int i = 0; i < 20; i++) {
      Panda aPanda = Panda.builder()
        .name("a" + i)
        .build();

      Panda bPanda = Panda.builder()
        .name("b" + i)
        .build();

      pandaRepository.save(aPanda);
      pandaRepository.save(bPanda);
    }
  }

  @DisplayName("백업 테스트")
  @Test
  @Commit
  void backup() throws Exception {
    // given
    JobParameters jobParameters = new JobParametersBuilder()
      .addString("date", LocalDateTime.now().format(FORMATTER))
      .toJobParameters();

    // when
    JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

    // then
    List<PandaBackup> backups = pandaBackupRepository.findAll();

    assertAll(
      () -> assertThat(backups.size()).isNotZero(),
      () -> assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED)
    );
  }
}
