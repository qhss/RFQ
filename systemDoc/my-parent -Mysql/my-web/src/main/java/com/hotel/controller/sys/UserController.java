package com.hotel.controller.sys;

import java.util.Calendar;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.config.Config;
import com.hotel.controller.BasicController;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.Result;
import com.hotel.entity.sys.User;
import com.hotel.service.sys.UserService;



/**
 * 用户相关操作控制类
 * @author duanqs
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BasicController {
	private Logger logger = LogManager.getLogger(UserController.class);
	
	@RequestMapping("user")
	public String user() {
		return "sys/user";
	}
	
	@Resource(name = "userService")
	UserService userService;

	@Resource
	Config config;
	

	@RequestMapping("/page")
	@ResponseBody
	public Map<String, Object> queryUser(HttpServletRequest request, String keyword,String departId,String pId) {
		InitDataTablesParams(request);
		Page<User> result =userService.queryOnePage(keyword,departId,pId,this.getPageIndex(),this.getPageRow(),this.getSort());
		Map<String, Object> mapresult=getResultMap(result);
		
		return mapresult;
		
	}

	/**
	 * 根据用户ID查询
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/queryUserById")
	@ResponseBody
	public User queryUserById(String userId) {
		ServiceResult<User> result=userService.findById(userId);
		if(result.isSuccess())
			return result.getData();
		else
			return null;
	}

	/**
	 * 新增时查询用户名是否存在
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/queryUserByLoginName")
	@ResponseBody
	public String queryUserByLoginName(String loginName) {
		ServiceResult<User> result=userService.queryUserByLoginName(loginName);
		if(result.isSuccess())
			return result.getData().getId();
		else
			return "";
	}
	
	/**
	 * 判断登录账号是否存在
	 * @param userId
	 * @param loginName
	 * @return
	 */
	@RequestMapping("/loginNameExist")
	@ResponseBody
	public Result loginNameExist(String userId,String loginName) {
		Result result=new Result();
		ServiceResult<Boolean> existResult=new ServiceResult<Boolean>();
		if(StringUtils.isBlank(userId))
			existResult=userService.loginNameExist(loginName);
		else
			existResult=userService.loginNameExistExpectUserId(loginName,userId);
		
		if(existResult.isSuccess())
			result.setRightResult(existResult.getData());
		else
			result.setErrorResult("", "判断登录账号是否存在出错");
		
		return result;
	}

	/**
	 * 修改或增加
	 * 
	 * @param user
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/addOrUpdateUser")
	@ResponseBody
	public Result addOrUpdateUser(String id,String userName,String loginName,String password,
			Integer sex,String phone,String departId, 
			@RequestParam(value = "roleId[]") Object[] roleId) {
		Result result=new Result();
		if(StringUtils.isBlank(userName)) {
			result.setErrorResult("", "用户姓名不能为空");
		}
		if(StringUtils.isBlank(departId)) {
			result.setErrorResult("", "部门不能为空,请选择部门");
		}
		if (result.getErrorMessage()!=null&&result.getErrorMessage()!=""){
			result.setErrorResult("", result.getErrorMessage());
			return result;
		}
		ServiceResult<Integer> updateResult=userService.addOrUpdate(id,userName,loginName,password,
				sex,phone,departId, roleId);
		if(updateResult.isSuccess())
			result.setRightResult(updateResult.getData());
		else
			result.setErrorResult("", updateResult.getErrorMessage());
		
		return result;
	}

	/**
	 * 修改状态
	 * @param id
	 * @param state
	 * @return
	 */
	@RequestMapping("/state")
	@ResponseBody
	public Result UpdateState(String id,int state) {
		Result result=new Result();
		try {
			ServiceResult<Integer> data =userService.UpdateState(id,state);
			
			if(data.isSuccess())
				result.setRightResult(data.getData());
			else
				result.setErrorResult("", data.getErrorMessage());
			
			return result;
		}catch (Exception e) {
			logger.error(e);
			result.setErrorResult("", e.getMessage());
			return result;
		}
	}
	
	
	/**
	 * 删除单条数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Result Delete(String id) {
		Result result=new Result();
		try {
			ServiceResult<Integer> data =userService.Delete(id);
			
			if(data.isSuccess())
				result.setRightResult(data.getData());
			else
				result.setErrorResult("", data.getErrorMessage());
			
			return result;
			
		}catch (Exception e) {
			logger.error(e);
			result.setErrorResult("", e.getMessage());
			return result;
		}
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/batchdel")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids[]") String[] ids) {
		Result result=new Result();
		try {
			ServiceResult<Integer> data= userService.batchDelete(ids);
			
			if(data.isSuccess())
				result.setRightResult(data.getData());
			else
				result.setErrorResult("", data.getErrorMessage());
			
			return result;
			
		}catch (Exception e) {
			logger.error(e);
			result.setErrorResult("", e.getMessage());
			return result;
		}
	}
	
	

	public int getCurrentMonthLastDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	
	/**
	 * 修改个人资料
	 * 
	 * @param user
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/updateUser")
	@ResponseBody
	public Result updateUser(User user) {
		Result result=new Result();
		try {
			
			String userId=userService.updateUser(user);
			result.setRightResult(userId);
			
		}catch(Exception ex) {
			result.setRightResult(ex);
		}
		return result;
	}
	
	
//	@RequestMapping("/findCurrUser")
//	@ResponseBody
//	public User findCurrUser() {
//
//		return userService.findCurrUser();
//
//	}
//


	

//	/**
//	 * 批量修改
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/batchUpdate")
//	@ResponseBody
//	public int batchUpdate(@RequestParam(value = "userIds[]") Object[] userIds) {
//		List<User> users = new ArrayList<User>();
//		User user = null;
//		for (Object obj : userIds) {
//			user = new User();
//			user.setId(obj.toString());
//			user.setState(2);
//			users.add(user);
//		}
//		return userService.batchUpdate(users);
//	}

	
	
//	/**
//	 * 查询用户所有档案
//	 * 
//	 * @param user
//	 * @return
//	 */
//	@RequestMapping("/queryDocument")
//	@ResponseBody
//	public List<Map<String, Object>> queryDocument(String openId) {
//		return userService.queryDocument(openId);
//	}
//
//	
//
//	@RequestMapping("/queryUserByName")
//	public @ResponseBody Result queryUserByName(String name) {
//		try {
//
//			List<Map<String, Object>> listUser = userService.queryUserByName(name);
//			return new Result(Result.STATUS_OK, listUser);
//
//		} catch (Exception e) {
//			logger.error(e);
//
//			return new Result(Result.STATUS_FAIL, Result.Default, e.getMessage());
//		}
//	}
//
//	
	
	

