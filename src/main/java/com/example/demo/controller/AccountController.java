package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	//	ユーザー情報変更画面　表示
	@GetMapping("/user/{userId}/edit")
	public String edit(
			@PathVariable("userId") Integer userId,
			Model model) {

		User user = userRepository.findById(userId).get();

		model.addAttribute("user", user);

		return "userEdit";

	}

	//	ログイン　実行
	@PostMapping("/login")
	public String login(
			@RequestParam(name = "loginId", defaultValue = "") String loginId,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {

		String result = "login";

		List<String> errList = new ArrayList<>();
		List<User> userList = userRepository.findByLoginIdAndPassword(loginId, password);

		if (userList.size() == 1) {
			User user = userList.get(0);
			account.setName(user.getName());
			account.setUserId(user.getId());

			result = "redirect:/taskList";

		} else {
			errList.add("ユーザーIDとパスワードが一致しません");
			model.addAttribute("errs", errList);

		}

		return result;

	}

	//	ユーザー新規登録　実行
	@PostMapping("/userAdd")
	public String add(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "loginId", defaultValue = "") String loginId,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {

		String result = "userAdd";

		List<String> errList = new ArrayList<>();
		List<User> users = userRepository.findByLoginId(loginId);

		if (name.equals("")) {
			//			"".equals(name)
			//			name.isEmpty()
			errList.add("【ユーザーネーム】　は入力必須項目です");

		}

		if (loginId.equals("")) {
			errList.add("【ユーザーID】　は入力必須項目です");

		} else if (users.size() != 0) {
			errList.add("ユーザーID：【" + loginId + "】　は使用されています");

		}

		if (password.equals("")) {
			errList.add("【パスワード】　は入力必須項目です");

		}

		if (errList.size() == 0) {
			User adduser = new User(loginId, name, password);
			userRepository.save(adduser);

			result = "redirect:/login";

		} else {
			model.addAttribute("errs", errList);
			model.addAttribute("name", name);
			model.addAttribute("loginId", loginId);

		}

		return result;

	}

}
