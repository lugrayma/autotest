package api_suite;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.BeforeTestConfig;
import controller.CoreRun;
import model.InterfaceName;
import model.bicyclingbean;
import utils.BundleTakeProperFile;
import utils.GetResult_zZ;
import utils.MybatisFirstStepUtil;
import utils.PostResult_zZ;

public class Api1_Test {

	public static Object result_final;
	public static Object result_final2;
	public static Object jarr;

	@BeforeTest(groups = "requestTrue", description = "测试准备工作，获取httpClient对象")
	public void beforeTest() {
		// dev环境
		BeforeTestConfig.address = BundleTakeProperFile.getUrl(InterfaceName.BICYCLINGAPI);
		// Test的moco环境
//		BeforeTestConfig.address = BundleTakeProperFile.getUrl(InterfaceName.MOCOTEST);
		System.out.println(BeforeTestConfig.address);
	}

	@Test(groups = "requestTrue", description = "骑行导航API测试")
	public void requestTrue() throws IOException {
		SqlSession session = MybatisFirstStepUtil.getSqlsession();
		String Result = null;
		String URL = null;
		try {
			int sqlcount = session.selectOne("model.select_count");
			for (int i = 1; i <= sqlcount; i++) {
				// 操作CRUD，第一个参数：指定statement，规则：命名空间+“.”+statementId
				// 第二个参数：指定传入sql的参数：这里是用户id
				bicyclingbean bcycb = session.selectOne("select_ibicycling", i);
				// 接口地址
				//测试环境
//				String URL = BeforeTestConfig.address;
				//开发环境
				if(bcycb.getURI()==null) {
					URL = BeforeTestConfig.address;
				}else {
					URL = BeforeTestConfig.address + bcycb.getURI();
				}
				
				// 获取测试用例检查点
				String veriftypoints = bcycb.getVeriftyPoints();
				// 需要解析的数据类型有三种ResponeTxt：字符串全匹配；JSONObject:字符串中{X}这样的数据；JSONArray：字符串中[{X}这样的数据]
				String DT = bcycb.getData_type();
//				System.out.println(DT);
				String requestMethod = bcycb.getRequestMethod();
//				System.out.println(requestMethod);
				if (requestMethod.equals("get")) {
					// OKHTTP 返回get响应数据结果
					Result = GetResult_zZ.httpGetResult(URL, bcycb);
				} else if (requestMethod.equals("postJsonBdy")) {
					// OKHTTP
					// 返回post响应数据结果，如果post()传入true发送的时JSON的POST请求，如果传入false传入的时表单forms的post请求。
					Result = PostResult_zZ.httpPostResult(URL, bcycb,true);
				}else if (requestMethod.equals("postJsonFms")) {
					Result = PostResult_zZ.httpPostResult(URL, bcycb,false);
				}
//				String Result = "{\"status\":\"1\",\"route\":{\"paths\":[{\"steps\":[{\"nima\":[{\"niba\":[{\"nilaoye\":\"111\"}]}]}]}]}}";
				/**
				 * @author Administrator
				 * @note 执行区间
				 */
				CoreRun.executer(DT, veriftypoints, Result, session, i, bcycb);
				// 更新测试用例状态
//				bicyclingbean user = session.selectOne("model.update1", 2);
			}

		} finally {
			session.close();
		}
	}
}