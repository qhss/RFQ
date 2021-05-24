package com.rfq.service.customer;

import com.rfq.common.impl.BaseServiceImpl;

import com.rfq.dao.customer.CustomerRepository;
import com.rfq.entity.ServiceResult;
import com.rfq.entity.customer.Customer;
import com.rfq.util.sapConnection.SAPConnUtils;
import com.rfq.util.sapConnection.SapConn;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService extends BaseServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SapConn sapConn;

    @Resource
    SAPConnUtils sapConnUtils;


    /**
     * 报价时查询客户是否存在
     * @param param
     * @return
     */
    public ServiceResult<Customer> checkCustomer(Map<String, Object> param) throws Exception {
        String cardCode = MapUtils.getString(param, "cardCode","");
        String dataId = MapUtils.getString(param, "dataId","");
        if (cardCode==null||cardCode==""){
            return new ServiceResult<>(true,"客户编码不能为空");
        }
        if (dataId==null||dataId==""){
            return new ServiceResult<>(true,"未获取到公司ID,请重新点击公司进行报价");
        }
       //优先查询数据库
        Customer customer = new Customer();
        Optional<Customer> item = customerRepository.findByCardCodeAndDataId(cardCode, dataId);
        if (item.isPresent()){
            customer = item.get();
            return new ServiceResult<>(customer);
        }
        //数据库查询不到再从sap系统里拿数据
        else {
            //传入参数查询客户信息,转成sap中对应的数据格式
            while (cardCode.length()<10){
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(cardCode);// 左补0
                cardCode = sb.toString();
            }

            String sapDataId="";
            if (dataId.equals("2")){
                sapDataId="1001";//HS
            }else if (dataId.equals("3")){
                sapDataId="2101";//SPC
            }else if (dataId.equals("4")){
                sapDataId="2222";//MSHK
            }else if (dataId.equals("5")){
                sapDataId="2001";//MSSZ
            }else if (dataId.equals("6")){
                sapDataId="1101";//MSME
            }else
                sapDataId="";
            //进行sap交互
            JCoDestination destination = sapConnUtils.connect(sapConn);
            JCoFunction function = null;
            try {
                function = destination.getRepository().getFunction("Z_PAIRINGTORFQ ");
                function.getImportParameterList().setValue("PARTNER", cardCode);
                function.getImportParameterList().setValue("IMT_VKORG", sapDataId);
                function.execute(destination);

                //获取到sap系统中查询到的表信息
                JCoTable outTable = function.getTableParameterList().getTable("ET_CUSTOMER");
                if (outTable==null || outTable.getNumRows()==0){
                    return new ServiceResult<>(true,"未查询到该客户信息");
                }
                String code = outTable.getString("PARTNER");
                String name = outTable.getString("NAME_ORG1");
                String submmitClass = outTable.getString("KVGR2");
                String currency = outTable.getString("WAERS");
                String oldCard = outTable.getString("BU_SORT2");

                //向客户表中中加入记录
                customer.setCardCode(code);
                customer.setCusClass(submmitClass);
                customer.setCardName(name);
                customer.setCurrency(currency);
                customer.setOldCard(oldCard);
                customer.setDataId(dataId);
                customerRepository.save(customer);

                return new ServiceResult<>(customer);

            } catch (JCoException e) {
                e.printStackTrace();
            }
            return new ServiceResult<>(true,"未查询到该客户信息");
        }
    }
}
