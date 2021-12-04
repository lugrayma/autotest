package springbootservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.User;

/**
 * Created by CR7 on 2017-8-18 Json返回数据的Controller
 */
@RestController
@Api(value = "/", description = "这是我的ReturnJson方法")
public class ReturnJsoncontroller {

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	@ApiOperation(value = "通过这个方法可以获得返回JSON格式数据", httpMethod = "GET")
	public User getUser() {
		User user = new User();
		user.setId(1);
		user.setUsername("zhanghaoliang");
		user.setPassword("1231");
		return user;
	}
}
