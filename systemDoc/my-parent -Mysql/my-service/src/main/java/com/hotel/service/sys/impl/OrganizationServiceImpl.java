package com.hotel.service.sys.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.sys.OrganizationRepository;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.sys.Organization;
import com.hotel.entity.sys.User;
import com.hotel.service.BasicServiceImpl;
import com.hotel.service.sys.OrganizationService;
import com.hotel.service.where.ExcludeDeleted;


@Service
public class OrganizationServiceImpl extends BasicServiceImpl  implements OrganizationService {

	@Autowired
	OrganizationRepository organizationRepository;

	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	
	
	@Override
	public List<Organization> findAll() {
		//排除删除的数据
		List<Organization> list=organizationRepository.findAll(new ExcludeDeleted<Organization>());
		return list;
	}


	@Override
	public ServiceResult<Organization> queyById(String departId) {
		Optional<Organization> orgOptional=organizationRepository.findById(departId);
		if(orgOptional.isPresent()) {
			if(orgOptional.get().getDeleted()==Organization.DELETE_STATE_UNDELETE)
				return new ServiceResult<Organization>(orgOptional.get());
		}
		return new ServiceResult<Organization>(true,"获取部门错误");
	}

	@Override
	public ServiceResult<Map<String,Object>> getRootOrg() {
		Optional<Organization> organization = organizationRepository.findByCompanyIdAndDeletedAndPid(getLoginUser().getData().getCompanyId(),0,"0");
		if(organization.isPresent()) {
			Organization o = organization.get();
			Map<String,Object> map = new HashMap<>();
			map.put("id",o.getId());
			map.put("type",o.getType());
			map.put("name",o.getName());
			map.put("pid",o.getPid());
			map.put("sort",o.getSort());
				return new ServiceResult<Map<String,Object>>(map);
		}
		return new ServiceResult<Map<String,Object>>(true,"获取根节点错误");
	}

	@Override
	public ServiceResult<List<Map<String,Object>>> getNodes(String pId) {
		List<Map<String,Object>> data = null;
		List<Map<String,Object>> list = new ArrayList<>();
		try {
			User user = getLoginUser().getData();
			data = organizationRepository.queryChildeNode(user.getCompanyId(),pId);
			if(CollectionUtils.isNotEmpty(data)){
				for(Map<String,Object> map : data){
					Map<String,Object> map2 = new HashMap<>();
					for(Map.Entry<String,Object> entry : map.entrySet()){
						map2.put(entry.getKey(),entry.getValue());
						BigInteger childCount = (BigInteger) map.get("childCount");
						int a =  childCount.intValue();
						map2.put("isParent",a>0);
						//BigInteger bIconMark = (BigInteger)(map.get("iconMark"));
						//int iconMark = bIconMark.intValue();
//						if(iconMark == 1){
//							//设置仓库的图标地址
//							map2.put("icon", VariableConfig.ICON_PATH_FOR_WAREHOUSE);
//						}
//						if(iconMark == 2){
//							//设置区域的图标地址
//							map2.put("icon",VariableConfig.ICON_PATH_FOR_AREA);
//						}
					}
					list.add(map2);
				}
			}else{
				return new ServiceResult<List<Map<String,Object>>>(true,"区域为空");
			}
			return new ServiceResult<List<Map<String,Object>>>(list);
		} catch (Exception e) {
			logger.error("查询仓库所有的区域出错",e);
			return new ServiceResult<List<Map<String,Object>>>(true,e.getMessage());
		}

	}

	@Override
	public ServiceResult<List<Map<String,Object>>> getNodesByPid(String pId) {
		try {
			User user = getLoginUser().getData();
			List<Map<String,Object>> organizations = organizationRepository.getNodesByPid(pId,user.getCompanyId());
			List<Map<String,Object>> list = new ArrayList<>();
			if(CollectionUtils.isNotEmpty(organizations)){
				for(Map<String,Object> map : organizations){
					Map<String,Object> map2 = new HashMap<>();
					for(Map.Entry<String,Object> entry : map.entrySet()){
						map2.put(entry.getKey(),entry.getValue());
						Long childCount = (Long) map.get("childCount");
						map2.put("isParent",childCount > 0);
						//int bType = (Integer)map.get("type");
//						if(bType == 1){
//							//设置部门的图标地址
//							map2.put("icon",VariableConfig.ICON_PATH_FOR_DEPARTMENT);
//						}
//						if(bType == 2){
//							//设置仓库的图标地址
//							map2.put("icon", VariableConfig.ICON_PATH_FOR_WAREHOUSE);
//						}
					}
					list.add(map2);
				}
				return new ServiceResult<List<Map<String,Object>>>(list);
			}else {
				return new ServiceResult<List<Map<String,Object>>>(true,"根据父节点获得子节点失败");
			}
		} catch (Exception e) {
			logger.error("根据父节点获得子节点出错",e);
			return new ServiceResult<List<Map<String,Object>>>(true,e.getMessage());
		}
	}

	@Override
	public ServiceResult<List<Organization>> allWarehouse() {
		User user = getLoginUser().getData();
		Optional<List<Organization>> organization = organizationRepository.findByDeletedAndTypeAndCompanyId(0,2,user.getCompanyId());
		if(organization.isPresent()){
			return new ServiceResult<List<Organization>>(organization.get());
		}else {
			return  new ServiceResult<List<Organization>>(true,"未获得数据");
		}
	}

	/***
	 * 保存
	 * @param id
	 * @param code
	 * @param name
	 * @param memo
	 * @param areaTypeId
	 * @return
	 */
	@Override
	public ServiceResult<Organization> save(Organization  organization) {
		Organization result=new Organization();
		try {
			if(StringUtils.isNotBlank(organization.getId())){
				result=organizationRepository.findById(organization.getId()).get();

			}else {
				//新增初始值
				result.setDeleted(Organization.DELETE_STATE_UNDELETE);
				result.setPid(organization.getPid());
			}
			result.setCode(organization.getCode());
			result.setName(organization.getName());
			result.setMemo(organization.getMemo());
			result.setCompanyId(getLoginUser().getData().getCompanyId());
			result.setSort(organization.getSort());
			result.setCanceled(organization.getCanceled());
			result.setType(organization.getType());
			result=organizationRepository.save(result);
			return new ServiceResult<Organization>(result);
		} catch (Exception e) {
			logger.error("更新组织机构出错",e);
			return new ServiceResult<Organization>(true,e.getMessage());
		}
	}

	@Override
	public ServiceResult<Integer> delete(String id) {
		try {
			if(StringUtils.isNotBlank(id)) {
				Organization organization=organizationRepository.findById(id).get();

				//TODO 增加删除校验
				organization.setDeleted(Organization.DELETE_STATE_DELETED);
				organizationRepository.save(organization);
				return new ServiceResult<Integer>(1);
			}
			return new ServiceResult<Integer>(true,"删除失败");

		} catch (Exception e) {
			logger.error("删除组织机构出错",e);
			return new ServiceResult<Integer>(true,e.getMessage());
		}
	}

	@Override
	public ServiceResult<List<Map<String, Object>>> queryByPidAndDeleted(String companyId, String pId) {
		return null;
	}
}
