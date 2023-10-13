package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			//			taskList = taskRepository.findByCategoryIdAndUserIdOrderByIdAsc(categoryId, userId);
			taskList = taskRepository.findByCategoryIdAndUserId(categoryId, userId);
		} else {
			model.addAttribute("categoryId", "");
			taskList = taskRepository.findByUserId(userId);

			//			taskList = taskRepository.findByUserIdOrderByIdAsc(userId);
			//			taskList = taskRepository.findAllByOrderByIdAsc();
		}

		//		taskList = taskRepository.findAll();

		model.addAttribute("tasks", taskList);

		return "taskList";
	}

	//	リスト登録画面　表示
	@GetMapping("/taskList/add")
	public String create(Model model) {

		List<String> errList = new ArrayList<>();

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		String err1 = (String) model.getAttribute("err1");
		String err2 = (String) model.getAttribute("err2");

		errList.add(err1);
		errList.add(err2);

		model.addAttribute("errs", errList);

		return "taskListAdd";
	}

	//	リスト更新画面　表示
	@GetMapping("/taskList/{id}/edit")
	public String edit(
			@PathVariable("id") Integer id,
			Model model) {

		List<String> errList = new ArrayList<>();
		String err1 = (String) model.getAttribute("err1");
		String err2 = (String) model.getAttribute("err2");
		errList.add(err1);
		errList.add(err2);
		model.addAttribute("errs", errList);

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		Task t = taskRepository.findById(id).get();
		model.addAttribute("t", t);

		model.addAttribute("selectedValue", t.getCategoryId());

		String deadline = t.getDeadline().replaceAll("/", "-");
		model.addAttribute("deadline", deadline);

		String taskDetail = (String) model.getAttribute("taskDetail");
		Integer categoryId = (Integer) model.getAttribute("categoryId");
		//		LocalDate deadlineFlash = (LocalDate) model.getAttribute("deadline");
		model.addAttribute("taskDetail", taskDetail);
		model.addAttribute("categoryId", categoryId);
		//		model.addAttribute("deadline", deadlineFlash);

		model.addAttribute("err2", err2);

		return "taskListEdit";
	}

	//	リスト登録　実行
	@PostMapping("/taskList/add")
	public String add(
			@RequestParam(name = "task", defaultValue = "") String task,
			@RequestParam(name = "detail", defaultValue = "") String detail,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			//			@RequestParam(name = "deadline", defaultValue = "") String deadline
			@RequestParam(name = "deadline", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline,
			RedirectAttributes redirectAttributes) {

		String result = "redirect:/taskList/add";
		Integer userId = account.getUserId();

		String err1 = "以下の項目は入力必須です";
		String err2 = "";

		//		List<String> errList = new ArrayList<>();
		//		errList.add("以下の項目は入力必須です");

		if (task.equals("")) {
			//			errList.add("【ToDo】");
			err2 = "【ToDo】";
		}

		if (err2.equals("")) {
			Task t = new Task(userId, categoryId, task, detail, deadline);
			taskRepository.save(t);
			result = "redirect:/taskList";
		}

		//		redirectAttributes.addFlashAttribute("errList", errList);
		redirectAttributes.addFlashAttribute("err1", err1);
		redirectAttributes.addFlashAttribute("err2", err2);

		return result;

	}

	//	リスト更新　実行
	@PostMapping("/taskList/{id}/edit")
	public String edit(
			@PathVariable("id") Integer id,
			@RequestParam(name = "task", defaultValue = "") String task,
			@RequestParam(name = "taskDetail", defaultValue = "") String taskDetail,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			//			@RequestParam(name = "deadline", defaultValue = "") String deadline
			@RequestParam(name = "deadline", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline,
			RedirectAttributes redirectAttributes) {

		String result = "redirect:/taskList/{id}/edit";
		Integer userId = account.getUserId();

		String err1 = "以下の項目は入力必須です";
		String err2 = "";

		//		List<String> errList = new ArrayList<>();
		//		errList.add("以下の項目は入力必須です");

		if (task.equals("")) {
			//			errList.add("【ToDo】");
			err2 = "【ToDo】";
		}

		if (err2.equals("")) {
			Task t = new Task(id, userId, categoryId, task, taskDetail, deadline);
			taskRepository.save(t);
			result = "redirect:/taskList";
		}

		//		redirectAttributes.addFlashAttribute("errList", errList);
		redirectAttributes.addFlashAttribute("err1", err1);
		redirectAttributes.addFlashAttribute("err2", err2);

		redirectAttributes.addFlashAttribute("taskDetail", taskDetail);
		redirectAttributes.addFlashAttribute("categoryId", categoryId);
		//		redirectAttributes.addFlashAttribute("deadline", deadline);

		return result;

	}

	//	リスト削除　実行
	@PostMapping("/taskList/{id}/delete")
	public String delete(
			@PathVariable("id") Integer id) {

		taskRepository.deleteById(id);

		return "redirect:/taskList";

	}

}
