package com.getjavajob.dataprocessingservice.account.web.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"com.getjavajob.training.timashovy.socialnetwork.domain"})
public class DataConfig {
}
