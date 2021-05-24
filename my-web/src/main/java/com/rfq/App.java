package com.rfq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableJpaAuditing
public class App { 
	public static void main(String[] args) {
		
		SpringApplication.run(App.class, args);
	}

	@Configuration
    public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	http.headers().frameOptions().disable();
            http.authorizeRequests()
            	.anyRequest().permitAll()
                .and().csrf().disable();        
         }
    }
	
}



