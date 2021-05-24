package com.rfq.config.sapConnection;

import com.rfq.util.sapConnection.SAPConnUtils;
import com.rfq.util.sapConnection.SapConn;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;

class SAPConnUtilsTest {
    public static void main(String[] args) throws Exception {

        SapConn con = new SapConn(
                "192.168.16.238",
                "00",
                "800",
                "HSIT-lucky",
                "lucky987",
                "zh",
                "200",
                "200",
                ""
        );

        // 测试连接
        JCoDestination destination = SAPConnUtils.connect(con);
        JCoFunction function = null;
        try {
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
        } catch (JCoException e) {
            e.printStackTrace();
        }
    }
}