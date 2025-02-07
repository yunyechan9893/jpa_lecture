package jpabook.jpashop.api.v1.config;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

  @Bean
  public Hibernate5JakartaModule hibernate5Module() {
    return new Hibernate5JakartaModule();
  }

}
