package com.jia.ocr.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import com.jia.ocr.util.support.HttpApiClient;
import com.jia.ocr.util.support.LogTypeEnum;

public class Util {

	public static final String UPLOADERRORMSG = "{\"code\":-1,\"message\":\"获取验证码文件失败\"}";
	public static final String INVALIDMSG = "{\"code\":-2,\"message\":\"非法请求\"}";
	public static final String SUCCESSMSG = "{\"code\":0,\"message\":\"识别成功\",\"data\":\"?\"}";

	
	/*	String recognizeResult = HttpApiClient.recognize("1996", // 软件ID. 如何获取? (参见
																// http://www.zhima365.com/jump/api_help_software.php)
				"41c572216c252b4150b8871645fa9035", // 软件key. 如何获取? (参见
													// http://www.zhima365.com/jump/api_help_software.php)
				"imasdf", // 需要注册用户, 然后联系客服QQ1766515174充入免费测试题分. 注册地址:
							// http://www.zhima365.com/api.php?id=464
				"ts123456", // 用户的密码
	 * */
	public static String getCode(File f) {
		File imageFileTest = f;
		String recognizeResult = HttpApiClient.recognize("3", // 软件ID. 如何获取? (参见
																// http://www.zhima365.com/jump/api_help_software.php)
				"623527b90698a47ec626043dac04a0f1", // 软件key. 如何获取? (参见
													// http://www.zhima365.com/jump/api_help_software.php)
				"838279033", // 需要注册用户, 然后联系客服QQ1766515174充入免费测试题分. 注册地址:
							// http://www.zhima365.com/api.php?id=464
				"a12345678", // 用户的密码
				imageFileTest, // 需要上传的文件
				"1040",//"1040", // 图片类型. 图片类型是什么? (参见8492坐标
						// http://www.zhima365.com/jump/api_help_picture_type.php)
				20, // 设置超时时间(单位:秒)
				"", // 备注
				LogTypeEnum.YES); // 是否记录日志
		System.out.println("识别结果：" + recognizeResult);
		String[] recognizeResults = recognizeResult.split("\\|");
		// String queryBalanceResult = HttpApiClient.queryBalance(
		// "imlisa", // 需要注册用户, 然后联系客服QQ1766515174充入免费测试题分. 注册地址:
		// http://www.zhima365.com/api.php?id=464
		// "imlisa"); // 用户的密码

		HttpApiClient.reportError(recognizeResults[3]);  
		return recognizeResults[1];
	}
	
	
	
	public static File getCodeFile(HttpServletRequest req) {

		File f = new File("/img");
		f.mkdir();
		try {
			f = new File(f, System.currentTimeMillis() + ".jpeg");
			System.out.println(f.getAbsolutePath());
			byte[] data = new byte[req.getContentLength()];
			InputStream in = req.getInputStream();
			int len = in.read(data);
			while (len < data.length) {
				int bytes = in.read(data, len, data.length);
				len += bytes;
			}
			String boundary = req.getHeader("Content-Type").split("=")[1];
			System.out.println("boundary:" + boundary);
			String result = new String(data, 0, len, "ISO-8859-1");
			String type = "";
			Pattern p = Pattern.compile("Content-Type.*\\s");
			Matcher m = p.matcher(result);
			if (m.find()) {
				type = result.substring(m.start() + 14, m.end());
				System.out.println("type:" + type);
			}
			int start = result.indexOf("\n", result.indexOf(type)) + 3;
			int end = result.lastIndexOf("--" + boundary) - 2;
			result = result.substring(start, end);
//			System.out.println(result);
			int filelen=result.getBytes("ISO-8859-1").length;
			System.out.println("code file size:"+filelen+" byte");
			if(filelen<1){
				return null;
			}
			FileOutputStream fout = new FileOutputStream(f);
//			System.out.println(f.getAbsolutePath());
			fout.write(result.getBytes("ISO-8859-1"));
			fout.close();
		} catch (Exception e) {
			System.out.println("Upload File Fail...");
			e.printStackTrace();
			return null;
		}
		return f;
	}
	
	public static File getUploadFile(HttpServletRequest req) {

		File f = new File("/file");
		String fileName="";
		f.mkdir();
		try {
			byte[] data = new byte[req.getContentLength()];
			InputStream in = req.getInputStream();
			int len = in.read(data);
			while (len < data.length) {
				int bytes = in.read(data, len, data.length);
				len += bytes;
			}
			String boundary = req.getHeader("Content-Type").split("=")[1];
			System.out.println("boundary:" + boundary);
			String result = new String(data, 0, len, "ISO-8859-1");
			result=result+"Content+Type: image/jpeg";
//			System.out.println(result);
			String type = "";
			System.out.println(System.currentTimeMillis());
			Pattern p = Pattern.compile("Content-Type.*\\s");
			Matcher m = p.matcher(result);
			if (m.find()) {
				type = result.substring(m.start() + 14, m.end());
				System.out.println("type:" + type);
			}
			p=Pattern.compile("filename=\".*\"");
			m=p.matcher(result);
			
			if(m.find()){
				fileName = result.substring(m.start() + 10, m.end()-1);
				System.out.println("fileName:" + fileName);
			}
			System.out.println(System.currentTimeMillis());
			f = new File(f,fileName);
			System.out.println(f.getAbsolutePath());
			int start = result.indexOf("\n", result.indexOf(type)) + 3;
			int end = result.lastIndexOf("--" + boundary) - 2;
			result = result.substring(start, end);
//			System.out.println(result);
			int filelen=result.getBytes("ISO-8859-1").length;
			System.out.println("code file size:"+filelen+" byte");
			if(filelen<1){
				return null;
			}
			FileOutputStream fout = new FileOutputStream(f);
//			System.out.println(f.getAbsolutePath());
			fout.write(result.getBytes("ISO-8859-1"));
			fout.close();
		} catch (Exception e) {
			System.out.println("Upload File Fail...");
			e.printStackTrace();
			return null;
		}
		return f;
	}
}
