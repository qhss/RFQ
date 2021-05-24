package com.hotel.config.sapConnection;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
@Component
public class SAPConnUtils {

    private static final String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";


    /**
     * 创建SAP接口属性文件。
     * @param name  ABAP管道名称
     * @param suffix    属性文件后缀
     * @param properties    属性文件内容
     */
    private static void createDataFile(String name, String suffix, Properties properties){
        File cfg = new File(name+"."+suffix);
        if(cfg.exists()){
            cfg.deleteOnExit();
        }
        try{
            FileOutputStream fos = new FileOutputStream(cfg, false);
            properties.store(fos, "for tests only !");
            fos.close();
        }catch (Exception e){
            System.out.println("Create Data file fault, error msg: " + e.toString());
            throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
        }
    }

    /**
     * 初始化SAP连接
     */
    private static void initProperties(SapConn sapConn) {
        Properties connectProperties = new Properties();
        // SAP服务器
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, sapConn.getJCO_ASHOST());
        // SAP系统编号
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  sapConn.getJCO_SYSNR());
        // SAP集团
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, sapConn.getJCO_CLIENT());
        // SAP用户名
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,   sapConn.getJCO_USER());
        // SAP密码
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, sapConn.getJCO_PASSWD());
        // SAP登录语言
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   sapConn.getJCO_LANG());
        // 最大连接数
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, sapConn.getJCO_POOL_CAPACITY());
        // 最大连接线程
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, sapConn.getJCO_PEAK_LIMIT());
        // SAP ROUTER
        connectProperties.setProperty(DestinationDataProvider.JCO_SAPROUTER, sapConn.getJCO_SAPROUTER());

        createDataFile(ABAP_AS_POOLED, "jcoDestination", connectProperties);
    }

    /**
     * 获取SAP连接
     * @return  SAP连接对象
     */
    public static JCoDestination connect(SapConn sapConn,String type){
        System.out.println("正在连接至SAP...");
        JCoDestination destination = null;
        initProperties(sapConn);
        try {
            destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
            destination.ping();
            System.out.println("已成功建立sap的连接");
            System.out.println("--------向sap系统拿数据---------");

            if (type=="customer"){

                //传入参数查询客户信息
                JCoFunction function = null;
                function = destination.getRepository().getFunction("Z_PAIRINGTORFQ ");
                function.getImportParameterList().setValue("PARTNER", "0005000736");
                function.getImportParameterList().setValue("IMT_VKORG", "2001");
                function.execute(destination);

                //获取字符串类型
                String bu_sort2 = function.getExportParameterList().getString("BU_SORT2");
                //获取表类型的字符串
                String code = function.getTableParameterList().getTable("ET_CUSTOMER").getString("PARTNER");
                String name = function.getTableParameterList().getTable("ET_CUSTOMER").getString("NAME_ORG1");
//            String type = function.getTableParameterList().getTable("ET_CUSTOMER").getString("KVG");
                String currency = function.getTableParameterList().getTable("ET_CUSTOMER").getString("WAERS");
                System.out.println(bu_sort2);
                System.out.println("客户编码为：   "+code);
                System.out.println("客户名称为：   "+name);
//            System.out.println("企业生产类型为：   "+type);
                System.out.println("报价币别为：   "+currency);
                System.out.println("--------sap系统展示数据---------");
            }else if (type=="material"){
                System.out.println("--------暂未开发---------");
            }
            else {
                System.out.println("--------暂未开发---------");
            }


        } catch (JCoException e) {
            System.out.println("Connect SAP fault, error msg: " + e.toString());
        }
        return destination;
    }
}
