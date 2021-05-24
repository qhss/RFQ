package com.rfq.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class Util {

	public static int toInt(int[] i) {
		int total = 0;
		for (int count : i) {
			total += count;
		}
		return total;
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp;
	}

	/**
	 * 获得随机的三位字符,用于MD5加密
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		StringBuffer ss = new StringBuffer();
		long result = 0;
		for (int i = 0; i < length; i++) {
			// 产生a-z的ASCII码
			result = Math.round(Math.random() * 25 + 97);
			// 将ASCII码转成字符
			ss.append(String.valueOf((char) result));
		}
		return ss.toString();
	}

	/**
	 * 字符串第一个字母转大写
	 * 
	 * @param str
	 * @return
	 */
	public static String upperStrFirstLetter(String str) {
		String first = str.substring(0, 1).toUpperCase();
		String last = str.substring(1, str.length());
		str = first + last;
		return str;
	}

	// 根据汉字获取拼音首字母
	/*
	 * public static String charToPinyin(String word) { /*** ^[\u2E80-\u9FFF]+$
	 * 匹配所有东亚区的语言 ^[\u4E00-\u9FFF]+$ 匹配简体和繁体 ^[\u4E00-\u9FA5]+$ 匹配简体
	 *
	 * String regExp="^[\u4E00-\u9FFF]+$"; StringBuffer sb=new StringBuffer();
	 * if(word==null||"".equals(word.trim())) { return ""; } String pinyin="";
	 * for(int i=0;i<word.length();i++) { char unit=word.charAt(i);
	 * if(match(String.valueOf(unit),regExp))//是汉字，则转拼音 {
	 * pinyin=convertSingleHanziToPinyin(unit); sb.append(pinyin.charAt(0)); }else {
	 * sb.append(unit); } } return sb.toString(); }
	 */
	/***
	 * 将单个汉字转成拼音
	 * 
	 * @param hanzi
	 * @return
	 * 
	 * 		private static String convertSingleHanziToPinyin(char hanzi) {
	 *         HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
	 *         outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE); String[]
	 *         res; StringBuffer sb=new StringBuffer(); try { res =
	 *         PinyinHelper.toHanyuPinyinStringArray(hanzi,outputFormat);
	 *         sb.append(res[0]);//对于多音字，只用第一个拼音 } catch (Exception e) {
	 *         e.printStackTrace(); return ""; } return sb.toString(); }
	 */

	/***
	 * 汉字匹配
	 * 
	 * @param str   源字符串
	 * @param regex 正则表达式
	 * @return 是否匹配
	 */
	public static boolean match(String str, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}

	public static BigDecimal multiply(String s, String s1) {
		BigDecimal unit = new BigDecimal(s);
		BigDecimal n = new BigDecimal(s1);
		return unit.multiply(n).setScale(2, BigDecimal.ROUND_HALF_DOWN);
	}

	public static String bigAdd(String s, String s1) {
		BigDecimal unit = null;
		BigDecimal n = null;
		if (s == null || "".equals(s.trim()))
			unit = new BigDecimal(0);
		else
			unit = new BigDecimal(s);
		if (s1 == null || "".equals(s1.trim()))
			n = new BigDecimal(0);
		else
			n = new BigDecimal(s1);
		return unit.add(n).toString();
	}

	public static String getRandom() {
		try {
			String str = "";
			for (int i = 0; i < 3; i++) {
				int intValue = (int) (Math.random() * 26 + 97);
				str = str + (char) intValue;
			}
			return str;
		} catch (Exception e) {
			return "aaa";
		}
	}

	/**
	 * Object数组转Integer数组
	 * 
	 * @param obj
	 * @return
	 */
	public static Integer[] retValue(Object[] obj) {
		if (null != obj && 0 != obj.length) {
			Integer[] in = new Integer[obj.length];
			for (int i = 0; i < obj.length; i++) {
				in[i] = Integer.parseInt((String) obj[i]);
			}
			return in;
		}
		return null;
	}

	/**
	 * 上传图片
	 * 
	 * @param multipartFile
	 * @param request
	 * @param fName
	 * @return
	 */
	public static Map<String, Object> uploadImage(MultipartFile multipartFile, HttpServletRequest request, String fName,
			String serverUrl) {
		Map<String, Object> map = new HashMap<String, Object>();
		String nowpath = System.getProperty("user.dir");
		String path = nowpath.replace("bin", "webapps");
		path = path + File.separator + "uploadFiles" + File.separator;
		if (null == multipartFile) {
			map.put("code", "-1");
			return map;
		}
		String url = request.getScheme() + "://" + serverUrl + ":" + request.getServerPort() + File.separator
				+ "uploadFiles" + File.separator + fName + File.separator;
		String fileName = multipartFile.getOriginalFilename();
		Long name = System.currentTimeMillis();
		String addr = name.toString() + fileName.substring(fileName.lastIndexOf("."));
		String riskPath = path + fName + File.separator + addr;
		File targetFile = new File(path + File.separator + fName, addr);
		BufferedImage bi;
		try {
			bi = ImageIO.read(multipartFile.getInputStream());
			int fileSize = (int) (multipartFile.getSize() / 1024); // 图片大小 KB
			if ("body".equals(fName)) {
				if (bi.getWidth() < 60 || bi.getHeight() < 60 || fileSize > 1024) {
					map.put("code", "-2");
					return map;
				}
			} else if (bi.getWidth() < 200 || bi.getHeight() < 200 || fileSize > 5120) {
				map.put("code", "-2");
				return map;
			}
		} catch (Exception e1) {
			map.put("code", "-1");
			return map;
		}
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			multipartFile.transferTo(targetFile);
		} catch (Exception e) {
			map.put("code", "-1");
			return map;
		}
		map.put("fwPath", url + addr);
		map.put("riskPath", riskPath);
		return map;
	}

	/**
	 * 上传图片
	 * 
	 * @param multipartFile
	 * @param request
	 * @param fName
	 * @return
	 */
	public static Map<String, Object> uploadVideo(MultipartFile multipartFile, HttpServletRequest request, String fName,
			String serverUrl) {
		Map<String, Object> map = new HashMap<String, Object>();
		String nowpath = System.getProperty("user.dir");
		String path = nowpath.replace("bin", "webapps");
		path = path + File.separator + "uploadFiles" + File.separator;
		if (null == multipartFile) {
			map.put("code", "-1");
			return map;
		}
		String url = request.getScheme() + "://" + serverUrl + ":" + request.getServerPort() + File.separator
				+ "uploadFiles" + File.separator + fName + File.separator;

		String fileName = multipartFile.getOriginalFilename();
		Long name = System.currentTimeMillis();
		String addr = name.toString() + fileName.substring(fileName.lastIndexOf("."));
		String riskPath = path + fName + File.separator + addr;
		File targetFile = new File(path + File.separator + fName, addr);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			multipartFile.transferTo(targetFile);
		} catch (Exception e) {
			map.put("code", "-1");
			return map;
		}
		map.put("fwPath", url + addr);
		map.put("riskPath", riskPath);
