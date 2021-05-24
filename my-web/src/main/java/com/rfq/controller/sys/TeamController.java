package com.rfq.controller.sys;

import com.rfq.controller.BasicController;
import com.rfq.entity.ServiceResult;
import com.rfq.entity.sys.Result;
import com.rfq.entity.sys.Team;
import com.rfq.service.sys.TeamService;
import com.rfq.util.sapConnection.SAPConnUtils;
import com.rfq.util.sapConnection.SapConn;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("team")
@Controller
public class TeamController extends BasicController {
	 private static Logger logger = LogManager.getLogger(TeamController.class);
		
		@Resource
		TeamService teamService;
		@Resource
		SapConn sapConn;

		@Resource
		SAPConnUtils sapConnUtils;

		@RequestMapping("")
		public String Index(HttpServletRequest request) {
			return "peopleInfo/worker";
		}

		
		@RequestMapping("/save")
		@ResponseBody
		public Result update(@RequestParam Map<String, Object> param) {
			Result result=new Result();
			try {
				ServiceResult<Team> data= teamService.update(param);
				if(data.isSuccess()) {
					result.setRightResult(data.getData().getId());
					return result;
				}else
				{
					result.setErrorResult("", data.getErrorMessage());
					return result;
				}
			}catch (Exception e) {
				logger.error(e);
				result.setErrorResult(e);
				return result;
			}
		}

	/**
	 * 测试连接sap系统，自定义配置类是否生效
	 */
	@RequestMapping("/test")
		@ResponseBody
		public String  test() throws Exception {
			JCoDestination destination = sapConnUtils.connect(sapConn);
			//传入参数查询客户信息
			JCoFunction function = null;
			try {
				function = destination.getRepository().getFunction("Z_PAIRINGTORFQ ");
				function.getImportParameterList().setValue("PARTNER", "0005000736");
				function.getImportParameterList().setValue("IMT_VKORG", "2001");
				function.execute(destination);

				//获取表类型的字符串
				JCoTable outTable = function.getTableParameterList().getTable("ET_CUSTOMER");
				if (outTable==null || outTable.getNumRows()==0){
					return "查询信息为空";
				}
				String code = outTable.getString("PARTNER");
				String name =outTable.getString("NAME_ORG1");
	            String type = outTable.getString("KVGR2");
				String currency = outTable.getString("WAERS");
				System.out.println("客户编码为：   "+code);
				System.out.println("客户名称为：   "+name);
	            System.out.println("企业生产类型为：   "+type);
				System.out.println("报价币别为：   "+currency);
				System.out.println("--------sap系统展示数据---------");
			} catch (JCoException e) {
				e.printStackTrace();
			}
			return "Hello 测试向SAP系统发送和请求数据";


	}


}
