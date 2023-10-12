package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Task;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;

@Controller
public class TaskController {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	Account account;

	//	ToDo一覧画面　表示
	@GetMapping("/taskList")
	public String index(
			@RequestParam(name = "categoryId", defaultValue = "0") Integer categoryId,
			Model model) {

		List<Task> taskList = null;
		Integer userId = account.getUserId();

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		if (categoryId != 0) {
			taskList = taskRepository.findByCategoryIdAndUserId(categoryId, userId);
		} else {
			model.addAttribute("categoryId", "");
			taskList = taskRepository.findByUserId(userId);
		}

		//		taskList = taskRepository.findAll();

		model.addAttribute("tasks", taskList);

		return "taskList";
	}

	//	リスト登録画面　表示
	@GetMapping("/taskList/add")
	public String create(Model model) {

		List<Category> categoryList = categoryRepository.findAll();

		model.addAttribute("categories", categoryList);

		return "taskListAdd";
	}

	//	リスト更新画面　表示
	@GetMapping("/taskList/{id}/edit")
	public String edit(
			@PathVariable("id") Integer id,
			Model model) {

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		Task t = taskRepository.findById(id).get();
		model.addAttribute("t", t);

		String deadline = t.getDeadline().replaceAll("/", "-");
		model.addAttribute("deadline", deadline);

		return "taskListEdit";
	}

	//	リスト登録　実行
	@PostMapping("/taskList/add")
	public String add(
			@RequestParam(name = "task", defaultValue = "") String task,
			@RequestParam(name = "detail", defaultValue = "") String detail,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name = "deadline", defaultValue = "") String deadline) {

		Integer userId = account.getUserId();

		Task t = new Task(userId, categoryId, task, detail, deadline);

		taskRepository.save(t);

		return "redirect:/taskList";

	}

	//	リスト更新　実行
	@PostMapping("/taskList/{id}/edit")
	public String edit(
			@PathVariable("id") Integer id,
			@RequestParam(name = "task", defaultValue = "") String task,
			@RequestParam(name = "taskDetail", defaultValue = "") String taskDetail,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name = "deadline", defaultValue = "") String deadline) {

		Integer userId = account.getUserId();

		Task t = new Task(id, userId, categoryId, task, taskDetail, deadline);

		taskRepository.save(t);

		return "redirect:/taskList";

	}

	//	リスト削除　実行
	@PostMapping("/taskList/{id}/delete")
	public String delete(
			@PathVariable("id") Integer id) {

		taskRepository.deleteById(id);

		return "redirect:/taskList";

	}

}
