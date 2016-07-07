package com.lzg.utils.properties;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 测试PropertiesUtil类
 * 
 * @author lzg
 * @date 2016年7月7日
 */
public class PropertiesUtilTest {

	@Test
	public void testGet() throws IOException, InterruptedException {
		Object value = PropertiesUtil.getValue("test.properties", "test.url");
		System.out.println(value);

		Thread.sleep(TimeUnit.SECONDS.toMillis(10));// 休息10秒,利用这个时间去修改
													// test.properties中的值
		value = PropertiesUtil.getValue("test.properties", "test.url");
		System.out.println(value);
	}

	@Test
	public void testSet() throws IOException {
		PropertiesUtil.setValue("test.properties", "test", "test");
	}
}
