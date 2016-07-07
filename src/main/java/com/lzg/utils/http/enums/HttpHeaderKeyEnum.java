package com.lzg.utils.http.enums;

/**
 * http head 相关的key，后期自己有需要自己加
 * 
 * @author lzg
 * @date 2016年6月28日
 */
public enum HttpHeaderKeyEnum {

	Accept("Accept"),

	Authorization("Authorization"),

	ContentType("Content-Type"),

	Connection("Connection"),

	UserAgent("User-Agent");

	private String key;

	private HttpHeaderKeyEnum(String key) {
		this.key = key;
	}

	public String toString() {
		return this.key;
	};
}
