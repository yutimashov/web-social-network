package com.getjavajob.training.timashovy.socialnetwork.dao.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.getjavajob.training.timashovy.socialnetwork.dao.interfaces"})
@EntityScan(basePackages = {"com.getjavajob.training.timashovy.socialnetwork.domain"})
public class PersistenceConfig {
}
