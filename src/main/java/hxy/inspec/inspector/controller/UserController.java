package hxy.inspec.inspector.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hxy.inspec.inspector.po.User;
import hxy.inspec.inspector.services.UserService;
import hxy.inspec.inspector.util.CodeMd5;

@Controller
@RequestMapping("/")
public class UserController {

	private final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/lo", method = RequestMethod.GET)
	public String login(ModelMap model) {
		model.addAttribute("greeting", "Hello World Again, from Spring 4 MVC");
		return "index";
	}

	@RequestMapping(value = "/loginVerify", method = RequestMethod.POST)
	public void loginVerify(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws IOException, NoSuchAlgorithmException {
		int resultCode = 0;
		String tel = null;
		String password = null;

		try {
			tel = request.getParameter("tel").trim();// 这个应该是电话号码
			password = request.getParameter("passwd").trim();
		} catch (NullPointerException e) {
			logger.warn("传入的是一个null");
		}

		logger.info("login Post tel is:" + tel + "Post password is:" + password);

		if (tel != null && password != null && !"".equals(tel) && !"".equals(password)) {
			UserService userService = new UserService();
			User user = userService.login(tel);
			if (user != null) {
				logger.info("用户存在" + user.getUserName());
				// 检查密码
				CodeMd5 codeMd5 = new CodeMd5();
				String newpasswd = codeMd5.codeMd5(password);
				
				if (newpasswd.equals(user.getUserPasswd())) {
					// 匹配成功
					resultCode = 200;
					// 把用户对象存储到session
					request.getSession().setAttribute("user", user);
				} else {
					// 提示密码不正确
					resultCode = 601;
				}
			} else {
				// 提示用户未注册
				resultCode = 404;
			}
		} else
			resultCode = 701;
		org.json.JSONObject user_data = new org.json.JSONObject();
		user_data.put("resultCode", resultCode);
		user_data.put("key2", "today4");
		user_data.put("key3", "today2");
		String jsonStr2 = user_data.toString();
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().append(jsonStr2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/register-user", method = RequestMethod.POST)
	public void userRegister(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws IOException, NoSuchAlgorithmException {
		int resultCode = 0;
		try {
			// 返回页面防止出现中文乱码
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		boolean flag = false;
		String username = null;
		String password = null;
		String tel = null;
		String email = null;
		try {
			username = request.getParameter("username").trim();// 这个应该是电话号码
			password = request.getParameter("passwd").trim();
			tel = request.getParameter("tel").trim();
			email = request.getParameter("email").trim();
			logger.info("register Post username is:" + username + tel +email+ " register Post password is:" + password);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("存在null");
		}
		if (flag) {
			
			CodeMd5 codeMd5 = new CodeMd5();
			String newpasswd = codeMd5.codeMd5(password);
			User user = new User();
			user.setUserName(username);
			user.setUserPasswd(newpasswd);
			user.setUserTel(tel);
			user.setEmail(email);
			user.setIntegral("0");
			user.setOrders("0");
			user.setRmb("0");
			user.setStatus("0");

			if (username != null && password != null && !"".equals(username) && !"".equals(password)) {
				UserService userService = new UserService();
				// 检查用户是否存在
				User user1 = userService.login(tel);
				if (user1 != null) {
					logger.info("用户存在" + user1.getUserName());
					// 检查密码
					if (password.equals(user1.getUserPasswd())) {
						// 匹配成功
						logger.info("密码正确");
						request.getSession().setAttribute("user", user);
						resultCode = 200;
					} else {
						// 提示密码不正确
						logger.info("密码不正确");
						resultCode = 101;// 用户已经存在，但是密码不正确
					}

				} else {
					logger.info("用户未注册");
					// 提示用户未注册
					if (userService.insert(user)) {
						logger.info("用户注册成功");
						request.getSession().setAttribute("user", user);
						resultCode = 200;
					} else
						;
				}
			}
		} else {
			resultCode = 404;
		}
		logger.info("返回注册信息");
		org.json.JSONObject user_data = new org.json.JSONObject();
		user_data.put("resultCode", resultCode);
		user_data.put("key2", "today4");
		user_data.put("key3", "today2");
		String jsonStr2 = user_data.toString();
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().append(jsonStr2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/user-login-out", method = RequestMethod.GET)
	public String userLoginOut(ModelMap model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			logger.info(user.getUserName() + "将要退出登录");
			// false代表：不创建session对象，只是从request中获取。
			HttpSession session = request.getSession(false);
			if (session == null) {

			} else
				session.removeAttribute("user");
//	https://blog.csdn.net/u010143291/article/details/51597507 
		}
		return "login";
	}

}
