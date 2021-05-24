package com.rfq.service.sys;

import com.rfq.dao.sys.TeamRepository;
import com.rfq.entity.ServiceResult;
import com.rfq.entity.sys.Team;
import com.rfq.service.BasicServiceImpl;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;


@Service
public class TeamService extends BasicServiceImpl {
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(TeamService.class);
	@Resource
	TeamRepository teamRepository;

	/**
	 * 修改单条数据
	 * @param param
	 * @return
	 */
	public ServiceResult<Team> update(Map<String, Object> param) {
		
		int id=param.get("id")==null?0:Integer.parseInt(param.get("id").toString());
		Team team=new Team();
		Optional<Team> result=teamRepository.findById(id);
		if(result.isPresent()) {
			team=result.get();
		}
		//员工姓名
		team.setTname(MapUtils.getString(param, "tname",""));

		team=teamRepository.save(team);
		
		return new ServiceResult<>(team);
		
	}


}
