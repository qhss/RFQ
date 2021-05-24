package com.hotel.controller.sys;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.controller.BasicController;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.Organization;
import com.hotel.entity.sys.Result;
import com.hotel.service.sys.OrganizationService;

@RequestMapping("/depart")
@Controller
public class OrganizationController  extends BasicController{

	@Autowired
	OrganizationService organizationService;

	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

	/**
	 * 组织机构管理
	 * @return
	 */
	@RequestMapping("orgManage")
	public String orgManage(){
		return "sys/organization";
	}
	
	/**
	 * 查询所有组织部门
	 * @return
	 */
	@RequestMapping("/all")
	@ResponseBody
	public Result query() {
		List<Organization> list =organizationService.findAll();
		Result result=new Result();
		result.setRightResult(list);
		return result;
	}

	/**
	 * 根据父节点获取其子节点
	 * @param id
	 * @return
	 */
	@RequestMapping("/getNodes")
	@ResponseBody
	public Result getNodes(String id){
		Result result = new Result();
		ServiceResult<List<Map<String,Object>>> data = organizationService.getNodesByPid(id);
		if(data.isSuccess()){
			result.setRightResult(data.getData());
		}else{
			result.setErrorResult("",data.getErrorMessage());
		}
		return result;
	}
	/**
	 * 查询所有仓库
	 * @return
	 */
	@RequestMapping("/allWarehouse")
	@ResponseBody
	public Result allWarehouse(String id) {
		Result result=new Result();
		try {
			ServiceResult<List<Organization>> list =organizationService.allWarehouse();
			if(list.isSuccess()){
				result.setRightResult(list.getData());
			}else{
				result.setErrorResult("",list.getErrorMessage());
			}
			return result;
		} catch (Exception e) {
			logger.error("获得所有仓库异常",e);
			return result;
		}
	}

	/**
	 * 根据Id查询所有组织部门
	 * @return
	 */
	@RequestMapping("/queryWarehouseById")
	@ResponseBody
	public Result queryWarehouseById(String id) {
		Result result=new Result();
		try {
			ServiceResult<Organization> list =organizationService.queyById(id);
			if(list.isSuccess()){
				result.setRightResult(list.getData());
			}else{
				result.setErrorResult("",list.getErrorMessage());
			}
			return result;
		} catch (Exception e) {
			logger.error("获得所有仓库异常",e);
			return result;
		}
	}

	/**
	 * 保存数据
	 * @param organization
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(Organization organization) {
		Result result=new Result();
		try {
			if(null == organization.getId() && null != organization.getType()){
				if(organization.getType() == 2){
					result.setErrorResult("", "仓库下不能添加了");
					return result;
				}
			}
			ServiceResult<Organization> data=organizationService.save(organization);
			if(data.isSuccess()){
				result.setRightResult(data.getData());
			}else{
				result.setErrorResult("", data.getErrorMessage());
			}
			return result;
		}catch (Exception e) {
			logger.error("",e);
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
	public Result delete(String id) {
		Result result=new Result();
		try {
			ServiceResult<Integer> data =organizationService.delete(id);
			if(data.isSuccess())
				result.setRightResult(data.getData());
			else
				result.setErrorResult("", data.getErrorMessage());
			return result;
		}catch (Exception e) {
			logger.error("controller层删除组织机构出错",e);
			result.setErrorResult("", e.getMessage());
			return result;
		}
	}
	
	/**
	 * 获取根级部门
	 * @return
	 */
	@RequestMapping("/getRootOrg")
	@ResponseBody
	public Result getRootOrg(){
			Result result = new Result();
		try {
			ServiceResult<Map<String,Object>> data = organizationService.getRootOrg();
			if(data.isSuccess()){
				result.setRightResult(data.getData());
			}else{
				result.setErrorResult("",data.getErrorMessage());
			}
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return result;
		}
	}
}
