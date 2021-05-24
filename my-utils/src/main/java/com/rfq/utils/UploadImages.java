package com.rfq.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;


public class UploadImages {
	private static Logger  logger = LogManager.getLogger(UploadImages.class);
	/**
	 * 上传图片
	 * @param multipartFile
	 * @param request
	 * @param subDirName 子目录名称
	 * @param physicalPath 物理路径 例如:c:\\a\
	 * @param serverUrl 需要包含端口 例如:http://www.baidu.cn:8080/
	 * @return
	 */
	public static List<Map<String, Object>> uploadImages(
			HttpServletRequest request,
			MultipartFile[] multipartFile, 
			String subDirName, String physicalPath,String serverUrl) {
		
		List<Map<String, Object>> urlList = new ArrayList<Map<String, Object>>();

		String rootPath = physicalPath + File.separator + "uploadFiles";
		if (multipartFile.length == 0) {
			return null;
		}
		for (MultipartFile file : multipartFile) {
			// 保存
			try {
				String url = serverUrl+ "uploadFiles/" + subDirName;
				Map<String, Object> map = new HashMap<String, Object>();
				String fileName = file.getOriginalFilename();

				String uuid =UUID.randomUUID().toString().replaceAll("-", "");// System.currentTimeMillis();
				String randomName = uuid.toString()
						+ fileName.substring(fileName.lastIndexOf("."));
				String fileDir = rootPath + File.separator + subDirName;
				//BufferedImage bi = ImageIO.read(file.getInputStream());
				//int fileSize = (int) (file.getSize()/1024);  //图片大小  KB
//				if("doctor".equals(subDirName)){
//					/*if(bi.getWidth()<120||bi.getHeight()<120||fileSize>1050){
//						break;
//					}*/
//				}

				map.put("fileName", fileName);
				
				File targetFile = new File(fileDir, randomName);
				File targetDir=new File(fileDir);
				if (!targetDir.exists()) {
					targetDir.mkdirs();
				}

				file.transferTo(targetFile);
				String pathUrl = fileDir + File.separator + randomName;
				String fwUrl = url + "/" + randomName;
				map.put("pathUrl", pathUrl);
				map.put("fwUrl", fwUrl);
				urlList.add(map);
			} catch (Exception e) {
			
				logger.error(e);
				return null;
			}
		}
		return urlList;
	}
	
}
