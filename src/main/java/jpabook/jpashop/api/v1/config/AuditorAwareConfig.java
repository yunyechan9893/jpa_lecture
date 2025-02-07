package jpabook.jpashop.api.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AuditorAwareConfig {

  @Bean
  public AuditorAware<String> auditorProvider() {
    return () -> Optional.of(UUID.randomUUID().toString());
  }

}
