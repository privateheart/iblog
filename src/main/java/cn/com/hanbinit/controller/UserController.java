package cn.com.hanbinit.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.hanbinit.domain.User;
import io.swagger.annotations.ApiOperation;

/**
 * 用户操作类(demo)
 * @author icer
 *
 */
@RestController
@RequestMapping(value="/users") // 这里配置的url对本类所有的方法都有效
public class UserController {
	
	// 线程安全的map.
	static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>()); 
	
	/**
	 * 拦截"/users/"的GET请求<br>
	 * 获取所有的用户
	 * @return
	 */
	// @RequestMapping("/")
	@ApiOperation(value="获取用户列表", notes="不需要传入参数")
	@RequestMapping(value="/", method=RequestMethod.GET)
	public List<User> getUserList(){
		List<User> userList = new ArrayList<User>(users.values());
		return userList;
	}

	/**
	 * 拦截"/users/"的POST请求<br>
	 * 创建新用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String postUser(@ModelAttribute User user){
		users.put(user.getId(), user);
		System.err.println("===========");
		System.out.println(user);
		System.err.println("++++++++++++++");
		return "success";
	}
	
	/**
	 * 拦截"/users/id"的GET请求<br>
	 * 根据id获取用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public User getUser(@PathVariable Long id){
		return users.get(id);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String putUser(@PathVariable Long id, @ModelAttribute User user){
		User u = users.get(id);
		u.setName(user.getName());
		u.setAge(user.getAge());
		users.put(id, u);
		return "success";
	}
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public String delUser(@PathVariable Long id){
		users.remove(id);
		return "success";
	}
	
}
