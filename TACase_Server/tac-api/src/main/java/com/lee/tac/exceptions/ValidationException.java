package com.lee.tac.exceptions;

// 业务层的异常优先在API层定义，以便于异常在分布式调用中传递，避免出现未知异常类型
public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ValidationException(String msg) {
		super(msg);
	}
}