//		map.put("onlyRiskPath",  path + fName);
//		map.put("onlyName", addr);
		return map;
	}

	/**
	 * 上传图片
	 * 
	 * @param multipartFile
	 * @param request
	 * @param fName
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String uploadFoodImage(MultipartFile multipartFile, HttpServletRequest request, String fName,
			String serverUrl) {

		String nowpath = System.getProperty("user.dir");
		String path = nowpath.replace("bin", "webapps");
		path = path + "/uploadFiles";
		if (null == multipartFile) {
			return null;
		}
		String url = request.getScheme() + "://" + serverUrl + ":" + request.getServerPort() + File.separator
				+ "uploadFiles" + File.separator + fName + File.separator;
		String fileName = multipartFile.getOriginalFilename();
		Long name = System.currentTimeMillis();
		String addr = name.toString() + fileName.substring(fileName.lastIndexOf("."));
		File targetFile = new File(path + File.separator + fName, addr);
		BufferedImage bi;
		try {
			bi = ImageIO.read(multipartFile.getInputStream());
			int fileSize = (int) (multipartFile.getSize() / 1024); // 图片大小 KB
			/*
			 * if(bi.getWidth()<200||bi.getHeight()<200||fileSize>5120){ return null; }
			 */
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			multipartFile.transferTo(targetFile);
		} catch (Exception e) {
			return null;
		}
		return url + addr;
	}

	/**
	 * 上传文件
	 * 
	 * @param multipartFile
	 * @param request
	 * @param fName
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String uploadFile(MultipartFile multipartFile, HttpServletRequest request, String name) {

		String nowpath = System.getProperty("user.dir");
		String path = nowpath.replace("bin", "webapps");
		path = path + "/zipTemp";
		if (null == multipartFile) {
			return null;
		}
		String fileName = multipartFile.getOriginalFilename();
		String addr = name.toString() + fileName.substring(fileName.lastIndexOf("."));
		File targetFile = new File(path, addr);
		BufferedImage bi;
		try {
			bi = ImageIO.read(multipartFile.getInputStream());
			int fileSize = (int) (multipartFile.getSize() / 1024); // 图片大小 KB
			/*
			 * if(bi.getWidth()<200||bi.getHeight()<200||fileSize>5120){ return null; }
			 */
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			multipartFile.transferTo(targetFile);
		} catch (Exception e) {
			return null;
		}
		return addr;
	}

	/**
	 * 图片迁移
	 * 
	 * @param request
	 * @param imgPath
	 * @param dirName
	 * @param fileName
	 * @return
	 */
	public static Map<String, String> imageMove(HttpServletRequest request, File file, String dirName,
			String serverUrl) {
		String name = System.currentTimeMillis() + file.getName().substring(file.getName().lastIndexOf("."));
		String nowpath = System.getProperty("user.dir");
		String path = nowpath.replace("bin", "webapps");
		path = path + "/uploadFiles";
		String url = request.getScheme() + "://" + serverUrl + ":" + request.getServerPort() + File.separator
				+ "uploadFiles" + File.separator + dirName + File.separator;
		String riskPath = path + File.separator + dirName + File.separator + name;
		File riskFile = new File(riskPath);
		if (!riskFile.getParentFile().exists()) {
			riskFile.getParentFile().mkdirs();
		}
		InputStream read = null;
		BufferedOutputStream write = null;
		try {
			riskFile.createNewFile();
			read = new FileInputStream(file);
			write = new BufferedOutputStream(new FileOutputStream(riskFile));
			int cha = 0;
			while ((cha = read.read()) != -1) {
				write.write(cha);
			}
			write.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				write.close();
				read.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("fwPath", url + name);
		map.put("riskPath", riskPath);
		return map;
	}

	public static void deleteFile(String sPath) {
		if (!StringUtils.isEmpty(sPath)) {
			File file = new File(sPath);
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		}
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/*
	 * public static String AudioPlayTime(String addr) { int i = 0; String times =
	 * "0:0"; try { File file1 = new File(addr); FileInputStream fis = new
	 * FileInputStream(file1); int b = fis.available(); Bitstream bt = new
	 * Bitstream(fis); Header h = bt.readFrame(); int time = (int) h.total_ms(b); i
	 * = time / 1000; times = i / 60 + ":" + i % 60; } catch (FileNotFoundException
	 * e) { e.printStackTrace(); } catch (BitstreamException e) {
	 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); } return
	 * times; }
	 */
	public static String getIp(HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = null;
			// ipAddress = this.getRequest().getRemoteAddr();
			ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (ipAddress.equals("127.0.0.1") || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress = inet.getHostAddress();
				}
			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
																// = 15
				if (ipAddress.indexOf(",") > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				}
			}
		} catch (Exception e) {
			ipAddress = "Exception";
		}
		return ipAddress;
	}

	/*
	 * public static void main(String[] args) { //1 会员 2 私人医生 3 运维 4-销售代表 JSONObject
	 * obj = JSONObject
	 * .fromString("{\"action_name\": \"QR_LIMIT_SCENE\",\"action_info\": {\"scene\": {\"scene_id\": 4}}}"
	 * ); String s = HttpReuqest .sendPost(
	 * "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=dXv17gBDDhnXoMhZVVoVYq-PwsE3eDgSL_aYWTVvHdiUOmw9MGxK5OrKIw29FZImVUbbfw9FA3x_xC38K2ZL980MslSBs9QVppdK68WCkNsKVHeAEABMY",
	 * obj); System.out.println(s); }
	 */
	/**
	 * 根据内容类型判断文件扩展名
	 *
	 * @param contentType 内容类型
	 * @return
	 */
	public static String getFileexpandedName(String contentType) {
		String fileEndWitsh = "";
		if ("image/jpeg".equals(contentType) || "image/jpg".equals(contentType))
			fileEndWitsh = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileEndWitsh = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileEndWitsh = ".amr";
		else if ("video/mp4".equals(contentType))
			fileEndWitsh = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileEndWitsh = ".mp4";
		return fileEndWitsh;
	}

	@SuppressWarnings("rawtypes")
	public static void mapToXMLTest2(Map map, StringBuffer sb) {
		Set set = map.keySet();
		for (Iterator it = set.iterator(); it.hasNext();) {
			String key = (String) it.next();
			Object value = map.get(key);
			if (null == value)
				value = "";
			if (value.getClass().getName().equals("java.util.ArrayList")) {
				ArrayList list = (ArrayList) map.get(key);
				sb.append("<" + key + ">");
				for (int i = 0; i < list.size(); i++) {
					HashMap hm = (HashMap) list.get(i);
					mapToXMLTest2(hm, sb);
				}
				sb.append("</" + key + ">");

			} else {
				if (value instanceof HashMap) {
					sb.append("<" + key + ">");
					mapToXMLTest2((HashMap) value, sb);
					sb.append("</" + key + ">");
				} else {
					sb.append("<" + key + ">" + value + "</" + key + ">");
				}

			}

		}
	}

	/**
	 * 时间戳转日期
	 * 
	 * @param seconds
	 * @return
	 */
	public static String timeStamp2Date(String seconds) {
		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
			return "";
		}
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds + "000")));
	}

	/**
	 * 解压zip
	 * 
	 * @param request
	 * @param file
	 * @throws IOException
	 */
	public static String CopyOfMyzipDecompressing(HttpServletRequest request, MultipartFile file, String name) {
		String fileName = uploadFile(file, request, name);
		String nowpath = System.getProperty("user.dir");
		String path = nowpath.replace("bin", "webapps");
		path = path + File.separator + "zipTemp" + File.separator + fileName;// 输出目录
		String path1 = nowpath.replace("bin", "webapps") + File.separator + "zipTemp" + File.separator
				+ fileName.substring(0, fileName.lastIndexOf(".")) + File.separator;
		InputStream in = null;
		ZipInputStream zin = null;
		ZipFile zf = null;
		try {
			zf = new ZipFile(path);
			in = new BufferedInputStream(new FileInputStream(path));
			zin = new ZipInputStream(in);
			ZipEntry entry = null;
			while ((entry = zin.getNextEntry()) != null) {
				if (entry.isDirectory()) {
					continue;
				}
				File tmp = new File(path1 + entry.getName());
				if (!tmp.exists()) {
					File rootDirectoryFile = new File(tmp.getParent());
					if (!rootDirectoryFile.exists()) {
						boolean ifSuccess = rootDirectoryFile.mkdirs();
						if (!ifSuccess) {
							return null;
						}
					}
				}
				tmp.createNewFile();
				InputStream read = zf.getInputStream(entry);
				BufferedOutputStream write = new BufferedOutputStream(new FileOutputStream(tmp));
				int cha = 0;
				while ((cha = read.read()) != -1) {
					write.write(cha);
				}
				write.flush();
				write.close();
				read.close();
			}
		} catch (Exception e) {
			return null;
		} finally {
			try {
				in.close();
				zin.close();
				zf.close();
			} catch (IOException e1) {
				return null;
			}
		}
		return path1;
	}

	public static boolean isEquals(Object o1, Object o2) {
		BigDecimal a = new BigDecimal(o1.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal b = new BigDecimal(o2.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
		return a.equals(b);
	}

	public static List<Map<String, Object>> buildTree(List<Map<String, Object>> list) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> m : list) {
			if (null == m.get("pid") || m.get("pid").toString().equals("0")) {
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Object> attrId = new HashMap<String, Object>();
				map.put("title", m.get("name").toString());
				map.put("type", "folder");
				attrId.put("id", "permissios_" + m.get("id").toString());
				attrId.put("permissionsId", m.get("id").toString());
				attrId.put("pid", null == m.get("pid") ? "0" : m.get("pid").toString());
				attrId.put("state", m.get("state").toString());
				attrId.put("_type", m.get("type").toString());
				map.put("attr", attrId);
				build(m, m.get("id").toString(), map, list);
				l.add(map);
			}
		}
		return l;
	}

	private static void build(Map<String, Object> m, String pid, Map<String, Object> map,
			List<Map<String, Object>> list) {
		List<Map<String, Object>> children = getChildren(m, list);
		List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
		if (!children.isEmpty() && children.size() > 0) {
			for (Map<String, Object> m1 : children) {
				Map<String, Object> param = new HashMap<String, Object>();
				Map<String, Object> attrId = new HashMap<String, Object>();
				param.put("title", m1.get("name").toString());
				if (0 == getChildren(m1, list).size()) {
					param.put("type", "item");
				} else {
					param.put("type", "folder");
				}
				attrId.put("id", "permissios_" + m1.get("id").toString());
				attrId.put("permissionsId", m1.get("id").toString());
				attrId.put("pid", m1.get("pid").toString());
				attrId.put("state", m1.get("state").toString());
				attrId.put("_type", m1.get("type").toString());
				param.put("attr", attrId);
				String id = m1.get("id").toString();
				build(m1, id, param, list);
				childList.add(param);
			}
			map.put("products", childList);
		} else {
			map.put("products", childList);
		}
	}

	private static List<Map<String, Object>> getChildren(Map<String, Object> m, List<Map<String, Object>> list) {
		List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
		String id = m.get("id").toString();
		for (Map<String, Object> map : list) {
			if (id.equals(null == map.get("pid") ? "0" : map.get("pid").toString())) {
				children.add(map);
			}
		}
		return children;
	}

	public static List<Map<String, Object>> buildTagTree(List<Map<String, Object>> list) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> m : list) {
			if (null == m.get("pid") || m.get("pid").toString().equals("0")) {
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Object> attrId = new HashMap<String, Object>();
				map.put("title", m.get("name").toString());
				map.put("type", "folder");
				attrId.put("id", "permissios_" + m.get("id").toString());
				attrId.put("permissionsId", m.get("id").toString());
				attrId.put("pid", null == m.get("pid") ? "0" : m.get("pid").toString());
				attrId.put("state", m.get("state").toString());
				attrId.put("_type", m.get("type").toString());
				map.put("attr", attrId);
				buildTags(m, m.get("id").toString(), map, list);
				l.add(map);
			}
		}
		return l;
	}

	private static void buildTags(Map<String, Object> m, String pid, Map<String, Object> map,
			List<Map<String, Object>> list) {
		List<Map<String, Object>> children = getChildren(m, list);
		List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
		if (!children.isEmpty() && children.size() > 0) {
			for (Map<String, Object> m1 : children) {
				Map<String, Object> param = new HashMap<String, Object>();
				Map<String, Object> attrId = new HashMap<String, Object>();
				param.put("title", m1.get("name").toString());
				if (0 == getTagChildren(m1, list).size()) {
					param.put("type", "item");
				} else {
					param.put("type", "folder");
				}
				attrId.put("id", "permissios_" + m1.get("id").toString());
				attrId.put("permissionsId", m1.get("id").toString());
				attrId.put("pid", m1.get("pid").toString());
				attrId.put("state", m.get("state").toString());
				attrId.put("_type", m1.get("type").toString());
				param.put("attr", attrId);
				String id = m1.get("id").toString();
				buildTags(m1, id, param, list);
				childList.add(param);
			}
			map.put("products", childList);
		} else {
			map.put("products", childList);
		}
	}

	private static List<Map<String, Object>> getTagChildren(Map<String, Object> m, List<Map<String, Object>> list) {
		List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
		String id = m.get("id").toString();
		for (Map<String, Object> map : list) {
			if (id.equals(null == map.get("pid") ? "0" : map.get("pid").toString())) {
				children.add(map);
			}
		}
		return children;
	}

	public static Map<String, Object> doExcelByZip(HttpServletRequest request, HttpServletResponse response,
			String zipDirName, String dirName, String[] title, List<Map<String, Object>> data, String name,
			String serverHost, String pyhPath) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		final int count = 10000;
		// 服务器访问路径
		String fwUrl = serverHost + "uploadFiles" + "/" + zipDirName + "/";
		// 服务器上传物理路径
		String upPath = pyhPath + File.separator + "uploadFiles" + File.separator + dirName + File.separator;
		if (!new File(upPath).exists()) {
			new File(upPath).mkdirs();
		}

		String zipName = name.replace("xls", "zip");

		if (data != null && data.size() > 0) {
			int len = data.size();
			int remainder = len % count;// 余数
			int merchant = len / count;// 取整
			if (merchant > 0) {
				for (int i = 0; i < merchant; i++) {
					List<Map<String, Object>> oneList = data.subList(count * i, count * (i + 1));
					writeExcel(oneList, response, upPath, name.replace(".", (i + 1) + "."), title);
				}
			}
			if (remainder > 0) {
				List<Map<String, Object>> remainderList = data.subList(merchant * count, len);
				writeExcel(remainderList, response, upPath, name.replace(".", (merchant + 1) + "."), title);
			}
		}
		String zipPath = pyhPath + File.separator + "uploadFiles" + File.separator + zipDirName + File.separator;
		if (!new File(zipPath).exists()) {
			new File(zipPath).mkdirs();
		}
		zipPath = zipPath + zipName;
		String fullPath = "";
		boolean b = CreateExcelZip.createExcelZip(zipPath, new File(upPath));
		if (b) {
			fullPath = fwUrl + zipName;
			File file = new File(upPath);
			if (file.exists()) {
				if (file.isFile()) {// 判断是否是文件
					file.delete();// 删除文件
				} else if (file.isDirectory()) {// 否则如果它是一个目录
					File[] files = file.listFiles();// 声明目录下所有的文件 files[];
					for (int i = 0; i < files.length; i++) {// 遍历目录下所有的文件
						files[i].delete();
					}
					file.delete();// 删除文件夹
				}
			}
		}
		resultMap.put("fullPath", fullPath);
		return resultMap;
	}

	/*
	 * public static Map<String,Object> doExcelByZip(HttpServletRequest
	 * request,HttpServletResponse response, String zipDirName,String
	 * dirName,String[] title,List<Map<String,Object>> data,String name,String
	 * serverIp){ Map<String,Object> resultMap=new HashMap<String,Object>(); final
	 * int count=10000; //服务器访问路径 String fwUrl = request.getScheme() + "://" +
	 * serverIp + ":" + request.getServerPort()+ "/" + "uploadFiles" +
	 * "/"+zipDirName+"/"; // 服务器上传路径 String nowpath =
	 * System.getProperty("user.dir"); String upPath1 = nowpath.replace("bin",
	 * "webapps"); String upPath = upPath1 + File.separator + "uploadFiles" +
	 * File.separator +dirName + File.separator; if(!new File(upPath).exists()){ new
	 * File(upPath).mkdirs(); }
	 * 
	 * String zipName=name.replace("xls","zip");
	 * 
	 * if(data!=null && data.size()>0){ int len =data.size(); int remainder
	 * =len%count;//余数 int merchant=len/count;//取整 if(merchant>0){ for(int
	 * i=0;i<merchant;i++){ List<Map<String,Object>> oneList = data.subList(count*i,
	 * count*(i+1)); writeExcel(oneList,response,upPath,name.replace(".",
	 * (i+1)+"."),title); } } if(remainder>0){ List<Map<String,Object>>
	 * remainderList=data.subList(merchant*count, len);
	 * writeExcel(remainderList,response,upPath,name.replace(".",
	 * (merchant+1)+"."),title); } } String zipPath = upPath1 + File.separator +
	 * "uploadFiles" + File.separator +zipDirName + File.separator; if(!new
	 * File(zipPath).exists()){ new File(zipPath).mkdirs(); }
	 * zipPath=zipPath+zipName; String fullPath=""; boolean b
	 * =CreateExcelZip.createExcelZip(zipPath, new File(upPath)); if(b){
	 * fullPath=fwUrl + zipName; File file=new File(upPath); if(file.exists()){ if
	 * (file.isFile()) {//判断是否是文件 file.delete();//删除文件 } else if
	 * (file.isDirectory()) {//否则如果它是一个目录 File[] files =
	 * file.listFiles();//声明目录下所有的文件 files[]; for (int i = 0;i < files.length;i ++)
	 * {//遍历目录下所有的文件 files[i].delete(); } file.delete();//删除文件夹 } } }
	 * resultMap.put("fullPath", fullPath); return resultMap; }
	 * 
	 */

	private static String writeExcel(List<Map<String, Object>> data, HttpServletResponse response, String upPath,
			String name, String[] title) {
		String result = ExprotExcel.exportExcelForMap(response, upPath, name, title, data);
		return result;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param zipDirName
	 * @param dirName
	 * @param title
	 * @param data
	 * @param name
	 * @param serverHost
	 * @param pyhPath
	 * @param count 导出每个excel内最高包含数据条数，超出将生成多个excel
	 * @return
	 */
	public static Map<String, Object> doExcel(HttpServletRequest request, HttpServletResponse response,
			String zipDirName, String dirName, String[] title, List<Map<String, Object>> data, String name,
			String serverHost, String pyhPath,Integer count) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 服务器访问路径
		String fwUrl = serverHost + "uploadFiles" + "/" + zipDirName + "/";
		// 服务器上传物理路径
		String upPath = pyhPath + File.separator + "uploadFiles" + File.separator + dirName + File.separator;
		//下载地址
		String zipPath = pyhPath + File.separator + "uploadFiles" + File.separator + zipDirName + File.separator;

		String zipName = name.replace("xls", "zip");
		
		int len = data.size();
		int remainder = len % count;// 余数
		int merchant = len / count;// 取整
		//不需要打包
		if(len<=count){
			if (!new File(zipPath).exists()) {
				new File(zipPath).mkdirs();
			}
			
			List<Map<String, Object>> remainderList = data.subList(merchant * count, len);
			writeExcel(remainderList, response, zipPath, name, title);
			resultMap.put("fullPath", fwUrl + name);
			return resultMap;
		}else{
			if (!new File(upPath).exists()) {
				new File(upPath).mkdirs();
			}
			
			if (merchant > 0) {
				for (int i = 0; i < merchant; i++) {
					List<Map<String, Object>> oneList = data.subList(count * i, count * (i + 1));
					writeExcel(oneList, response, upPath, name.replace(".", "_"+(i + 1) + "."), title);
				}
			}
			if (remainder > 0) {
				List<Map<String, Object>> remainderList = data.subList(merchant * count, len);
				writeExcel(remainderList, response, upPath, name.replace(".", "_"+(merchant + 1) + "."), title);
			}
		}
		
		if (!new File(zipPath).exists()) {
			new File(zipPath).mkdirs();
		}
		zipPath = zipPath + zipName;
		String fullPath = "";
		boolean b = CreateExcelZip.createExcelZip(zipPath, new File(upPath));
		if (b) {
			fullPath = fwUrl + zipName;
			File file = new File(upPath);
			if (file.exists()) {
				if (file.isFile()) {// 判断是否是文件
					file.delete();// 删除文件
				} else if (file.isDirectory()) {// 否则如果它是一个目录
					File[] files = file.listFiles();// 声明目录下所有的文件 files[];
					for (int i = 0; i < files.length; i++) {// 遍历目录下所有的文件
						files[i].delete();
					}
					file.delete();// 删除文件夹
				}
			}
		}
		resultMap.put("fullPath", fullPath);
		return resultMap;
	}
	
	
	/**
	 * 保留两位小数
	 * 
	 * @param value
	 * @return
	 */
	public static Double changeTwoDouble(String value) {
		Double douVal = 0d;
		//DecimalFormat df = new DecimalFormat("######0.00");
		try {
			if (value != null && value != "") {
				douVal = (double) Math.round(Double.valueOf(value) * 100) / 100;
				// douVal=Double.valueOf(df.format(Double.valueOf(value)));
			}
		} catch (Exception e) {
			System.out.println(e);
			return 0d;
		}
		return douVal;
	}

	public static double doubleRound(Double number, int count) {
		double result = 0;
		double temp = Math.pow(10, count);
		result = Math.round(number * temp) / temp;
		return result;
	}

	public static void main(String[] args) {
		// System.out.println(doubleRound(0.5,0));
		// System.out.println(changeTwoDouble("0.2332"));
		// System.out.println(DateTime.getDate("yyyyMMddHHmmss"));
		// String drugArray="[{medicineId=cf14e495412911e8a87300163e1408cd, utilId=3,
		// maxWeight=9999, convertRate=20, drugPrice=0.0625, drugDose=1, util=0.5/克袋,
		// outOrIn=0, pinyinCode=GZ, drugName=桂枝, contentWeight=0.5, conflicts=[],
		// drugId=a032e23c8fd243d7b4b10deec40f3ddc,
		// healthProType=7aac6c245587404586df8883f619fb7a}]";
		// drugArray=drugArray.replace("/", "\\/");
		// List<Map<String, Object>> list = JsonUtil.parseJSON2List(drugArray);
		// System.out.println(list.get(0).get("util"));
		// int num=0;
		// System.out.println("ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(num, num+1));
		System.out.println();
	}

	/**
	 * 获取系统时间戳
	 * 
	 * @return
	 */
	public static Integer getSystemTimestamp() {
		return Integer.parseInt((System.currentTimeMillis() / 1000) + "");
	}

	public static Double trankNum(Double num) {
		String number = num.toString();
		Double after = 0.0;
		if (number.substring(number.indexOf(".") + 1, number.length()).length() <= 4) {
			after = Double.valueOf(number);
		} else {
			number = number.substring(0, number.indexOf(".") + 5);
			after = Double.valueOf(number);
		}
		return after;
	}

	// 根据汉字获取拼音首字母
