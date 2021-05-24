package com.rfq.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rfq.config.Config;
import com.rfq.utils.UploadImages;


@Controller
@RequestMapping("upload")
public class UploadController extends BasicController {

	
	private static Logger logger = LogManager.getLogger(UploadController.class);
	
	@Resource
	Config config;
	
	/**
	 * 上传主图
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping("/mainpic")
	@ResponseBody
	public List<Map<String, Object>> uploadHotelMainImage(HttpServletRequest request,
			@RequestParam(value = "multipartFile") MultipartFile[] file) {
		
		return uploadImages(request,file,"mainpic");
		
	}
	/**
	 * 上传酒店房间主图
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping("/roompic")
	@ResponseBody
	public List<Map<String, Object>> uploadHotelRoomMainImage(HttpServletRequest request,
			@RequestParam(value = "multipartFile") MultipartFile[] file) {
		
		return uploadImages(request,file,"roompic");
		
	}
	/**
	 * 上传酒店图册
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping("/hotelpics")
	@ResponseBody
	public List<Map<String, Object>> uploadHotelImages(HttpServletRequest request,
			@RequestParam(value = "multipartFile") MultipartFile[] file) {
		
		return uploadImages(request,file,"hotelpics");
		
	}
	
	/**
	 * 上传酒店图册
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping("/roompics")
	@ResponseBody
	public List<Map<String, Object>> uploadHotelRoomImages(HttpServletRequest request,
			@RequestParam(value = "multipartFile") MultipartFile[] file) {
		
		return uploadImages(request,file,"roompics");
		
	}
	
	
	/**
	 * 	横幅
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping("/banner")
	@ResponseBody
	public List<Map<String, Object>> uploadBannerImages(HttpServletRequest request,
			@RequestParam(value = "multipartFile") MultipartFile[] file) {
		
		return uploadImages(request,file,"banner");
		
	}
	
	/**
	 * Simditor富文本编辑器文章里的图片
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping("/simditor")
	@ResponseBody
	public Map<String, Object> uploadImageForSimditor(HttpServletRequest request,
			@RequestParam(value = "file") MultipartFile[] file) {
		
		Map<String, Object> returnResult=new HashMap<String, Object>();
		
		
		List<Map<String, Object>> result=uploadImages(request,file,"simditor");
		if(result==null || result.size()==0) {
			returnResult.put("success", false);
			returnResult.put("file_path", "");
			returnResult.put("msg", "上传失败");
		}else {
			Map<String, Object> upedfile=result.get(0);
			String url=MapUtils.getString(upedfile, "fwUrl","");
			returnResult.put("success", true);
			returnResult.put("file_path", url);
			returnResult.put("msg", "");
		}
		
		return returnResult;
		
	}
	
	
	/**
	 * 不生成缩略图
	 * @param request
	 * @param multipartFile
	 * @param dirName
	 * @return
	 */
	public List<Map<String, Object>> uploadImages(HttpServletRequest request,
			@RequestParam(value = "multipartFile") MultipartFile[] multipartFile, 
			String dirName) {
		
		return _uploadImages(request,multipartFile,dirName,false,0,0);
	}
	/**
	 *  上传
	 * @param request
	 * @param multipartFile
	 * @param dirName
	 * @param thumbnailWidth
	 * @param thumbnailHeight
	 * @return
	 */
	public List<Map<String, Object>> uploadImages(HttpServletRequest request,
			@RequestParam(value = "multipartFile") MultipartFile[] multipartFile, 
			String dirName,Integer thumbnailWidth,Integer thumbnailHeight) {
		
		return _uploadImages(request,multipartFile,dirName,true,thumbnailWidth,thumbnailHeight);
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @param multipartFile
	 * @param dirName
	 * @return
	 */
	private List<Map<String, Object>> _uploadImages(HttpServletRequest request,
			@RequestParam(value = "multipartFile") MultipartFile[] multipartFile, 
			String dirName,
			Boolean isCreateThumbnail,Integer thumbnailWidth,Integer thumbnailHeight) {
		if (multipartFile.length == 0) {
			return null;
		}
//		//文件服务器网址
//		String uploadServerUrl = config.getFileServerUrl()+
//				"&isCreateThumbnail="+(isCreateThumbnail?"1":"0")+
//				"&thumbnailWidth="+thumbnailWidth+
//				"&thumbnailHeight="+thumbnailHeight;
		
		//如果上传服务器没有配置存本地
		
			String uploadUrl = config.getImgHttpHost();
			String phyPath=config.getImgPhyPath();
			List<Map<String, Object>> urllist = UploadImages.uploadImages(
					request, 
					multipartFile, 
					dirName,
					phyPath,
					uploadUrl);
			return urllist;
		
//		else {
//			//上传到文件服务器
//			
//			List<Map<String, Object>> urlList = new ArrayList<Map<String, Object>>();
//			for (MultipartFile file : multipartFile) {
//				try {
//					Map<String, Object> mapResult = new HashMap<String, Object>();
//					//上传到文件服务器
//					String uploadUrl=uploadServerUrl + "&subDir=" + dirName;
//					String postResultString = HttpClientUtil.doPostFile(uploadUrl, file, "utf8");
//					//将字符串结果转为对象
//					FileServerResponseEntity upResult= new ObjectMapper().readValue(postResultString, FileServerResponseEntity.class);
//					
//					logger.debug("return pathUrl:" + upResult.getPathUrl());
//					logger.debug("return fwUrl:" + upResult.getFwUrl());
//					//组合为map结果
//					mapResult.put("pathUrl", upResult.getPathUrl());
//					mapResult.put("fwUrl", upResult.getFwUrl());
//					urlList.add(mapResult);
//				} catch (Exception e) {
//					logger.debug("uploadImages exception:" + e.getMessage());
//					logger.error(e);
//					return null;
//				}
//			}
//			return urlList;
//		}
	}
	
	
}
