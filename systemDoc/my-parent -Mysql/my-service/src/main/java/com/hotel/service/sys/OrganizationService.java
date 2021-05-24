package com.hotel.service.sys;

import java.util.List;
import java.util.Map;

import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.Organization;
import com.hotel.service.BasicService;

public interface OrganizationService extends BasicService{

	List<Organization> findAll();

	ServiceResult<Organization> queyById(String departId);

	ServiceResult<Map<String,Object>> getRootOrg();

	ServiceResult<List<Map<String,Object>>> getNodes(String pId);

	ServiceResult<List<Organization>> allWarehouse();

	ServiceResult<Organization> save(Organization  organization);

	ServiceResult<Integer> delete(String id);

	/**
	 * 根据父节点获得库存区域
	 * @param pId
	 * @param companyId
	 * @return
	 */
	ServiceResult<List<Map<String,Object>>> queryByPidAndDeleted(String companyId,String pId);

	/**
	 * 根据父节点获得其子节点
	 * @param pId
	 * @return
	 */
	ServiceResult<List<Map<String,Object>>> getNodesByPid(String pId);



}
