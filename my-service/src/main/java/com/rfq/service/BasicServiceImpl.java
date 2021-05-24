package com.rfq.service;

import com.rfq.entity.ServiceResult;
import com.rfq.entity.sys.SysRoler;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;


public abstract class BasicServiceImpl implements BasicService {

	private ServiceResult<SysRoler> getLoginUserBySession() {
		
		SysRoler user =(SysRoler) SecurityUtils.getSubject().getPrincipal();
		if(user!=null)
			return new ServiceResult<SysRoler>(user);
		else
			return null;
	}

	@Override
	public ServiceResult<SysRoler> getLoginUser() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String token=request.getParameter("token");
		//如果没有token,从安全框架中获取
		if(StringUtils.isBlank(token))
			return getLoginUserBySession();
		else {

				return new ServiceResult<SysRoler>();
			}
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