//	public static String charToPinyin(String word) {
//		/***
//		 * ^[\u2E80-\u9FFF]+$ 匹配所有东亚区的语言 ^[\u4E00-\u9FFF]+$ 匹配简体和繁体 ^[\u4E00-\u9FA5]+$
//		 * 匹配简体
//		 */
//		String regExp = "^[\u4E00-\u9FFF]+$";
//		StringBuffer sb = new StringBuffer();
//		if (word == null || "".equals(word.trim())) {
//			return "";
//		}
//		String pinyin = "";
//		for (int i = 0; i < word.length(); i++) {
//			char unit = word.charAt(i);
//			if (match(String.valueOf(unit), regExp))// 是汉字，则转拼音
//			{
//				pinyin = convertSingleHanziToPinyin(unit);
//				sb.append(pinyin.charAt(0));
//			} else {
//				sb.append(unit);
//			}
//		}
//		return sb.toString();
//	}

	/***
	 * 将单个汉字转成拼音
	 * 
	 * @param hanzi
	 * @return
	 */
//	private static String convertSingleHanziToPinyin(char hanzi) {
//		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
//		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//		String[] res;
//		StringBuffer sb = new StringBuffer();
//		try {
//			res = PinyinHelper.toHanyuPinyinStringArray(hanzi, outputFormat);
//			sb.append(res[0]);// 对于多音字，只用第一个拼音
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "";
//		}
//		return sb.toString();
//	}

	/**
	 * 获取指定月份的起始日期和结束日期
	 * 
	 * @param cDate
	 * @return
	 */
	public static Integer[] getDateRangeInMonth(int year, int month) {

		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, Calendar.DAY_OF_MONTH, 0, 0, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		Date startDate = c.getTime();

//		startDate.setHours(0);
//		startDate.setMinutes(0);
//		startDate.setSeconds(0);

		// 获取当前月最后一天
		Calendar ca = Calendar.getInstance();
		ca.set(year, month - 1, Calendar.DAY_OF_MONTH, 0, 0, 0);
		ca.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));

		Date endDate = ca.getTime();

