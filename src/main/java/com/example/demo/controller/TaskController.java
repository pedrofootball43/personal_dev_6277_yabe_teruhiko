package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
			@RequestParam(name = "sort", defaultValue = "") String[] sort,
			Model model) {

		Integer userId = account.getUserId();

		//		-----カテゴリプルダウン　情報取得・出力-----
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		//		-----検索・並び替え-----
		//		出力用タスクリスト(空)　作成
		List<VTask> vtaskList = null;

		//		検索：有
		if (categoryId != 0) {
			if (sort.length == 0) {
				vtaskList = vtaskRepository.findByCategoryIdAndUserIdOrderById(categoryId, userId);
			} else {
				if ("deadline".equals(sort[0])) {
					//		並び替え：期日
					vtaskList = vtaskRepository.findByCategoryIdAndUserIdOrderByDeadline(categoryId, userId);
				} else {
					//		並び替え：無
					vtaskList = vtaskRepository.findByCategoryIdAndUserIdOrderById(categoryId, userId);
				}
			}
			//		検索：無
		} else {
			if (sort.length == 2) {
				//		並び替え：期日・カテゴリ
				vtaskList = vtaskRepository.findByUserIdOrderByCategoryIdAscDeadlineAsc(userId);
			} else if (sort.length == 1) {
				if ("deadline".equals(sort[0])) {
					//		並び替え：期日
					vtaskList = vtaskRepository.findByUserIdOrderByDeadline(userId);
				} else {
					//		並び替え：カテゴリ
					vtaskList = vtaskRepository.findByUserIdOrderByCategoryId(userId);
				}
			} else {
				//		指定なし
				vtaskList = vtaskRepository.findByUserIdOrderById(userId);
			}
		}
		//		タスクリスト　出力
		model.addAttribute("tasks", vtaskList);

		return "taskList";
	}

	//	リスト登録画面　表示
	@GetMapping("/taskList/add")
	public String create(Model model) {

		//		-----カテゴリプルダウン　情報取得・出力-----
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		//		-----エラー発生　情報取得・出力-----
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

		//		-----カテゴリプルダウン　情報取得・出力-----
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categories", categoryList);

		Task t = taskRepository.findById(id).get();
		model.addAttribute("t", t);

		//		-----残り日数　算出・出力-----
		final String FORMAT = "yyyy-MM-dd";
		LocalDate now = LocalDate.now();
		LocalDate deadline = LocalDate.parse(t.getDeadline(), DateTimeFormatter.ofPattern(FORMAT));
		long dayNum = ChronoUnit.DAYS.between(now, deadline);

		if (dayNum < 0) {
			dayNum = 0;
		}

		model.addAttribute("dayNum", dayNum);

		//		-----エラー発生　情報取得・出力-----
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

	////	完了リスト　表示
	//	@GetMapping("/taskList/finish")

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

		//		-----入力チェック-----
		List<String> errList = new ArrayList<>();

		//		タスク有無
		if (task.equals("")) {
			errList.add("【ToDo】　は入力必須です");

		}

		//		期日過去
		if (deadline != null && deadline.isBefore(LocalDate.now())) {
			LocalDate lToday = LocalDate.now();
			String sToday = lToday.getYear() + "/" + lToday.getMonthValue() + "/" + lToday.getDayOfMonth();
			errList.add("【期日】　は本日（" + sToday + ")より後を選択してください");

		}

		//		エラー有無
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
			@RequestParam(name = "deadline", defaultValue = "9999-12-31") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline,
			RedirectAttributes redirectAttributes) {

		String result = "redirect:/taskList/{id}/edit";
		Integer errKind = 0;
		Integer userId = account.getUserId();

		//		-----入力チェック-----
		List<String> errList = new ArrayList<>();

		//		タスク有無
		if (task.equals("")) {
			errList.add("【ToDo】　は入力必須です");
			errKind += 1;
		}

		//		期日過去
		LocalDate chechDate = LocalDate.of(9999, 12, 31);
		if (deadline.isEqual(chechDate)) {
			errList.add("【期日】　は入力必須です");
			//			errKind += 2;
		} else if (deadline.isBefore(LocalDate.now())) {
			LocalDate lToday = LocalDate.now();
			String sToday = lToday.getYear() + "/" + lToday.getMonthValue() + "/" + lToday.getDayOfMonth();
			errList.add("【期日】　は本日（" + sToday + ")より後を選択してください");
			errKind += 2;
		}

		//		エラー有無
		if (errList.size() == 0) {
			Task t = new Task(id, userId, categoryId, task, taskDetail, deadline);
			taskRepository.save(t);

			result = "redirect:/taskList";

		} else {
			String sDeadline = deadline.getYear() + "-" + deadline.getMonthValue() + "-" + deadline.getDayOfMonth();
			redirectAttributes.addFlashAttribute("deadlineFlash", sDeadline);
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
