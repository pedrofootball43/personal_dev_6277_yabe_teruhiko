package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Account account;
	
	//	ログイン画面　表示
	@GetMapping("/login")
	public String index() {
		
		session.invalidate();
		
		return "login";
	}
	
	//	ユーザー新規登録画面　表示
	@GetMapping("/userAdd")
	public String userAdd() {
		return "userAdd";
	}
	
	//	ログイン　実行
	@PostMapping("/login")
	public String login(
			@RequestParam(name = "loginId", defaultValue = "") String loginId,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model
			) {
		
		String result = "";
		
		List<User> userList = userRepository.findByLoginIdAndPassword(loginId, password);
		
		if(userList.size() == 1) {
			User user = userList.get(0);
			account.setName(user.getName());
			result = "/taskList";
		}else {
			model.addAttribute("errMsg", "ユーザーIDとパスワードが一致しませんでした");
			result = "/login";
		}
		
		return result;
		
	}
	
}







