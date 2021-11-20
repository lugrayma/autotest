package controller;

import java.util.Iterator;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import junit.framework.Assert;

public class CoreRun {
	public static Object result_final;
	public static Object result_final2;
	public static Object jarr;

	public static void executer(String DT, String veriftypoints, String Result) {
		if (DT.equals("String")) {// 如果是字符串类型的返回数据，直接比较文本。
			int br = Result.indexOf(veriftypoints);
			Assert.assertTrue("实际结果:" + Result + '\r' + "不包含或等于预期结果:" + veriftypoints, br >= 0);
		} else if (DT.equals("JSONObject")) {// 如果是JSONObject类型的返回数据，需要剥洋葱。
			// 查找数据格式{"destination":"X"}
			String strat = "{\""; // 你指定的前缀字符串
			String end = "\":\""; // 你指定的后缀字符串
			int startIndex = veriftypoints.indexOf(strat) + strat.length();
			int endIndex = veriftypoints.indexOf(end); // 上两句获取的是要取出字符串的前后坐标
			String vp_expected_key = veriftypoints.substring(startIndex, endIndex); // 你要的结果

			JSONObject veriftypointsJson = JSONUtil.parseObj(veriftypoints);
			// 预期结果为表中定义{"destination":"X"}的X
			String veriftypoints_expected_value = (String) veriftypointsJson.get(vp_expected_key);
//			System.out.println(veriftypoints_expected_value);
			// 实际结果result_json
			JSONObject result_json = JSONUtil.parseObj(Result);
			// 找出result_json中对应key的值
			getAllKey(result_json, vp_expected_key);
			// 断言实际结果包含或等于预期结果
			int br = result_final.toString().indexOf(veriftypoints_expected_value);
			Assert.assertTrue("实际结果:" + vp_expected_key + '=' + result_final + '\r' + "不包含或等于预期结果:"
					+ veriftypoints_expected_value, br >= 0);
		} else if (DT.equals("JSONArray")) {//// 如果是JSONArray类型的返回数据，需要剥洋葱。
			// 查找数据格式"paths":"["0":{}]"
			String strat1 = "{\""; // 你指定的前缀字符串
			String end1 = "\":[{\""; // 你指定的后缀字符串
			String vp_expected_key = matchStr(veriftypoints, strat1, end1);

			String strat2 = "[{"; // 你指定的前缀字符串
			String end2 = "]}}"; // 你指定的后缀字符串
			String vp_expected_value = matchStr(veriftypoints, strat2, end2);

			// 实际结果result_json
			JSONObject result_json2 = JSONUtil.parseObj(Result);
			getAllKey(result_json2, vp_expected_key);
			if (result_final != null && result_final2 == null) {
				int br = result_final.toString().indexOf(vp_expected_value);
				Assert.assertTrue(
						"实际结果:" + vp_expected_key + '=' + result_final + '\r' + "不包含或等于预期结果:" + vp_expected_value,
						br >= 0);
			} else if (result_final2 != null) {
				int br = result_final2.toString().indexOf(vp_expected_value);
				Assert.assertTrue(
						"实际结果:" + vp_expected_key + '=' + result_final2 + '\r' + "不包含或等于预期结果:" + vp_expected_value,
						br >= 0);
			}
		}
	}

	/**
	 * @1
	 * 
	 * @param veriftypoints
	 * @param strat
	 * @param end
	 * @return vp_expected/key、value
	 */
	private static String matchStr(String veriftypoints, String strat, String end) {
		int startIndex = veriftypoints.indexOf(strat) + strat.length();
		int endIndex = veriftypoints.indexOf(end); // 上两句获取的是要取出字符串的前后坐标
		// 预期结果需要检查的值
		String vp_expected = veriftypoints.substring(startIndex, endIndex); // 你要的结果
		return vp_expected;
	}

	/**
	 * @2
	 * 
	 * @param jsonObject
	 * @param vp_key
	 * @return result_final /result_final2
	 */
	// 递归找到所有KEY、value
	private static void getAllKey(JSONObject jsonObject, String vp_key) {
		Iterator<String> keys = jsonObject.keySet().iterator();// jsonObject.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			if (key.equals(vp_key)) {
				result_final = jsonObject.get(vp_key);
//				System.out.println("asdasdasdasfdsfvdsdsfdsf" + result_final);
			} else if (jsonObject.get(key) instanceof JSONObject) {// JSON对象
				JSONObject innerObject = (JSONObject) jsonObject.get(key);
				getAllKey(innerObject, vp_key);
			} else if (jsonObject.get(key) instanceof JSONArray) {
				jarr = jsonObject.get(key);
				JSONArray array = JSONUtil.parseArray(jarr);
				// 遍历当前key数组
				for (int i = 0; i < array.size(); i++) {
					JSONObject job = array.getJSONObject(i);
					Object object = job.get(vp_key);
					// 把当前值返回Ruselt_final2
					result_final2 = object;
					if (result_final2 == null) {
						getAllKey(job, vp_key);
					}
				}
			}
		}
	}
}
