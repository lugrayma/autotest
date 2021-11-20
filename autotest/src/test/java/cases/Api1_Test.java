package cases;

import java.io.IOException;
import java.util.Iterator;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import config.TestConfig;
import controller.CoreRun;
import junit.framework.Assert;
import model.InterfaceName;
import model.bean1;
import model.bicyclingbean;
import utils.BundleTakeProperFile;
import utils.GetResult_zZ;
import utils.MybatisFirstStepUtil;

public class Api1_Test {

	public static Object result_final;
	public static Object result_final2;
	public static Object jarr;

	@BeforeTest(groups = "requestTrue", description = "测试准备工作，获取httpClient对象")
	public void beforeTest() {
		TestConfig.address = BundleTakeProperFile.getUrl(InterfaceName.BICYCLINGAPI);
		TestConfig.defaultHttpClient = new DefaultHttpClient();
	}

	@Test(groups = "requestTrue", description = "骑行导航API测试")
	public void requestTrue() throws IOException {
		SqlSession session = MybatisFirstStepUtil.getSqlsession();
		try {
			int sqlcount = session.selectOne("model.select_count");
			for (int i = 1; i <= sqlcount; i++) {
				// 操作CRUD，第一个参数：指定statement，规则：命名空间+“.”+statementId
				// 第二个参数：指定传入sql的参数：这里是用户id
				bicyclingbean bcycb = session.selectOne("select_ibicycling", i);
				// 接口地址
				String URL = TestConfig.address + bcycb.getURI();
				// 获取测试用例检查点
				String veriftypoints = bcycb.getVeriftyPoints();
				// 需要解析的数据类型有三种String（包含或等于）；JSONObject（需要遍历找出包含或者等于）；JSONArray（需要遍历找出包含或等于）
				String DT = bcycb.getData_type();
//				System.out.println(DT);
				// OKHTTP 返回响应数据结果
				String Result = GetResult_zZ.httpGetResult(URL, bcycb);
//				System.out.println(Result);
//				String Result = "{\"status\":\"1\",\"route\":{\"paths\":[{\"steps\":[{\"nima\":[{\"niba\":[{\"nilaoye\":\"111\"}]}]}]}]}}";
				/**
				 * @author Administrator
				 * @note 执行区间
				 */
				CoreRun.executer(DT, veriftypoints, Result, session,i,bcycb);
				// 更新测试用例状态
//				bicyclingbean user = session.selectOne("model.update1", 2);
			}

		} finally {
			session.close();
		}
	}
}

//	public void extracted(String DT, String veriftypoints, String Result) {
//		if (DT.equals("String")) {// 如果是字符串类型的返回数据，直接比较文本。
//			int br = Result.indexOf(veriftypoints);
//			Assert.assertTrue("实际结果:" + Result + '\r' + "不包含或等于预期结果:" + veriftypoints, br >= 0);
//		} else if (DT.equals("JSONObject")) {// 如果是JSONObject类型的返回数据，需要剥洋葱。
//			// 查找数据格式{"destination":"X"}
//			String strat = "{\""; // 你指定的前缀字符串
//			String end = "\":\""; // 你指定的后缀字符串
//			int startIndex = veriftypoints.indexOf(strat) + strat.length();
//			int endIndex = veriftypoints.indexOf(end); // 上两句获取的是要取出字符串的前后坐标
//			String vp_expected_key = veriftypoints.substring(startIndex, endIndex); // 你要的结果
//
//			JSONObject veriftypointsJson = JSONUtil.parseObj(veriftypoints);
//			// 预期结果为表中定义{"destination":"X"}的X
//			String veriftypoints_expected_value = (String) veriftypointsJson.get(vp_expected_key);
////			System.out.println(veriftypoints_expected_value);
//			// 实际结果result_json
//			JSONObject result_json = JSONUtil.parseObj(Result);
//			// 找出result_json中对应key的值
//			getAllKey(result_json, vp_expected_key);
//			// 断言实际结果包含或等于预期结果
//			int br = result_final.toString().indexOf(veriftypoints_expected_value);
//			Assert.assertTrue("实际结果:" + vp_expected_key + '=' + result_final + '\r' + "不包含或等于预期结果:"
//					+ veriftypoints_expected_value, br >= 0);
//		} else if (DT.equals("JSONArray")) {//// 如果是JSONArray类型的返回数据，需要剥洋葱。
//			// 查找数据格式"paths":"["0":{}]"
//			String strat1 = "{\""; // 你指定的前缀字符串
//			String end1 = "\":[{\""; // 你指定的后缀字符串
//			int startIndex1 = veriftypoints.indexOf(strat1) + strat1.length();
//			int endIndex1 = veriftypoints.indexOf(end1); // 上两句获取的是要取出字符串的前后坐标
//			String vp_expected_key = veriftypoints.substring(startIndex1, endIndex1); // 你要的结果
////			System.out.println(vp_expected_key);//
////
//			String strat2 = "[{"; // 你指定的前缀字符串
//			String end2 = "]}}"; // 你指定的后缀字符串
//			int startIndex2 = veriftypoints.indexOf(strat2) + strat2.length();
//			int endIndex2 = veriftypoints.indexOf(end2); // 上两句获取的是要取出字符串的前后坐标
//			// 预期结果需要检查的值
//			String vp_expected_value = veriftypoints.substring(startIndex2, endIndex2); // 你要的结果
//
//			// 实际结果result_json
//			JSONObject result_json2 = JSONUtil.parseObj(Result);
//			getAllKey(result_json2, vp_expected_key);
//			if (result_final != null && result_final2 == null) {
//				int br = result_final.toString().indexOf(vp_expected_value);
//				Assert.assertTrue(
//						"实际结果:" + vp_expected_key + '=' + result_final + '\r' + "不包含或等于预期结果:" + vp_expected_value,
//						br >= 0);
//			} else if (result_final2 != null) {
//				int br = result_final2.toString().indexOf(vp_expected_value);
//				Assert.assertTrue(
//						"实际结果:" + vp_expected_key + '=' + result_final2 + '\r' + "不包含或等于预期结果:" + vp_expected_value,
//						br >= 0);
//			}
//		}
//	}
// 
//	// 递归找到所有KEY
//	public static void getAllKey(JSONObject jsonObject, String vp_key) {
//		Iterator<String> keys = jsonObject.keySet().iterator();// jsonObject.keys();
//		while (keys.hasNext()) {
//			String key = keys.next();
//			if (key.equals(vp_key)) {
//				result_final = jsonObject.get(vp_key);
////				System.out.println("asdasdasdasfdsfvdsdsfdsf" + result_final);
//			} else if (jsonObject.get(key) instanceof JSONObject) {// JSON对象
//				JSONObject innerObject = (JSONObject) jsonObject.get(key);
//				getAllKey(innerObject, vp_key);
//			} else if (jsonObject.get(key) instanceof JSONArray) {
//				jarr = jsonObject.get(key);
//				JSONArray array = JSONUtil.parseArray(jarr);
//				// 遍历当前key数组
//				for (int i = 0; i < array.size(); i++) {
//					JSONObject job = array.getJSONObject(i);
//					Object object = job.get(vp_key);
//					// 把当前值返回Ruselt_final2
//					result_final2 = object;
//					if (result_final2 == null) {
//						getAllKey(job, vp_key);
//					}
//				}
//			}
//		}
//	}
