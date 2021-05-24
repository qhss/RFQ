package com.rfq.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *连接条码系统数据库-sqlServer 
 */
public class ClinitSqlServer {
	
	
	//条码系统测试库
	final static String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	final static String URL = "jdbc:sqlserver://10.3.1.6:1084;DatabaseName=KRT";
	
	//his正式库有测试数据
//	final static String DRIVER = "com.mysql.jdbc.Driver";
//	final static String URL = "jdbc:mysql://119.23.17.199:3316/health_manage_his";
	
	
	protected static PreparedStatement pstm;
	public static Connection conn;
	
	/**
	 * 创建连接对象
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName(DRIVER);
//		conn=DriverManager.getConnection(URL,"sa","k.2012");
		conn=DriverManager.getConnection(URL,"hisuser","saas#Prism");
		return conn;
	}
	
	
	/**
	 * 根据箱码获取条码信息
	 * @param boxCode
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,Object>> getBarInfoByBoxCode(String boxCode) throws Exception{
		
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
	
		String sql ="select  XB001 as kCode,XB004 as masterCode,XB006 as lotNo,XB014 as sCode ,XB201 as productionDate,XB200 as validityPeriod "
				+ "from SFCXB "
				+ "where XB018=?";//箱码
		
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, boxCode);
		ResultSet result = pstm.executeQuery();
		ResultSetMetaData md = result.getMetaData(); //获得结果集结构信息,元数据
		int columnCount = md.getColumnCount();   //获得列数 
		while (result.next()) {
			Map<String,Object> rowData = new HashMap<String,Object>();
			for (int i = 1; i <= columnCount; i++) {
				if(md.getColumnName(i).equals("XB001"))
					rowData.put("kCode", result.getObject(i));
				else if(md.getColumnName(i).equals("XB004"))
					rowData.put("masterCode", result.getObject(i));
				else if(md.getColumnName(i).equals("XB006"))
					rowData.put("lotNo", result.getObject(i));
				else if(md.getColumnName(i).equals("XB014"))
					rowData.put("sCode", result.getObject(i));
				else if(md.getColumnName(i).equals("XB201"))
					rowData.put("productionDate", result.getObject(i));
				else if(md.getColumnName(i).equals("XB200"))
					rowData.put("validityPeriod", result.getObject(i));
			}
			data.add(rowData);
		}
 
		return data;
		
	}
	
	
	/**
	 * 关闭连接
	 */
	public static void closeAll(){
		try{
			if(pstm!=null)	
				pstm.close();
			if(conn!=null && conn.isClosed()==false)	
				conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
