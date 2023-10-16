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
import com.example.demo.entity.VTask;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.VTaskRepository;

@Controller
public class TaskController {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	VTaskRepository vtaskRepository;

	@Autowired
	Account account;

	//	ToDo一覧画面　表示
	@GetMapping("/taskList")
	public String index(
			@RequestParam(name = "categoryId", defaultValue = "0") Integer categoryId,
			Model model) {

		Integer userId = account.getUserId();

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		//		view
		List<VTask> vtaskList = null;

		if (categoryId != 0) {
			//			view
			vtaskList = vtaskRepository.findByCategoryIdAndUserIdOrderById(categoryId, userId);

		} else {
			model.addAttribute("categoryId", "");
			//			view
			vtaskList = vtaskRepository.findByUserIdOrderById(userId);

		}

		model.addAttribute("tasks", vtaskList);

		return "taskList";
	}

	//	リスト登録画面　表示
	@GetMapping("/taskList/add")
	public String create(Model model) {

		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		@SuppressWarnings("unchecked")
		List<String> errList = (List<String>) model.getAttribute("errList");
		model.addAttribute("errs", errList);

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

		@SuppressWarnings("unchecked")
		List<String> errList = (List<String>) model.getAttribute("errList");

		Integer errKind = (Integer) model.getAttribute("errKind");
		String taskDetail = (String) model.getAttribute("taskDetail");
		Integer categoryId = (Integer) model.getAttribute("categoryId");
		String deadlineFlash = (String) model.getAttribute("deadlineFlash");
		String currentTask = (String) model.getAttribute("currentTask");

		model.addAttribute("errs", errList);
		model.addAttribute("errKind", errKind);
		model.addAttribute("taskDetail", taskDetail);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("deadlineFlash", deadlineFlash);
		model.addAttribute("currentTask", currentTask);

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

		List<String> errList = new ArrayList<>();

		if (task.equals("")) {
			errList.add("【ToDo】　は入力必須です");

		}

		if (deadline != null && deadline.isBefore(LocalDate.now())) {
			LocalDate lToday = LocalDate.now();
			String sToday = lToday.getYear() + "/" + lToday.getMonthValue() + "/" + lToday.getDayOfMonth();
			errList.add("【期日】　は本日（" + sToday + ")より後を選択してください");

		}

		if (errList.size() == 0) {
			Task t = new Task(userId, categoryId, task, detail, deadline);
			taskRepository.save(t);

			result = "redirect:/taskList";

		} else {
			redirectAttributes.addFlashAttribute("errList", errList);

		}

		return result;

	}

	//	リスト更新　実行
	@PostMapping("/taskList/{id}/edit")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam(name = "task", defaultValue = "") String task,
			@RequestParam(name = "taskDetail", defaultValue = "") String taskDetail,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			//			@RequestParam(name = "deadline", defaultValue = "") String deadline
			@RequestParam(name = "deadline", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline,
			RedirectAttributes redirectAttributes) {

		String result = "redirect:/taskList/{id}/edit";
		Integer errKind = 0;
		Integer userId = account.getUserId();

		List<String> errList = new ArrayList<>();

		if (task.equals("")) {
			errList.add("【ToDo】　は入力必須です");
			errKind = 1;
		}

		if (deadline != null && deadline.isBefore(LocalDate.now())) {
			LocalDate lToday = LocalDate.now();
			String sToday = lToday.getYear() + "/" + lToday.getMonthValue() + "/" + lToday.getDayOfMonth();
			errList.add("【期日】　は本日（" + sToday + ")より後を選択してください");
			errKind = 2;

		}

		if (errList.size() == 0) {
			Task t = new Task(id, userId, categoryId, task, taskDetail, deadline);
			taskRepository.save(t);

			result = "redirect:/taskList";

		} else {
			String sDeadline = deadline.getYear() + "/" + deadline.getMonthValue() + "/" + deadline.getDayOfMonth();
			redirectAttributes.addFlashAttribute("deadlineFlash", sDeadline.replaceAll("/", "-"));
			redirectAttributes.addFlashAttribute("taskDetail", taskDetail);
			redirectAttributes.addFlashAttribute("categoryId", categoryId);
			redirectAttributes.addFlashAttribute("currentTask", task);
			redirectAttributes.addFlashAttribute("errKind", errKind);
			redirectAttributes.addFlashAttribute("errList", errList);

		}

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
