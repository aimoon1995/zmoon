package com.project_study.my.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 
 * com.ithinkdt.web.common.enums.ErrorCodeEnums.java

 * 定义系统中的错误种类代码， 
 * @date 2018年9月26日
 * @author:YangDong
 * @version 1.0
 * @since JDK1.6
 */
public enum ErrorCodeEnums {
	SUCCESSFUL("0000"), // 处理成功
	
	BUSINESS_EXCEPTION("1000"), // 业务异常
	SYSTEM_EXCEPTION("1001"), // 系统异常
	NOT_LOGIN("1002"), // 用户未登录
	NO_ACCESS("1003"), // 无权限
	REFRENCE_NOT_EXIST("1004"), // 引用不存在
	DATA_FORMAT_ERROR("1005"), // 数据格式错误
	DATA_NOT_EXIST("1006"), // 数据不存在或被删除
	KEY_CONFLICT("1007"), // 主键冲突
	PARAM_VALIDATION_ERROR("1008"), // 参数验证错误
	SERVICE_UNAVAILABLE("1009"), // 服务不可用
	
	
	Request_Bad("4000"), // 请求错误
	Request_Unauthorized("4001"), // 请求超时
	Request_Payment_Required("4002"), // 该状态码是为了将来可能的需求而预留的。
	Request_Forbidden("4003"), // 服务器已经理解请求，但是拒绝执行它
	Request_Not_Found("4004"), // 请求失败，请求所希望得到的资源未被在服务器上发现
	Request_Method_not_allowed("4005"), // 求行中指定的请求方法不能被用于请求相应的资源
	Request_Not_Acceptable("4006"), // 请求的资源的内容特性无法满足请求头中的条件，因而无法生成响应实体。
	Request_Proxy_Authentication_Required("4007"), // 与401响应类似，只不过客户端必须在代理服务器上进行身份验证
	Request_Timeout("4008"), // 请求超时。客户端没有在服务器预备等待的时间内完成一个请求的发送。客户端可以随时再次提交这一请求而无需进行任何更改。
	Request_Conflict("4009"), // 由于和被请求的资源的当前状态之间存在冲突，请求无法完成
	Request_Gone("4010"), // 被请求的资源在服务器上已经不再可用，而且没有任何已知的转发地址
	Request_Length_Required("4011"), // 服务器拒绝在没有定义 Content-Length 头的情况下接受请求
	Request_Precondition_Failed("4012"), // 服务器在验证在请求的头字段中给出先决条件时，没能满足其中的一个或多个。
	Request_Request_Entity_Too_Large("4013"), // 服务器拒绝处理当前请求，因为该请求提交的实体数据大小超过了服务器愿意或者能够处理的范围
	Request_Request_URI_Too_Long("4014"), // 请求的URI 长度超过了服务器能够解释的长度，因此服务器拒绝对该请求提供服务
	Request_Unsupported_Media_Type("4015"), // 对于当前请求的方法和所请求的资源，请求中提交的实体并不是服务器中所支持的格式，因此请求被拒绝。
	Request_Requested_Range_Not_Satisfiable("4016"), // 如果请求中包含了 Range 请求头，并且 Range 中指定的任何数据范围都与当前资源的可用范围不重合，同时请求中又没有定义 If-Range 请求头，那么服务器就应当返回416状态码。
	Request_Expectation_Failed("4017"), // 在请求头 Expect 中指定的预期内容无法被服务器满足，或者这个服务器是一个代理服务器，它有明显的证据证明在当前路由的下一个节点上，Expect 的内容无法被满足。
	Request_too_many_connections("4021"), // There are too many connections from your internet address
	Request_Unprocessable_Entity("4022"), // 请求格式正确，但是由于含有语义错误，无法响应。（RFC 4918 WebDAV）
	Request_Locked("4023"), // 当前资源被锁定。（RFC 4918 WebDAV）
	Request_Failed_Dependency("4024"), // 由于之前的某个请求发生的错误，导致当前请求失败，例如 PROPPATCH。（RFC 4918 WebDAV）
	Request_Unordered_Collection("4025"), // 在WebDav Advanced Collections 草案中定义，但是未出现在《WebDAV 顺序集协议》（RFC 3658）中。
	Request_Upgrade_Required("4026"), // 客户端应当切换到TLS/1.0。（RFC 2817）
	Request_Retry_With("4049"), // 由微软扩展，代表请求应当在执行完适当的操作后进行重试。
	Request_Unavailable_For_Legal_Reasons("4051"), // Unavailable For Legal Reasons
	
	Internal_Server_Error("5000"), // 服务器错误
	Not_Implemented("5001"), // 服务器错误
	Bad_Gateway("5002"), // 服务器错误
	Service_Unavailable("5003"), // 服务器错误
	Gateway_Timeout("5004"), // 服务器错误
	HTTP_Version_Not_Supported("5005"), // 服务器错误
	
	Unparseable_Response_Headers("6000"); // 源站没有返回响应头部，只返回实体内容
	
	
	private String code;

	ErrorCodeEnums(String code) {
		this.code = code;
	}
	@JsonValue
	public String getCode() {
		return this.code;
	}
}