package controller;

import java.util.Iterator;

import org.apache.ibatis.session.SqlSession;
import config.Assertion;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import model.bicyclingbean;

public class CoreRun {
	public static Object result_final;
	public static Object jarr;

	public static void executer(String DT, String veriftypoints, String Result, SqlSession session, int i,
			bicyclingbean bicyclingbean) {
		if (DT.equals("allEquals")) {// 如果是message类型的返回数据，直接比较文本。
			boolean br = Result.equals(veriftypoints);
			if (br) {
				session.selectOne("model.update1", i);
			} else {
				session.selectOne("model.update0", i);
			}
			Assertion.verifyTrue(br, "实际结果:" + Result + '\r' + "不等于预期结果:" + veriftypoints);
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
			// 实际结果result_json
			JSONObject result_json = JSONUtil.parseObj(Result);
			// 找出result_json中对应key的值
			getAllKey(result_json, vp_expected_key);
			// 断言实际结果包含或等于预期结果
			if (result_final != null) {
				boolean br = result_final.toString().equals(veriftypoints_expected_value);
				if (br) {
					session.selectOne("model.update1", i);
					Assertion.verifyTrue(br, "实际结果:" + vp_expected_key + '=' + result_final + '\r' + "不等于预期结果:"
							+ veriftypoints_expected_value);
				}
			} else {
				session.selectOne("model.update0", i);
				Assertion.verifyNotNull(result_final,
						"无效的字段 " + vp_expected_key + "或者无效的预期值  " + veriftypoints_expected_value);
			}

		} else if (DT.equals("JSONArray")) {//// 如果是JSONArray类型的返回数据，需要剥洋葱。
			// 查找数据格式"paths":"["0":{}]"
			String strat1 = "{\""; // 你指定的前缀字符串
			String end1 = "\":[{\""; // 你指定的后缀字符串
			int startIndex1 = veriftypoints.indexOf(strat1) + strat1.length();
			int endIndex1 = veriftypoints.indexOf(end1); // 上两句获取的是要取出字符串的前后坐标
			String vp_expected_key = veriftypoints.substring(startIndex1, endIndex1); // 你要的结果
//				System.out.println(vp_expected_key);//
			//
			String strat2 = "[{"; // 你指定的前缀字符串
			String end2 = "]}}"; // 你指定的后缀字符串
			int startIndex2 = veriftypoints.indexOf(strat2) + strat2.length();
			int endIndex2 = veriftypoints.indexOf(end2); // 上两句获取的是要取出字符串的前后坐标
			// 预期结果需要检查的值
			String vp_expected_value = veriftypoints.substring(startIndex2, endIndex2); // 你要的结果
			System.out.println(vp_expected_value);
			// 实际结果result_json
			JSONObject result_json2 = JSONUtil.parseObj(Result);
			getAllKey(result_json2, vp_expected_key);
			if (result_final != null) {
				boolean br = result_final.toString().equals(vp_expected_value);
				if (br) {
					session.selectOne("model.update1", i);
				} else {
					session.selectOne("model.update0", i);
				}
				Assertion.verifyTrue(br,
						"实际结果:" + vp_expected_key + '=' + result_final + '\r' + "不等于预期结果:" + vp_expected_value);
			} else {
				Assertion.verifyNotNull(result_final, "实际结果:" + result_json2 + '\r' + "不等于预期结果:" + vp_expected_value);
			}
		} else if (DT.equals("Contains")) {// 包含
			// 多个字段包含
			if (veriftypoints.contains(", ")) {
				String[] str = veriftypoints.split(", ", -1);
				int num = 0;
				for (int j = 0; j < str.length; j++) {
					boolean br = Result.contains(str[j]);
					// 111 010 011
					if (!br) {
						num = num - 1;
					}
				}
				if (num < 0) {
					session.selectOne("model.update0", i);
				} else if (num == 0) {
					session.selectOne("model.update1", i);
				}
				Assertion.verifyTrue(num == 0, "实际结果:" + Result + '\r' + "不包含" + "预期结果：" + veriftypoints);
			} else if (!veriftypoints.contains(", ")) {
				// 单个字段包含
				boolean br = Result.contains(veriftypoints);
				if (br) {
					session.selectOne("model.update1", i);
				} else {
					session.selectOne("model.update0", i);
				}
				Assertion.verifyTrue(br, "实际结果:" + Result + '\r' + "不包含预期结果：" + veriftypoints);
			}
		} else if (DT.equals("NoContains")) {
			if (!veriftypoints.contains(", ")) {
				boolean br = Result.contains(veriftypoints);
				if (!br) {
					session.selectOne("model.update1", i);
				} else {
					session.selectOne("model.update0", i);
				}
				Assertion.verifyTrue(!br,
						"实际结果:" + Result + '\r' + "包含" + "预期结果：" + veriftypoints + "在内，但是预期想要的结果是不包含");
			} else {
				String[] str = veriftypoints.split(", ", -1);
				int num = 0;
				for (int j = 0; j < str.length; j++) {
					boolean br = Result.contains(str[j]);
					// 0 0 0 100 010 001 110 101
					if (br == true) {
						num = num + 1;
					}
				}
				if (num > 0) {
					session.selectOne("model.update0", i);
				} else if (num == 0) {
					session.selectOne("model.update1", i);
				}
				Assertion.verifyTrue(!(num == 0),
						"实际结果:" + Result + '\r' + "包含" + "预期结果：" + veriftypoints + "在内，但是预期想要的结果是不包含");

			}
		}
	}

	// 递归找到所有KEY
	public static void getAllKey(JSONObject jsonObject, String vp_key) {
		Iterator<String> keys = jsonObject.keySet().iterator();// jsonObject.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			if (key.equals(vp_key)) {
				result_final = jsonObject.get(vp_key);
//					System.out.println("asdasdasdasfdsfvdsdsfdsf" + result_final);
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
//					result_final2 = object;
					if (object == null) {
						getAllKey(job, vp_key);
					}
				}
			}
		}
	}
}
