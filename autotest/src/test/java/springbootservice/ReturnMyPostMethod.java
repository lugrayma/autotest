package springbootservice;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@Api(value = "/", description = "这是我的post方法")
public class ReturnMyPostMethod {
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletResponse response,
			@RequestParam(value = "userName",required = true) String userName,
			@RequestParam(value = "password",required = true) String password) {
		if(userName.equals("lugreyma")&&password.equals("123456")) {
			return "恭喜登录成功！";
		}
		return "用户名或密码错误！";
	}
}
