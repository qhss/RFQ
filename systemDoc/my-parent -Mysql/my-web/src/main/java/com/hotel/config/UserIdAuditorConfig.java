package com.hotel.config;

import java.util.Optional;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import com.hotel.entity.sys.User;

@Configuration
public class UserIdAuditorConfig implements AuditorAware<String> {

	public Optional<String> getCurrentAuditor() {
		if(SecurityUtils.getSubject()==null)
			return null;
		
		if(SecurityUtils.getSubject().getPrincipal()==null)
			return null;
		
		User user =(User)SecurityUtils.getSubject().getPrincipal();
		Optional<String> val=Optional.of(user.getId());
		return val;
	}

}
