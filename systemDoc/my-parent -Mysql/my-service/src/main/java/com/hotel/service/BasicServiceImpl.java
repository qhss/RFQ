package com.hotel.service;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hotel.dao.sys.OrganizationRepository;
import com.hotel.dao.sys.UserRepository;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.User;
import com.hotel.entity.sys.UserToken;
import com.hotel.service.sys.UserService;
import com.hotel.service.sys.UserTokenService;


public abstract class BasicServiceImpl implements BasicService {
	
	//private static Logger logger = LogManager.getLogger(BasicServiceImpl.class);
	
	@Autowired
	UserTokenService userTokenService;
	
	@Autowired
	UserService userService;
	

	@Resource(name="userRepository")
	UserRepository userRepository;

	@Resource(name="organizationRepository")
	OrganizationRepository organizationRepository;
	
	@Override
	public ServiceResult<User> getLoginUser() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String token=request.getParameter("token");
		//如果没有token,从安全框架中获取
		if(StringUtils.isBlank(token))
			return getLoginUserBySession();
		else {
			ServiceResult<UserToken> userToken=userTokenService.findByToken(token);
			if(userToken.isSuccess()) {
				ServiceResult<User> uServiceResult=userService.findById(userToken.getData().getUserId());
				return uServiceResult;
			}else {
				return new ServiceResult<User>(true,userToken.getErrorMessage());
			}
		}
		
	}

	private ServiceResult<User> getLoginUserBySession() {
		
		User user =(User)SecurityUtils.getSubject().getPrincipal();
		if(user!=null)
			return new ServiceResult<User>(user);
		else
			return null;
	}

	/**
	 * List构造Page
	 * @param list
	 * @param pageable
	 * @param <T>
	 * @return
	 */
	public <T> Page<T> listConvertToPage(List<T> list, Pageable pageable) {
		int start = (int)pageable.getOffset();
		int end = (start + pageable.getPageSize()) > list.size() ? list.size() : ( start + pageable.getPageSize());
		return new PageImpl<>(list.subList(start, end), pageable, list.size());
	}

	public int getCurrentMonthLastDay(){
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
	
	
}
