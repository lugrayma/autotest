package utils;

import model.bicyclingbean;

/**
 * 
 * @author Administrator
 * @apiNote : Get请求，判断是否有请求头，处理返回的StringJSON，最终返回expectedResult。
 */
public class HttpGetActualResult {
	public static String httpGetResult(String URL, bicyclingbean bcycb) {
		// 请求截取请求参数，放入OKHTTP3请求参数中

		OkHttpUtils oku = OkHttpUtils.builder();
		String str_param = bcycb.getRequestParameter();
		String str_head = bcycb.getRequestHeader();
		String result = null;
		// 如果请求参数和请求头都为null
		if (str_param == null && str_head == null) {
			result = oku.url(URL).get().sync();
			// 如果请求参数为null但请求头不为null
		} else if (str_param == null && str_head != null) {
			// 根据逗号截取字符串数组
			String[] str1 = str_head.split(",");
			for (int i = 0; i < str1.length; i++) {
				// 根据"="截取字符串数组
				String[] str2 = str1[i].split("=");
				// str2[0]为KEY,str2[1]为值
				oku.addHeader(str2[0], str2[1]);
			}
			result = oku.url(URL).get().sync();
			// 如果请求参数不为null和请求头为null
		} else if (str_param != null && str_head == null) {
			// 根据&截取字符串数组
			String[] str1 = str_param.split("&");
			for (int i = 0; i < str1.length; i++) {
				// 根据"="截取字符串数组
				String[] str2 = str1[i].split("=");
				// str2[0]为KEY,str2[1]为值
				oku.addParam(str2[0], str2[1]);
			}
			result = oku.url(URL).get().sync();
			// 如果请求参数和请求头都不为null
		} else if (str_param != null && str_head != null) {
			// 根据逗号截取字符串数组
			String[] str_h = str_head.split(",");
			for (int i = 0; i < str_h.length; i++) {
				// 根据"="截取字符串数组
				String[] str2 = str_h[i].split("=");
				// str2[0]为KEY,str2[1]为值
				oku.addHeader(str2[0], str2[1]);
			}

			// 根据&截取字符串数组
			String[] str_p = str_param.split("&");
			for (int i = 0; i < str_p.length; i++) {
				// 根据"="截取字符串数组
				String[] str2 = str_p[i].split("=");
				// str2[0]为KEY,str2[1]为值
				oku.addParam(str2[0], str2[1]);
				result = oku.url(URL).get().sync();
			}
			result = oku.url(URL).get().sync();
		}

		return result;
	}
}
