///**
// *
// */
//package com.project_study.quartz.common.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import net.sf.json.JSONObject;
//import org.apache.commons.httpclient.*;
//import org.apache.commons.httpclient.params.HttpMethodParams;
//import org.apache.commons.io.IOUtils;
//
//import java.io.IOException;
//import java.io.InputStream;
//
///**
// * Http Client的共通处理逻辑，提供根据url获取json串，根据url获取文件等功能
// * @author 浩
// *
// */
//@Slf4j
//public class HttpClientUtil {
//
//	/**
//	 * 根据参数创建一个HttpMethod方法
//	 * @author YangDong 2018年9月11日 下午4:02:59
//	 * @param method HttpMethod
//	 * @param charset charset
//	 * @param connectionTimeout 连接超时
//	 * @param requestTimeout 请求超时
//	 * @return HttpMethod
//	 * @throws Exception
//	 */
//	public static HttpMethod createRequest(HttpMethod method, String charset, int connectionTimeout, int requestTimeout) throws Exception {
//		return request(method, charset, connectionTimeout, requestTimeout);
//	}
//	private static HttpMethod request(HttpMethod method, String charset, int connectionTimeout, int requestTimeout) throws Exception {
//		/*
//		 * 使用 GetMethod 来访问一个 URL 对应的网页,实现步骤: 1:生成一个 HttpClinet 对象并设置相应的参数。
//		 * 2:生成一个 GetMethod 对象并设置响应的参数。 3:用 HttpClinet 生成的对象来执行 GetMethod 生成的Get
//		 * 方法。 4:处理响应状态码。 5:若响应正常，处理 HTTP 响应内容。 6:释放连接。
//		 */
//		/* 1 生成 HttpClinet 对象并设置参数 */
//		HttpClient httpClient = new HttpClient();
//		// 设置 Http 连接超时
//		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
//		//if(StringUtil.isNotEmpty(sessionId)){
//			//method.setRequestHeader("Cookie", "JSESSIONID=" + sessionId);
//		//}
//		// 设置 get 请求超时为 5 秒
//		method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, requestTimeout);
//		// 设置请求重试处理，用的是默认的重试处理：请求三次
//		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
//		// 设置Content字符集
//		method.getParams().setContentCharset(charset);
//		if (!"GET".equalsIgnoreCase(method.getName()) && method.getRequestHeader("Content-Type") == null){
//			method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
//		}
//		//Ajax
//		//method.addRequestHeader("X_REQUESTED_WITH", "XMLHttpRequest");
//		//InputStream response = null;
//
//		/* 3 执行 HTTP 请求 */
//		try {
//			int statusCode = httpClient.executeMethod(method);
//			/* 4 判断访问的状态码 */
//			if (statusCode != HttpStatus.SC_OK) {
//				System.err.println("请求出错: " + method.getStatusLine());
//			}
//			/* 5 处理 HTTP 响应内容 */
//			// HTTP响应头部信息，这里简单打印
////			Header[] headers = method.getResponseHeaders();
////			for (Header h : headers)
////				System.out
////						.println(h.getName() + "------------ " + h.getValue());
//			// 读取 HTTP 响应内容，这里简单打印网页内容
//			//byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
//			//response = new String(responseBody, charset);
//			//System.out.println("----------response:" + response);
//			// 读取为 InputStream，在网页内容数据量大时候推荐使用
//			// InputStream response = getMethod.getResponseBodyAsStream();
//		} catch (HttpException e) {
//			// 发生致命的异常，可能是协议不对或者返回的内容有问题
//			log.error("请检查输入的URL!", e);
//		} catch (IOException e) {
//			// 发生网络异常
//			log.error("发生网络异常!", e);
//		} finally {
//			/* 6 .释放连接 */
//			//getMethod.releaseConnection();
//		}
//		return method;
//	}
//
//	public static String requestString(HttpMethod m, String charset, int connectionTimeout, int requestTimeout) throws Exception {
//		InputStream input = null;
//		HttpMethod method = null;
//		try{
//			method = request(m, charset, connectionTimeout, requestTimeout);
//			input = method.getResponseBodyAsStream();
//			if(input != null){
//				return IOUtils.toString(input, charset);
//			}
//		}catch (Exception e){
//			log.error("RequestString Exception", e);
//		}finally{
//			IOUtils.closeQuietly(input);
//			method.releaseConnection();
//		}
//		return "";
//	}
//
//	public static JSONObject requestJson(HttpMethod m, String charset, int connectionTimeout, int requestTimeout) throws Exception {
//		String jsonString = requestString(m, charset, connectionTimeout, requestTimeout);
//		if(StringUtil.isEmpty(jsonString)){
//			return new JSONObject();
//		}else{
//			return JSONObject.fromObject(jsonString);
//		}
//	}
//}
