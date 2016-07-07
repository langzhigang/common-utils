package com.lzg.utils.http;

import org.junit.Test;

public class HttpUtilsTest {

	@Test
	public void testGet() {
		String base = "http://test.app.wang-guanjia.com/background-manage";
		String url = base + "/authority/role/list";
		String result = HttpUtils.sendGet(url, null, String.class);
		System.out.println(result);
	}

	@Test
	public void testPost() {
		String base1 = "http://test.app.wang-guanjia.com/background-manage";
		String url1 = base1 + "/authority/role/add";
		String role = "{\"name\":\"test111\"}";
		String result = HttpUtils.sendPost(url1, role, String.class);
		System.out.println(result);
	}
}
