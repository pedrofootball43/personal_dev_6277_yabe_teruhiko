package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {

	//	カテゴリー一覧画面　表示
	@GetMapping("/category")
	public String index() {
		return "category";
	}
	
	//	カテゴリー追加画面　表示
	@GetMapping("/category/add")
	public String create() {
		return "categoryAdd";
	}
	
	//	カテゴリー更新画面　表示
	@GetMapping("/category/edit")
	public String edit() {
		return "categoryEdit";
	}
	
}




