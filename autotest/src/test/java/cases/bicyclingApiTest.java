package cases;

import java.io.IOException;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import config.TestConfig;
import junit.framework.Assert;
import model.InterfaceName;
import model.bicyclingbean;
import utils.ConfigFile;
import utils.DatabaseUtil;
import utils.OkHttpUtils;

public class bicyclingApiTest {

	@BeforeTest(groups = "requestTrue", description = "测试准备工作，获取httpClient对象")
	public void beforeTest() {
		TestConfig.address = ConfigFile.getUrl(InterfaceName.BICYCLINGAPI);
		TestConfig.defaultHttpClient = new DefaultHttpClient();
	}

	@Test(groups = "requestTrue", description = "骑行导航API测试")
	public void requestTrue() throws IOException {
		SqlSession session = DatabaseUtil.getSqlsession();
		bicyclingbean bcycb = session.selectOne("bicyclingApidatacase", 1);
		System.out.println(TestConfig.address);
		String URL = TestConfig.address + bcycb.getURI();
		System.out.println(URL);
		// 获取测试用例检查点
		String veriftypoints = bcycb.getVeriftyPoints();
System.out.println(veriftypoints);
		// 请求截取请求参数，放入OKHTTP3请求参数中
		String param = bcycb.getRequestParameter();
		// 根据逗号截取字符串数组
		String[] str1 = param.split("&");
		OkHttpUtils oku = OkHttpUtils.builder();
		for (int i = 0; i < str1.length; i++) {
			// 根据":"截取字符串数组
			String[] str2 = str1[i].split("=");
			// str2[0]为KEY,str2[1]为值
			System.out.println(str2[0]);
			System.out.println(str2[1]);
			oku
			.addParam(str2[0], str2[1]);
		}

		System.out.println(bcycb.getRequestHeader());
		// 带head或者无head的GET请求
		String Result = oku.url(URL).get().sync();
		Result = Result.substring(1, 44);
		System.out.println(Result);
		Assert.assertEquals(veriftypoints, Result);
		
		/**
		 * 加判断带head或者无head的GET请求
		 */
		// else {
		// OkHttpUtils.builder().url(TestConfig.address)
		// .url(URL)
		// .addHeader()
		// .get()
		// .sync();
		// }

		// 2.请求地址TestConfig.loginUr
		// 解析JSON
		// 第二步验证返回结果
//        Assert.assertEquals(bcycb.getVeriftyPoints(),result);
	}
}
