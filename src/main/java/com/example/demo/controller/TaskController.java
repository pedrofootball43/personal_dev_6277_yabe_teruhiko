package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Task;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;

@Controller
public class TaskController {
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	//	ToDo一覧画面　表示
	@GetMapping("/taskList")
	public String index(
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			Model model
			) {
		
		List<Task> taskList = null;
		
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);
		
		if(categoryId != null) {
			taskList = taskRepository.findByCategoryId(categoryId);
		}else {
			model.addAttribute("categoryId", "");
			taskList = taskRepository.findAll();
		}
		
		model.addAttribute("tasks", taskList);
		
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
