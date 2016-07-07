package com.lzg.utils.properties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 动态读取properties文件中的内容
 * 
 * @author lzg
 * @date 2016年7月7日
 */
public class PropertiesUtil {

	/**
	 * 动态读取 properties文件的内容
	 * 
	 * @param filePath
	 *            文件路径,maven工程在resources根目录下的文件，直接写文件名
	 * @param key
	 * @return value
	 * @throws IOException
	 */
	public static String getValue(String filePath, String key) throws IOException {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		InputStreamReader insr = new InputStreamReader(in, "UTF-8");
		Properties props = new Properties();
		props.load(insr);
		return props.getProperty(key);
	}

	/**
	 * 写入properties文件（是写入target\classes目录下，所以下次打包的时候可能就失效了）
	 * 
	 * @param filePath
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public static void setValue(String filePath, String key, String value) throws IOException {
		Properties props = new Properties();

		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		InputStreamReader insr = new InputStreamReader(in, "UTF-8");
		props.load(insr);

		OutputStream out = new FileOutputStream(
				Thread.currentThread().getContextClassLoader().getResource(filePath).getFile());
		props.setProperty(key, value);
		props.store(out, "test");
	}
}