//	/**
//	 * 用户反馈
//	 */
//	@RequestMapping("/feedBack")
//	@ResponseBody
//	public void feedBack(HttpServletRequest request,String content,String user,String phone) {
//		
//		try {
//			String token=config.getDingTalkToken();
//			StringBuilder sBuilder=new StringBuilder();
//			User userLogin=GetLoginUser();
//			sBuilder
//				.append("系统:").append(request.getServerName()).append("\r")
//				.append("反馈者:").append(user).append("\r")
//				.append("反馈者电话:").append(phone).append("\r")
//				.append("登录账号:").append(userLogin.getUserName()).append("\r")
//				.append("医馆名称:").append(userLogin.getHospitalName()).append("\r")
//				.append("医馆标示:").append(userLogin.getHospitalId()).append("\r")
//				.append("问题描述:").append(content)
//				;
//			String message=sBuilder.toString();
//			
//			if(!StringUtils.isEmpty(token)) {
//				DingTalkUtil.SendMessage(token, message, "", "");
//			
//				//发送运营消息
//				SendOperationMessage(message);
//			}
//			
//			String mailto=config.getTrelloFeedBackMail();
//			if(!StringUtils.isEmpty(mailto)) {
//				//发送到trello
//				SimpleMailMessage mailMessage = new SimpleMailMessage();
//				mailMessage.setFrom(config.getTrelloFeedBackMailFrom());
//				mailMessage.setTo(mailto);
//				mailMessage.setSubject("客户："+user+"反馈问题");
//				mailMessage.setText(message);
//				
//		        mailSender.send(mailMessage);
//			}
//			
//		}catch (Exception e) {
//			logger.error(e);
//		}
//	}
	
	
}
