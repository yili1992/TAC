package com.lee.tac.requests;

import java.io.Serializable;

import com.lee.tac.dto.Page;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class CommonRequest<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private T requestData;

	private boolean flag;

    private Page page;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public T getRequestData() {
		return requestData;
	}

	public void setRequestData(T requestData) {
		this.requestData = requestData;
	}

    public Page getPage() {
        return page;
    }

    public Page getPage(boolean adapter) {
        if (!adapter) {
            return getPage();
        } else {
            if (null == page) {
                page = new Page();
            }
            if (page.getCurrentPage() < 1) {
                page.setCurrentPage(1);
            }
            return page;
        }
    }

    public void setPage(Page page) {
        this.page = page;
    }

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
