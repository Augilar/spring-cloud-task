/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.task.batch.configuration;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Provides auto configuration for the
 * {@link org.springframework.cloud.task.batch.handler.TaskJobLauncherCommandLineRunner}.
 *
 * @author Glenn Renfro
 */
@Configuration
@Conditional(JobLaunchCondition.class)
@EnableConfigurationProperties(TaskBatchProperties.class)
@AutoConfigureBefore(BatchAutoConfiguration.class)
public class TaskJobLauncherAutoConfiguration {

	@Autowired
	private TaskBatchProperties properties;

	@Bean
	public TaskJobLauncherCommandLineRunnerFactoryBean jobLauncherCommandLineRunner(
			JobLauncher jobLauncher, JobExplorer jobExplorer, List<Job> jobs,
			JobRegistry jobRegistry, JobRepository jobRepository,
			BatchProperties batchProperties) {
		TaskJobLauncherCommandLineRunnerFactoryBean taskJobLauncherCommandLineRunnerFactoryBean;
		taskJobLauncherCommandLineRunnerFactoryBean = new TaskJobLauncherCommandLineRunnerFactoryBean(
				jobLauncher, jobExplorer, jobs, this.properties, jobRegistry,
				jobRepository, batchProperties);

		return taskJobLauncherCommandLineRunnerFactoryBean;
	}

}
