//package com.project_study.my.common.utils;
//
//import com.ithinkdt.common.utils.HttpClientUtil;
//import org.apache.commons.httpclient.HttpMethod;
//import org.apache.commons.io.IOUtils;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//public class FileUtil extends com.ithinkdt.common.file.utils.FileUtil {
//
//	public static File createTemplateFile(){
//		return createTemplateFile(null);
//	}
//
//	public static File createTemplateFile(String contentType){
//		String descPath = FileUtil.buildPath(ApplicationConfigUtil.getTempRootPath(), IDGenerator.genUID() + StringUtil.nval(contentType, "." + contentType));
//		File file = new File(descPath);
//		FileUtil.addTemplateFiles(descPath);
//		return file;
//	}
//
//	public static File writeToTmpFile(byte[] data) throws Exception{
//		return writeToTmpFile(data);
//	}
//
//	public static File writeToTmpFile(byte[] data, String contentType) throws Exception{
//		File file = createTemplateFile(contentType);
//		OutputStream output = null;
//		try{
//			output = new FileOutputStream(file);
//			IOUtils.write(data, output);
//		}finally{
//			IOUtils.closeQuietly(output);
//		}
//
//		return file;
//	}
//
//	public static File writeToTmpFile(InputStream input, String contentType) throws Exception{
//		File file = createTemplateFile(contentType);
//		OutputStream output = null;
//		try{
//			output = new FileOutputStream(file);
//			IOUtils.copy(input, output);
//		}finally{
//			IOUtils.closeQuietly(output);
//		}
//
//		return file;
//	}
//
//	public static File writeToTmpFile(InputStream input) throws Exception{
//		return writeToTmpFile(input, null);
//	}
//
//
//	public static File requestTmpFile(HttpMethod m, String charset, int connectionTimeout, int requestTimeout) throws Exception {
//		HttpMethod method = null;
//		InputStream input = null;
//		File result = null;
//		try {
//			method = HttpClientUtil.createRequest(m, charset, connectionTimeout, requestTimeout);
//			input = method.getResponseBodyAsStream();
//			String fileExt = StringUtil.getFileExtName(m.getPath());
//			result = FileUtil.createTemplateFile(fileExt);
//			if (input != null) {
//				FileUtil.save(result.getAbsolutePath(), input);
//			}
//		} finally {
//			IOUtils.closeQuietly(input);
//			if (method != null) {
//				method.releaseConnection();
//			}
//		}
//		return result;
//	}
//}
