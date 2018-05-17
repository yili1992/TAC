package com.lee.tac.responses;

import com.lee.tac.dto.Page;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class CommonResponse<T> implements Serializable {
	public final static String OK = "00000";
	public final static String ERROR = "99999";
	public final static String ERROR_MSG = "系统繁忙，请稍后再试";
	public final static String PARAMS_ILLEGAL = "99998";
	public final static String PARAMS_ILLEGAL_MSG = "请求参数不合法";
	private static final long serialVersionUID = -5485171725326231761L;

	private String code = OK;// 默认无异常

	private String msg;

	private T data;

    private Page page;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * fast fail
	 * @param code
	 * @param msg
	 */
	public void fail(String code, String msg) {
		if (null == code || "" == code) {
			code = ERROR;
		}
		if (null == msg || "" == msg) {
			msg = ERROR_MSG;
		}
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
