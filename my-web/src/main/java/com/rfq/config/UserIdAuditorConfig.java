package com.rfq.config;

import java.util.Optional;

import com.rfq.entity.sys.SysRoler;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class UserIdAuditorConfig implements AuditorAware<String> {

	public Optional<String> getCurrentAuditor() {
		if(SecurityUtils.getSubject()==null)
			return null;
		
		if(SecurityUtils.getSubject().getPrincipal()==null)
			return null;
		
		SysRoler user =(SysRoler) SecurityUtils.getSubject().getPrincipal();
		Optional<String> val= Optional.ofNullable(Optional.of(user.getId()).toString());
		return val;
	}

}
