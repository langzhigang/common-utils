package com.lzg.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.lzg.utils.http.enums.HttpHeaderKeyEnum;

/**
 * Http发送 get post 请求工具类
 * 
 * @author lzg
 * @date 2016年7月7日
 */
public class HttpUtils {

	private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private final static Gson gson = new Gson();

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 *            (注意：很多restful风格的url,比如:
	 *            /get/user/{id}，这种参数是直接拼在url上面的。不要在param里面传)
	 * 
	 * @param responseObjType
	 *            返回的数据类型
	 * 
	 * @return T 所代表远程资源的响应结果
	 */
	@SuppressWarnings("unchecked")
	public static <T> T sendGet(String url, String params, Class<T> responseObjType) {
		T result = null;
		BufferedReader in = null;
		try {
			// 拼接参数
			String urlNameString = url + "?" + params;

			URL realUrl = new URL(urlNameString);

			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();

			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			// 建立实际的连接
			connection.connect();

			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			// 如果返回参数是String类型，就不作处理
			if (responseObjType.newInstance() instanceof String) {
				result = (T) sb.toString();
			} else {
				result = gson.fromJson(sb.toString(), responseObjType);
			}
		} catch (Exception e) {
			logger.info("发送GET请求出现异常！异常信息为:{}", e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				logger.info("关闭BufferedReader异常！异常信息为:{}", e2);
			}
		}

		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            参数dto，如果有多个对象，也包装成一个dto
	 * 
	 * @param responseObjType
	 *            返回的数据类型
	 * 
	 * @return T 所代表远程资源的响应结果
	 */
	public static <T> T sendPost(String url, Object param, Class<T> responseObjType) {
		return sendPost(url, param, responseObjType, null);
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            参数dto，如果有多个对象，也包装成一个dto
	 * 
	 * @param responseObjType
	 *            返回的数据类型
	 * 
	 * @param headMap
	 *            设置http head相关的属性
	 * 
	 * @return T 所代表远程资源的响应结果
	 */
	@SuppressWarnings("unchecked")
	public static <T> T sendPost(String url, Object param, Class<T> responseObjType,
			Map<HttpHeaderKeyEnum, String> headMap) {
		PrintWriter out = null;
		BufferedReader in = null;
		T result = null;

		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();

			// 设置通用的请求属性
			conn.setRequestProperty(HttpHeaderKeyEnum.Accept.toString(), "*/*");
			conn.setRequestProperty(HttpHeaderKeyEnum.Connection.toString(), "Keep-Alive");
			conn.setRequestProperty(HttpHeaderKeyEnum.UserAgent.toString(),
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty(HttpHeaderKeyEnum.ContentType.toString(), "application/json;charset=utf-8;");

			// 设置传入的head值，如果和默认冲突，就覆盖默认值
			if (headMap != null) {
				Set<HttpHeaderKeyEnum> keySet = headMap.keySet();
				for (HttpHeaderKeyEnum key : keySet) {
					conn.setRequestProperty(key.toString(), headMap.get(key));
				}
			}

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数，如果为String类型就直接发送
			if (param instanceof String) {
				out.print(param);
			} else {
				out.print(gson.toJson(param));
			}
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			// 如果返回参数是String类型，就不作处理
			if (responseObjType.newInstance() instanceof String) {
				result = (T) sb.toString();
			} else {
				result = gson.fromJson(sb.toString(), responseObjType);
			}
		} catch (Exception e) {
			logger.info("发送POST请求出现异常！异常信息为:{}", e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e2) {
				logger.info("关闭IO流异常！异常信息为:{}", e2);
			}
		}
		return result;
	}
}