//		endDate.setHours(0);
//		endDate.setMinutes(0);
//		endDate.setSeconds(0);

		int sDate = DateTime.dateToTimestamp(startDate);
		int eDate = DateTime.dateToTimestamp(endDate);

		return new Integer[] { sDate, eDate };
	}

	
	
	/**
	 * 根据出生日期计算年龄
	 * @param birthInfo
	 * @return
	 */
	public  static String getAgeFromBirth(String birthInfo) {
		
		String result = "";
		
		if(StringUtils.isEmpty(birthInfo))
			return "error--患者出生年月不能为空";
		
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if(birthInfo.indexOf("-")>-1) {
			//有月份
			String[] birth = birthInfo.split("-");
			//根据出生年月算年纪
			int birthYear = Integer.parseInt(birth[0]);
			int birthMonth = Integer.parseInt(birth[1]);
			int age=c.get(Calendar.YEAR)-birthYear;
			int month= c.get(Calendar.MONTH)+1-birthMonth;
			if(month<0){
				month=12+month;
				age=age-1;
			}
			result = age+"岁"+month+"个月";
		}else {
			//无月份
			int age = c.get(Calendar.YEAR)-Integer.parseInt(birthInfo);
			result = age+"岁";
		}
		
		return result;
		
	}
	
	//根据出生年月验证年龄
	public static String checkAgeByBirth(String send_age,String birth) {
		
		//根据出生年月计算生日
		String calculate_age = Util.getAgeFromBirth(birth);
		if(calculate_age.indexOf("error")>-1) {
			return calculate_age;
		}else {
			//比较年龄
			String age = send_age.substring(0, send_age.indexOf("岁"));
			if(StringUtils.isEmpty(age))
				return "error--处方患者年龄不能为空!";
			BigDecimal age_send = new BigDecimal(age);
			BigDecimal age_calculate = new BigDecimal(calculate_age.substring(0, calculate_age.indexOf("岁")));
			if(age_send.compareTo(age_calculate)!=0) 
				return "error--处方患者年龄与根据出生年月计算所得年龄不一致";
			if(calculate_age.indexOf("月")>-1&&send_age.indexOf("月")>-1) {
				//比较月份
				String month = send_age.substring(send_age.indexOf("岁")+1, send_age.indexOf("个"));
				if(StringUtils.isEmpty(month))
					return "error--处方患者年龄月份不能为空!";
				BigDecimal month_send  = new BigDecimal(month);
				BigDecimal month_calculate = new BigDecimal(calculate_age.substring(calculate_age.indexOf("岁")+1,calculate_age.indexOf("个")));
				if(month_send.compareTo(month_calculate)!=0)
					return "error--处方患者年龄中月份与计算所得月份不一致";
			}
		}
		
		return "success";
		
	}
	
	
	
	//判断实体属性是否全为空
	public static boolean isNullObject(Object obj){
		
		Class<? extends Object> stuCla = (Class<? extends Object>) obj.getClass();// 得到类对象
		Field[] fs = stuCla.getDeclaredFields();//得到属性集合
		boolean flag = true;
		for (Field f : fs) {//遍历属性
			f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
			Object val = null;
			try {
				val = f.get(obj);
			} catch (IllegalArgumentException e) {
				break;
			} catch (IllegalAccessException e) {
				break;
			}
			// 得到此属性的值
			if(val!=null) {//只要有1个属性不为空,那么就不是所有的属性值都为空
				flag = false;
				break;
			}
		}
		return flag;
		
	}
	
	

}
