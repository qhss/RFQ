package com.rfq.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExprotExcel {

	public final static String exportExcel(HttpServletResponse response,
			String fileName, String[] Title, List<Object> listContent) {
		String result = "系统提示：Excel文件导出成功！";
		// 以下开始输出到EXCEL
		try {
			// 定义输出流，以便打开保存对话框______________________begin
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			// 定义输出流，以便打开保存对话框_______________________end

			/** **********创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);

			/** **********创建工作表************ */

			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行

			/** ***************以下是EXCEL开头大标题，暂时省略********************* */
			// sheet.mergeCells(0, 0, colWidth, 0);
			// sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
			/** ***************以下是EXCEL第一行列标题********************* */
			for (int i = 0; i < Title.length; i++) {
				sheet.addCell(new Label(i, 0, Title[i], wcf_center));
			}
			/** ***************以下是EXCEL正文数据********************* */
			Field[] fields = null;
			int i = 1;
			for (Object obj : listContent) {
				fields = obj.getClass().getDeclaredFields();
				int j = 0;
				for (Field v : fields) {
					v.setAccessible(true);
					Object va = v.get(obj);
					if (va == null) {
						va = "";
					}
					sheet.addCell(new Label(j, i, va.toString(), wcf_left));
					j++;
				}
				i++;
			}
			/** **********将以上缓存中的内容写到EXCEL文件中******** */
			workbook.write();
			/** *********关闭文件************* */
			workbook.close();

		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public final static String exportExcelForMap(HttpServletResponse response,
			String path,String fileName, String[] Title, List<Map<String,Object>> listContent) {
		String result = "系统提示：Excel文件导出成功！";
		OutputStream os = null;
		// 以下开始输出到EXCEL
		try {
			
			// 定义输出流，以便打开保存对话框______________________begin
			os=new FileOutputStream(new File(path+File.separator+fileName));
			/*response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
*/			WritableWorkbook workbook =null;
			WritableSheet sheet = null;
			String filePath=path+File.separator+fileName;
			if(!new File(filePath).exists()){
				workbook= Workbook.createWorkbook(new File(filePath));
				sheet = workbook.createSheet("Sheet1", 0);
			}else{
				workbook= Workbook.createWorkbook(os);
			}
			if(sheet==null){
				sheet = workbook.createSheet("Sheet1", 0);
			}
			
			
			// 定义输出流，以便打开保存对话框_______________________end

			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行

			/** ***************以下是EXCEL开头大标题，暂时省略********************* */
			// sheet.mergeCells(0, 0, colWidth, 0);
			// sheet.addCell(new Label(0, 0, "XX报表", wcf_center));
			/** ***************以下是EXCEL第一行列标题********************* */
			for (int i = 0; i < Title.length; i++) {
				sheet.addCell(new Label(i, 0, Title[i], wcf_center));
				sheet.setColumnView(i, 30);
			}
			/** ***************以下是EXCEL正文数据********************* */
			//数据分析统计表的标题另行处理，需要合并单元格
			int q ;
			if(fileName.contains("数据分析统计表")){
				q = 2;
				for(int i = 0;i<Title.length ;i++){
					if(i<10){
						sheet.mergeCells(i, 0,i,1);
						sheet.addCell(new Label(i,0,Title[i],wcf_center));
					}else if(i == 10){
						sheet.mergeCells(i, 0, i+2,0);
						sheet.addCell(new Label(i,0,Title[i],wcf_center));
					}else if(i > 10){
						sheet.mergeCells(i-1, 0,i-1,1);
						sheet.addCell(new Label(i-1,0,Title[i],wcf_center));
					}else if(i == 14){
						sheet.mergeCells(i-1, 0, i+1,0);
						sheet.addCell(new Label(i-1,0,Title[i],wcf_center));
					}else if(i > 14){
						sheet.mergeCells(i-2, 0,i-2,1);
						sheet.addCell(new Label(i-2,0,Title[i],wcf_center));
					}else if(i == 18){
						sheet.mergeCells(i-2, 0, i,0);
						sheet.addCell(new Label(i-2,0,Title[i],wcf_center));
					}else if(i > 18){
						sheet.mergeCells(i-3, 0,i-3,1);
						sheet.addCell(new Label(i-3,0,Title[i],wcf_center));
					}else if(i == 22){
						sheet.mergeCells(i-3, 0, i-2,0);
						sheet.addCell(new Label(i-3,0,Title[i],wcf_center));
					}else if(i > 22){
						sheet.mergeCells(i-4, 0,i,1);
						sheet.addCell(new Label(i-4,0,Title[i],wcf_center));
					}else if(i == 25){
						sheet.mergeCells(i-4, 0, i-1,0);
						sheet.addCell(new Label(i-4,0,Title[i],wcf_center));
					}else if(i > 25){
						sheet.mergeCells(i-5, 0,i-5,1);
						sheet.addCell(new Label(i-5,0,Title[i],wcf_center));
					}else if(i == 32){
						sheet.mergeCells(i-5, 0, i-2,0);
						sheet.addCell(new Label(i-5,0,Title[i],wcf_center));
					}else{
						sheet.mergeCells(i-6, 0,i-6,1);
						sheet.addCell(new Label(i-6,0,Title[i],wcf_center));
					}
					
				}
			}else{
				q = 1;
				for (int i = 0; i < Title.length; i++) {
					sheet.addCell(new Label(i, 0, Title[i], wcf_center));
				}
			}
			
			/** ***************以下是EXCEL正文数据********************* */
			for(Map<String,Object> map :listContent){
				int j=0;
				Iterator iter = map.entrySet().iterator();
				while(iter.hasNext()){
					Entry entry = (Entry)iter.next();
					sheet.addCell(new Label(j, q, (entry.getValue()==null?"无":entry.getValue().toString()), wcf_left));
					j++;
				}
				q++;
			}

			/** **********将以上缓存中的内容写到EXCEL文件中******** */
			workbook.write();
			/** *********关闭文件************* */
			workbook.close();

		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			System.out.println(result);
			e.printStackTrace();
		}finally{
			try {
				if(os!=null){
					os.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}
