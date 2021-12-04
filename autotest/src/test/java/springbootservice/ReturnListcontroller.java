package springbootservice;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.User;

/**
 * Created by CR7 on 2017-8-18 list返回数据的Controller
 */
@RestController
@Api(value = "/", description = "这是我的ReturnList方法")
public class ReturnListcontroller {
	@RequestMapping(value = "/getUserList", method = RequestMethod.GET)
	@ApiOperation(value = "通过这个方法可以获得返回List格式数据", httpMethod = "GET")
	public List<User> getUserList() {
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
		List<User> list = new ArrayList<>();
		list.add(user1);
		list.add(user2);
		list.add(user3);
		return list;
	}
}
