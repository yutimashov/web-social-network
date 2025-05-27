package com.getjavajob.accountservice.dao.cofing;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.getjavajob.accountservice.dao"})
@EntityScan(basePackages = {"com.getjavajob.training.timashovy.socialnetwork.domain"})
public class DataConfig {
}
