package com.greatlaboratory.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // jpa auditing기능 활성화
public class JpaConfig {
}
