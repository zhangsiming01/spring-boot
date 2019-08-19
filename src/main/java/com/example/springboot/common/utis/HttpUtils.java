package com.example.springboot.common.utis;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月25日 上午11:17:38 
* 类说明:http请求格式和超时工具类
 */
public class HttpUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private static final CloseableHttpClient httpClient;
	public static final String CHARSET_GBK = "GBK";
	public static final String CHARSET_UTF8 = "UTF-8";
	/**
	 *  设置超时时间
	 */
	public static final int REQUEST_TIMEOUT = 60000;
	public static final int REQUEST_SOCKET_TIME = 60000;

	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(5000);
		cm.setDefaultMaxPerRoute(500);
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(REQUEST_TIMEOUT)
				.setSocketTimeout(REQUEST_SOCKET_TIME).build();

		httpClient = HttpClients.custom().setConnectionManager(cm)
				.setDefaultRequestConfig(requestConfig)
				.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
				.disableRedirectHandling().build();
		
	}
	

	public static HttpResEntity doGet(String url, Map<String, String> params,
			String charset) {
		HttpResEntity entity = new HttpResEntity();
		if (StringUtils.isEmpty(url)) {
			entity.setMsg("请求地址异常");
			entity.setStatus(404);
			return entity;
		}
		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(
						params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					Object value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(),
								(String) value));
					}
				}
				url += "?"
						+ EntityUtils.toString(new UrlEncodedFormEntity(pairs,
								charset));
			}
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				entity.setData("请求异常");
				entity.setStatus(statusCode);
				return entity;
			}
			HttpEntity httpEntity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(httpEntity, charset);
			}
			EntityUtils.consume(httpEntity);
			response.close();
			entity.setStatus(statusCode);
			entity.setData(result);
			return entity;
		} catch (SocketTimeoutException e) {
			logger.info("请求超时:" + url);
			return null;
		} catch (ConnectTimeoutException e) {
			logger.info("请求超时:" + url);
			return null;
		} catch (HttpHostConnectException e) {
			logger.info("请求超时:" + url);
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public static HttpResEntity doPost(String url, Map<String, String> params,
			String charset) {
		List<NameValuePair> pairs = null;
		if (params != null && !params.isEmpty()) {
			pairs = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, String> entry : params.entrySet()) {
				Object value = entry.getValue();
				if (value != null) {
					pairs.add(new BasicNameValuePair(entry.getKey(),
							(String) value));
				}
			}
		}
		StringEntity httpEntity = null;
		try {
			httpEntity = new UrlEncodedFormEntity(pairs, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return doPost(url, httpEntity, charset);
	}

	@SuppressWarnings("unchecked")
	public static HttpResEntity doPost(String url, String params, String charset,String contentType,boolean isMap) {
		Map<String,String> map = JsonUtil.parseNotThrowException(params, Map.class);
		
		return doPost(url, map, charset);
	}
	
	

	public static HttpResEntity doPost(String url, String params, String charset) {
		StringEntity entity = null;
		entity = new StringEntity(params, charset);
		return doPostSpecial(url, entity, charset);
	}

	public static HttpResEntity doPost(String url, String params,
			String charset, String contentType) {
		StringEntity entity = null;
		entity = new StringEntity(params, charset);
		entity.setContentType(contentType);
		return doPost(url, entity, charset);
	}

	/**
	 * HTTP Post 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数
	 * @param charset
	 *            编码格式
	 * @return 页面内容
	 */
	public static HttpResEntity doPost(String url, StringEntity params,
			String charset) {
		HttpResEntity entity = new HttpResEntity();
		if (StringUtils.isEmpty(url)) {
			entity.setMsg("请求地址异常");
			entity.setStatus(404);
			return entity;
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			if (params != null) {
				httpPost.setEntity(params);
			}
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				entity.setData("请求异常");
				entity.setStatus(statusCode);
				return entity;
			}
			HttpEntity httpEntity = response.getEntity();
			String result = null;
			if (httpEntity != null) {
				result = EntityUtils.toString(httpEntity, charset);
			}
			EntityUtils.consume(httpEntity);
			response.close();
			entity.setStatus(statusCode);
			entity.setData(result);
			return entity;
		} catch (SocketTimeoutException e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		} catch (ConnectTimeoutException e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		} catch (HttpHostConnectException e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		} catch (SocketException e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		} catch (Exception e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		}
	}
	
	/**
	 * HTTP Post 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数
	 * @param charset
	 *            编码格式
	 * @return 页面内容
	 */
	public static HttpResEntity doPostSpecial(String url, StringEntity params,
			String charset) {
		HttpResEntity entity = new HttpResEntity();
		if (StringUtils.isEmpty(url)) {
			entity.setMsg("请求地址异常");
			entity.setStatus(404);
			return entity;
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			if (params != null) {
				httpPost.setEntity(params);
				httpPost.addHeader("Content-Type", "application/json");
			}
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				entity.setData("请求异常");
				entity.setStatus(statusCode);
				return entity;
			}
			HttpEntity httpEntity = response.getEntity();
			String result = null;
			if (httpEntity != null) {
				result = EntityUtils.toString(httpEntity, charset);
			}
			EntityUtils.consume(httpEntity);
			response.close();
			entity.setStatus(statusCode);
			entity.setData(result);
			return entity;
		} catch (SocketTimeoutException e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		} catch (ConnectTimeoutException e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		} catch (HttpHostConnectException e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		} catch (SocketException e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		} catch (Exception e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		}
	}
	
	/**
	 * HTTP Post 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数
	 * @param charset
	 *            编码格式
	 * @return 页面内容
	 */
	public static HttpResEntity post(String url, StringEntity params,
			String charset) {
		HttpResEntity entity = new HttpResEntity();
		if (StringUtils.isEmpty(url)) {
			entity.setMsg("请求地址异常");
			entity.setStatus(404);
			return entity;
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			if (params != null) {
				httpPost.setEntity(params);
			}
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				entity.setData("请求异常");
				entity.setStatus(statusCode);
				return entity;
			}
			HttpEntity httpEntity = response.getEntity();
			String result = null;
			if (httpEntity != null) {
				result = EntityUtils.toString(httpEntity, charset);
			}
			EntityUtils.consume(httpEntity);
			response.close();
			entity.setStatus(statusCode);
			entity.setData(result);
			return entity;
		} catch (SocketTimeoutException e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		} catch (ConnectTimeoutException e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		} catch (HttpHostConnectException e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		} catch (SocketException e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		} catch (Exception e) {
			logger.info("请求超时:" + url+","+e.getMessage());
			return null;
		}
	}

	public static HttpResEntity post(String url, Map<String, String> params,
			String charset) {
		List<NameValuePair> pairs = null;
		if (params != null && !params.isEmpty()) {
			pairs = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, String> entry : params.entrySet()) {
				Object value = entry.getValue();
				if (value != null) {
					pairs.add(new BasicNameValuePair(entry.getKey(),
							(String) value));
				}
			}
		}
		StringEntity httpEntity = null;
		try {
			httpEntity = new UrlEncodedFormEntity(pairs, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return post(url, httpEntity, charset);
	}
	
public static HttpResEntity post(String url , InputStream is){
		
		HttpResEntity entity = new HttpResEntity();
		if (StringUtils.isEmpty(url)) {
			entity.setMsg("请求地址异常");
			entity.setStatus(404);
			return entity;
		}
		
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type", "application/json");
			if (is != null) {
				httpPost.setEntity(new InputStreamEntity(is));
			}
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				entity.setData("请求异常:statusCode"+statusCode);
				entity.setStatus(statusCode);
				return entity;
			}
			HttpEntity httpEntity = response.getEntity();
			String result = null;
			if (httpEntity != null) {
				result = EntityUtils.toString(httpEntity, "utf-8");
			}
			EntityUtils.consume(httpEntity);
			response.close();
			entity.setStatus(statusCode);
			entity.setData(result);
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 发送POST 请求
	 * 
	 * @param url
	 *            请求地址
	 * @param charset
	 *            编码格式
	 * @param params
	 *            请求参数
	 * @return 响应
	 * @throws IOException
	 */
	public static String post(String url, String charset,
			Map<String, String> params, String sign) throws IOException {

		HttpURLConnection conn = null;
		OutputStreamWriter out = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer result = new StringBuffer();
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Charset", charset);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			out = new OutputStreamWriter(conn.getOutputStream(), charset);
			String data = buildQuery(params, charset);
			data += "&sign=" + sign;
			out.write(data);
			out.flush();
			inputStream = conn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);
			String tempLine = null;
			while ((tempLine = reader.readLine()) != null) {
				result.append(tempLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
			logger.info("请求超时:" + url, e);
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			if (out != null) {
				out.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return result.toString();
	}

	/**
	 * 将map转换为请求字符串
	 * <p>
	 * data=xxx&msg_type=xxx
	 * </p>
	 * 
	 * @param params
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String buildQuery(Map<String, String> params, String charset)
			throws IOException {
		if (params == null || params.isEmpty()) {
			return null;
		}

		StringBuffer data = new StringBuffer();
		boolean flag = false;

		for (Entry<String, String> entry : params.entrySet()) {
			if (flag) {
				data.append("&");
			} else {
				flag = true;
			}

			data.append(entry.getKey())
					.append("=")
					.append(URLEncoder.encode(entry.getValue() == null ? ""
							: entry.getValue(), charset));
		}

		return data.toString();

	}
}
