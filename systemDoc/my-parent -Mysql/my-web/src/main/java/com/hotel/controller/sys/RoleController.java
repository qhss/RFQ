package com.hotel.controller.sys;

import java.util.List;
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

import com.hotel.controller.BasicController;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.Menu;
import com.hotel.entity.sys.Result;
import com.hotel.entity.sys.Role;
import com.hotel.service.sys.RoleService;
import com.hotel.util.PermissionsUtil;



/**
 * 
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BasicController {
	private Logger logger = LogManager.getLogger(RoleController.class);
	
	@RequestMapping("role")
	public String role() {
		return "sys/role";
	}
	
	

	@Resource(name = "roleService")
	RoleService roleService;

	/**
	 * 分页查询角色列表
	 * @param request
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	public Map<String, Object> queryRoles(HttpServletRequest request, String keyword) {
		
		InitDataTablesParams(request);
		Page<Role> result =roleService.queryOnePage(keyword,this.getPageIndex(),this.getPageRow(),this.getSort());
		Map<String, Object> mapresult=getResultMap(result);
		
		return mapresult;
		
	}
	
	/**
	 * 根据用户ID查询
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/id")
	@ResponseBody
	public Role queryUserById(String roleId) {
		ServiceResult<Role> result=roleService.findById(roleId);
		if(result.isSuccess())
			return result.getData();
		else
			return null;
	}
	
	
	
	/**
	 * 查询所有角色
	 * @return
	 */
	@RequestMapping("/all")
	@ResponseBody
	public Result query() {
		List<Role> list =roleService.findAll();
		Result result=new Result();
		result.setRightResult(list);
		return result;
	}
	

	/**
	 * 查询权限树
	 * 
	 * @return
	 */
	@RequestMapping("/allPermissions")
	@ResponseBody
	public List<Map<String, Object>> queryAllMenus() {
		
		List<Menu> menus = roleService.queryAllMenus();
		PermissionsUtil permissions = new PermissionsUtil(menus);
		return permissions.buildTree();
	}
	

	/**
	 * 查询角色所对应的权限
	 */
	@RequestMapping("/queryPermissionsByRole")
	@ResponseBody
	public List<Integer> queryPermissionsByRole(String roleId) {
		List<Integer> menuId = roleService.queryPermissionsByRole(roleId);
		return menuId;
	}
	
	/**
	 * 更新角色所对应的权限
	 */
	@RequestMapping("/updatePermissions")
	@ResponseBody
	public Result updatePermissions(String roleId, @RequestParam(value = "menuIds[]") Object[] menuIds) {
		Result result=new Result();
		ServiceResult<Integer> serviceResult=roleService.updatePermissions(roleId, menuIds);
		if(serviceResult.isSuccess())
			result.setRightResult(serviceResult.getData());
		else
			result.setErrorResult("", serviceResult.getErrorMessage());
		
		return result;
	}
	
	
	
	/**
	 * 新增时查询角色名称是否存在
	 * @param roleName
	 * @return
	 */
	@RequestMapping("/exist")
	@ResponseBody
	public Result queryByName(String roleName, String roleId) {
		Result result=new Result();
		ServiceResult<Boolean> existResult=new ServiceResult<Boolean>();
		if(StringUtils.isBlank(roleId))
			existResult=roleService.roleNameExist(roleName);
		else
			existResult=roleService.roleNameExistExpectRoleId(roleName,roleId);
		
		if(existResult.isSuccess())
			result.setRightResult(existResult.getData());
		else
			result.setErrorResult("", "判断登录账号是否存在出错");
		
		return result;
	}
	
	
	/**
	 * 修改或增加角色
	 * 
	 * @param user
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Result addOrUpdate(String id, String name,String description ) {
		Result result=new Result();
		ServiceResult<String> roleResult=new ServiceResult<String>();
		// 当前用户的所属医馆
		roleResult = roleService.save(id,name,description);
		if(roleResult.isSuccess())
			result.setRightResult(roleResult.getData());
		else
			result.setErrorResult("", roleResult.getErrorMessage());
		
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
			ServiceResult<Integer> data =roleService.UpdateState(id,state);
			
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
			ServiceResult<Integer> data =roleService.Delete(id);
			
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
			ServiceResult<Integer> data= roleService.batchDelete(ids);
			
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
	
//	@RequestMapping("/queryRole")
//	@ResponseBody
//	public List<Role> queryRole(Role role) {
//		role.setIs_enable(0);
//		role.setIs_admin(0);// 取消显示超级管理员,超级管理员只能是admin
//		role.setHospitalId(GetLoginUser().getHospitalId());
//		return roleService.query(role);
//	}
//
//	
//	/**
//	 * 根据用户ID查询
//	 * 
//	 * @param userId
//	 * @return
//	 */
//	@RequestMapping("/queryRoleById")
//	@ResponseBody
//	public Role queryRoleById(String roleId) {
//		return roleService.queryById(Role.class, roleId);
//	}
//
//	/**
//	 * 禁用/启用用户
//	 * 
//	 * @param user
//	 * @return
//	 */
//	@RequestMapping("/openOrDisableRole")
//	@ResponseBody
//	public int openOrDisableRole(Role role) {
//		int i = roleService.updateEntity(role);
//		return i;
//	}
//
//	/**
//	 * 删除用户
//	 * 
//	 * @param user
//	 * @return
//	 */
//	@RequestMapping("/deleteRole")
//	@ResponseBody
//	public int deleteRole(String roleId) {
//		int i = roleService.deleteEntity(Role.class, roleId);
//		return i;
//	}
//
//	/**
//	 * 批量删除用户
//	 * 
//	 * @param user
//	 * @return
//	 */
//	@RequestMapping("/deleteAllRole")
//	@ResponseBody
//	public int deleteAllRole(@RequestParam(value = "roleIds[]") Object[] roleIds, HttpServletRequest request) {
//		int i = roleService.deleteEntityList(request, Role.class, Arrays.asList(roleIds));
//		return i;
//	}
//

	


}
