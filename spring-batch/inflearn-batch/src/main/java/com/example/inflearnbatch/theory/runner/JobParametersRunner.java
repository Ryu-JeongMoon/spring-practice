package com.example.inflearnbatch.theory.runner;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;

// @Component
public class JobParametersRunner extends BatchRunner {

	public JobParametersRunner(Job parametersJob, JobLauncher jobLauncher) {
		super(parametersJob, jobLauncher);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
			.addDate("date", new Date())
			.addString("name", "batch")
			.addLong("version", 1L)
			.addDouble("minor-version", 0.1)
			.toJobParameters();

		jobLauncher.run(job, jobParameters);
	}
}

/*
1. Application 구동 시 Program Arguments 넘기는 방법
  - Intellij 사용 시, Program Arguments에 name=panda version(long)=1L date(date)=2022/10/16 minor-version(double)=0.1 입력
  - iterm(zsh) 사용 시, 'name=panda' 'version(long)=1L' 'date(date)=2022/10/16' 'minor-version(double)=0.1' 입력

zsh: no matches found: version(long)=1L
각 파라미터 별 작은 따옴표 붙여줘야 함, 안 붙이면 인식을 못함

2. ApplicationRunner 구현하는 컴포넌트 생성, JobParameters 직접 만들어 인자로 넘겨줌
  - application.yml 에서 spring.batch.job.enabled: false 설정
 */
