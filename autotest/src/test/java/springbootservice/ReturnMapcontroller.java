package springbootservice;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.User;

/**
 * Created by CR7 on 2017-8-18 Map返回数据的Controller
 */
@RestController
@Api(value = "/", description = "这是我的ReturnMap方法")
public class ReturnMapcontroller {
	@RequestMapping(value = "/getUserMap", method = RequestMethod.GET)
	@ApiOperation(value = "通过这个方法可以获得返回Map格式数据", httpMethod = "GET")
	public Map<String, User> getUserMap() {
		User user1 = new User();
		user1.setId(1);
		user1.setUsername("zhanghaoliang");
		user1.setPassword("123");
		User user2 = new User();
		user2.setId(2);
		user2.setUsername("chensi");
		user2.setPassword("456");
		User user3 = new User();
		user3.setId(3);
		user3.setUsername("doudou");
		user3.setPassword("789");
		Map<String, User> map = new HashMap<>();
		map.put("user1", user1);
		map.put("user2", user2);
		map.put("user3", user3);
		return map;
	}
}
