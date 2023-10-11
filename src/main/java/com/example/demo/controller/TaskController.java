package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {
	
	//	ToDo一覧画面　表示
	@GetMapping("/taskList")
	public String index() {
		return "taskList";
	}
	
	//	リスト登録画面　表示
	@GetMapping("/taskList/add")
	public String create() {
		return "taskListAdd";
	}
	
	//	リスト更新画面　表示
	@GetMapping("taskList/edit")
//	@GetMapping("taskList/{id}/edit")
	public String edit() {
		return "taskListEdit";
	}
	
}
