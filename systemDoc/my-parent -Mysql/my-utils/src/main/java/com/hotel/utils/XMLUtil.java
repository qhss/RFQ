package com.hotel.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.platform.commons.util.StringUtils;


public class XMLUtil {
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String, String> doXMLParse(String strxml) throws JDOMException, IOException {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map<String, String> m = new HashMap<String, String>();
		
		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List<?> list = root.getChildren();
		Iterator<?> it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List<?> children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = XMLUtil.getChildrenText(children);
			}
			
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List<?> children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator<?> it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List<?> list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(XMLUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
	
	
	
	/**
	 * 采购出库计划xml解析
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 * 
	 * 示例：
	 * <?xml version="1.0" encoding="utf-16"?>
	 * 		<I_ITEM>
	 * 			<ITEM>
	 * 				<EBELN>0000000123</EBELN>
				 * <BSART>456</BSART><EBELP>00010</EBELP>
				 * <BEDAT>2019-05-15</BEDAT>
				 * <MATNR>000000000000123456</MATNR>
				 * <TXZ01>789456</TXZ01>
				 * <MENGE>1.0</MENGE>
				 * <CHARG>10</CHARG>
				 * <GROES>123456</GROES>
				 * <MEINS>KAR</MEINS>
				 * <UMREZ>1</UMREZ>
			 * </ITEM>
	 * 		</I_ITEM>
	 * 备注：当有多条记录时，实际为ITEM中数据重复多次，没有区分循环标签
	 */
	public static List<Map<String,Object>> doXMLParseForPurchaseOut(String strxml) throws JDOMException, IOException {
		
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		//xml解析结果
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		
		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		
		Element root = doc.getRootElement();//根节点
		List<?> list = root.getChild("ITEM").getChildren();//所有采购数据
		
		Iterator<?> it = list.iterator();
		Map<String,Object> map = new HashMap<String,Object>();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = e.getTextNormalize();
			map.put(k, v);
			if(k.equals("UMREZ")) {
				result.add(map);
				map = new HashMap<String,Object>();
			}
		}
		
		//关闭流
		in.close();
		
		return result;
	}
	
	
	/**
	 * 销售公司wms-xml解析
	 * <?xml version=\"1.0\" encoding=\"GB2312\"?>
	 * 	<ROOT>
	 * 		<DETAIL>
	 * 			<DH>单号</DH>
	 * 		</DETAIL>
	 * 	</ROOT>
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static String doXMLParseForWms(String strxml) throws JDOMException, IOException {
		
		String result = "";
		
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		//xml解析结果
		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		
		Element root = doc.getRootElement();//根节点
		Element detail = root.getChild("DETAIL").getChild("DH");
		if(!StringUtils.isBlank(detail.getTextNormalize()));
			result = detail.getTextNormalize();
		
		//关闭流
		in.close();
		
		return result;
	}
	
	
	/**
	 * 变更装运类型xml解析
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String,Object> doXmlOfChangeShip(String strxml) throws JDOMException, IOException{
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		//xml解析结果
		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		
		Element root = doc.getRootElement();//根节点
		Element billCode = root.getChild("I_ITEM").getChild("ITEM").getChild("EBELN");//销售单号
		result.put("EBELN", billCode.getTextNormalize());
		Element type = root.getChild("I_ITEM").getChild("ITEM").getChild("VSART");//装运类型
		result.put("VSART", type.getTextNormalize());
		
		return result;
		
	}
	
}
