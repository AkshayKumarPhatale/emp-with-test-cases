package com.accenture.empportal.validation;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {

        /*
          if you are using spring security, you can get the currently logged username with following code segment.
          SecurityContextHolder.getContext().getAuthentication().getName()
         */
		/*
		 * return new AuditorAware<String>() {
		 * 
		 * @Override public Optional<String> getCurrentAuditor() { return
		 * Optional.ofNullable("Akshay"); } };
		 */
		
		return ()->{
			return Optional.ofNullable("Akshay");};
    }
}
