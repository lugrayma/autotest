package utils;

import java.util.Locale;
import java.util.ResourceBundle;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CookiesForGet {
	private String  url;
	private ResourceBundle bundle;
//	private CookieStore store;

	@BeforeTest
	public void bundleMethod() {
		// TODO Auto-generated method stub
		bundle = ResourceBundle.getBundle("application", Locale.CHINA);
		url = bundle.getString("test.url");
	}

	@Test
	public void getCookies() {
		String url = this.url;
		System.out.println(url);
	}

}
