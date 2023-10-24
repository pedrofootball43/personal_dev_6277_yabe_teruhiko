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
		List<Category> categoryList = categoryRepository.findByUserIdOrderByIdAsc(userId);
		model.addAttribute("categories", categoryList);

		//		-----検索・並び替え-----
		//		出力用タスクリスト(空)　作成
		List<VTask> vtaskList = null;

		//		検索：有
		if (categoryId != 0) {
			if (sort.length == 0) {
				vtaskList = vtaskRepository.findByCategoryIdAndUserIdAndSituationOrderById(categoryId, userId, "未");
			} else {
				if ("deadline".equals(sort[0])) {
					//		並び替え：期日
					vtaskList = vtaskRepository.findByCategoryIdAndUserIdAndSituationOrderByDeadline(categoryId, userId,
							"未");
				} else {
					//		並び替え：無
					vtaskList = vtaskRepository.findByCategoryIdAndUserIdAndSituationOrderById(categoryId, userId, "未");
				}
			}
			//		検索：無
		} else {
			if (sort.length == 2) {
				//		並び替え：期日・カテゴリ
				vtaskList = vtaskRepository.findByUserIdAndSituationOrderByCategoryIdAscDeadlineAsc(userId, "未");
			} else if (sort.length == 1) {
				if ("deadline".equals(sort[0])) {
					//		並び替え：期日
					vtaskList = vtaskRepository.findByUserIdAndSituationOrderByDeadline(userId, "未");
				} else {
					//		並び替え：カテゴリ
					vtaskList = vtaskRepository.findByUserIdAndSituationOrderByCategoryId(userId, "未");
				}
			} else {
				//		指定なし
				//				vtaskList = vtaskRepository.findByUserIdAndSituationOrderById(userId, "未");
				//		デフォルト　カテゴリ・期日順
				vtaskList = vtaskRepository.findByUserIdAndSituationOrderByCategoryIdAscDeadlineAsc(userId, "未");
			}
		}
		//		タスクリスト　出力
		model.addAttribute("tasks", vtaskList);

		List<String> alertList = new ArrayList<>();
		List<String> outList = new ArrayList<>();
		List<Task> taskList = taskRepository.findByUserIdAndSituation(userId, "未");
		Integer alertNum = 0;
		Integer outNum = 0;
		final String FORMAT = "yyyy-MM-dd";
		LocalDate now = LocalDate.now();

		for (Task t : taskList) {
			LocalDate deadline = LocalDate.parse(t.getDeadline(), DateTimeFormatter.ofPattern(FORMAT));
			long dayNum = ChronoUnit.DAYS.between(now, deadline);

			if (dayNum < 0) {
				String outDate = t.getDeadline().replace("-", "/");
				outList.add(t.getTask() + "(" + outDate + ")");
				outNum++;
			} else if (dayNum < 8) {
				String alertDate = t.getDeadline().replace("-", "/");
				alertList.add(t.getTask() + "(" + alertDate + ")");
				alertNum++;
			}
		}

		if (outNum != 0) {
			model.addAttribute("outMsg", "期日の過ぎているタスクが【" + outNum + "件】あります");
			model.addAttribute("outs", outList);
			//			model.addAttribute("outDate", outDate);
		}

		if (alertNum != 0) {
			model.addAttribute("alertMsg", "期日の近いタスクが【" + alertNum + "件】あります");
			model.addAttribute("alerts", alertList);
			//			model.addAttribute("alertDate", alertDate);
		}

		if (outNum == 0 && alertNum == 0) {
			model.addAttribute("okMsg", "期日を過ぎたタスク、期日の近いタスクはありません");
		}

		return "taskList";
	}

	//	リスト登録画面　表示
	@GetMapping("/taskList/add")
	public String create(Model model) {

		//		-----カテゴリプルダウン　情報取得・出力-----
		List<Category> categoryList = categoryRepository.findByUserIdOrderByIdAsc(account.getUserId());
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
		List<Category> categoryList = categoryRepository.findByUserIdOrderByIdAsc(account.getUserId());
		model.addAttribute("categories", categoryList);

		Task t = taskRepository.findById(id).get();
		model.addAttribute("t", t);

		//		-----残り日数　算出・出力-----
		final String FORMAT = "yyyy-MM-dd";
		LocalDate now = LocalDate.now();
		LocalDate deadline = LocalDate.parse(t.getDeadline(), DateTimeFormatter.ofPattern(FORMAT));
		long dayNum = ChronoUnit.DAYS.between(now, deadline);
		String numColor = "black";

		if (dayNum < 0 || "済".equals(t.getSituation())) {
			dayNum = 0;
		}

		if (dayNum < 8 && "未".equals(t.getSituation())) {
			numColor = "red";
		}

		model.addAttribute("numColor", numColor);
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

	//	完了リスト　表示
	@GetMapping("/taskList/finish")
	public String showfinish(Model model) {

		Integer userId = account.getUserId();

		//		出力用タスクリスト(空)　作成
		List<VTask> vtaskList = null;
		vtaskList = vtaskRepository.findByUserIdAndSituationOrderByDeadline(userId, "済");
		model.addAttribute("tasks", vtaskList);

		return "taskListFinish";
	}

	//	完了タスク詳細　表示
	@GetMapping("taskList/{id}/finish")
	public String finishDeatil(
			@PathVariable("id") Integer id,
			Model model) {

		VTask vt = vtaskRepository.findById(id).get();
		String categoryName = vt.getCategoryName();
		model.addAttribute("categoryName", categoryName);

		Task t = taskRepository.findById(id).get();
		model.addAttribute("t", t);

		String finishDate = t.getFinishDate();
		model.addAttribute("finishDate", finishDate.replaceAll("-", "/"));

		String deadline = t.getDeadline();
		model.addAttribute("deadline", deadline.replaceAll("-", "/"));

		return "taskFinishDetail";
	}

	//	リスト登録　実行
	@PostMapping("/taskList/add")
	public String add(
			@RequestParam(name = "task", defaultValue = "") String task,
			@RequestParam(name = "detail", defaultValue = "") String detail,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			//			@RequestParam(name = "deadline", defaultValue = "") String deadline
			@RequestParam(name = "deadline", defaultValue = "9999-12-31") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline,
			RedirectAttributes redirectAttributes) {

		final String SITUATION = "未";
		String result = "redirect:/taskList/add";
		Integer userId = account.getUserId();

		//		-----入力チェック-----
		List<String> errList = new ArrayList<>();

		//		タスク有無
		if (task.equals("")) {
			errList.add("【ToDo】　は入力必須です");

		}

		//		期日過去
		LocalDate chechDate = LocalDate.of(9999, 12, 31);
		if (deadline.isEqual(chechDate)) {
			errList.add("【期日】　は入力必須です");
			//			errKind += 2;
		} else if (deadline != null && deadline.isBefore(LocalDate.now())) {
			LocalDate lToday = LocalDate.now();
			String sToday = lToday.getYear() + "/" + lToday.getMonthValue() + "/" + lToday.getDayOfMonth();
			errList.add("【期日】　は本日（" + sToday + ")以降を選択してください");

		}

		//		エラー有無
		if (errList.size() == 0) {
			Task t = new Task(userId, categoryId, task, detail, deadline, SITUATION);
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

		final String SITUATION = "未";
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
			errKind += 4;
		} else if (deadline.isBefore(LocalDate.now())) {
			LocalDate lToday = LocalDate.now();
			String sToday = lToday.getYear() + "/" + lToday.getMonthValue() + "/" + lToday.getDayOfMonth();
			errList.add("【期日】　は本日（" + sToday + ")以降を選択してください");
			errKind += 2;
		}

		//		エラー有無
		if (errList.size() == 0) {
			Task t = new Task(id, userId, categoryId, task, taskDetail, deadline, SITUATION);
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

	//	リスト未完了　→　完了　実行
	@PostMapping("/taskList/{id}/finish")
	public String finish(
			@PathVariable("id") Integer id) {

		final String SITUATION = "済";
		Task t = taskRepository.findById(id).get();

		Task newT = new Task(t.getId(), t.getUserId(), t.getCategoryId(), t.getTask(), t.getTaskDetail(),
				t.getLDeadline(), SITUATION, LocalDate.now());

		taskRepository.save(newT);

		return "redirect:/taskList";

	}

	//	リスト完了　→　未完了　実行
	@PostMapping("/taskList/{id}/notFinish")
	public String notFinish(
			@PathVariable("id") Integer id) {

		final String SITUATION = "未";
		Task t = taskRepository.findById(id).get();

		Task newT = new Task(t.getId(), t.getUserId(), t.getCategoryId(), t.getTask(), t.getTaskDetail(),
				t.getLDeadline(), SITUATION);

		taskRepository.save(newT);

		return "redirect:/taskList/finish";

	}

	//	リスト削除　実行
	@PostMapping("/taskList/{id}/delete")
	public String delete(
			@PathVariable("id") Integer id) {

		//		リダイレクト先　指定
		String result = "redirect:/taskList";

		//		対象タスク　取得
		Task t = taskRepository.findById(id).get();

		//		対象タスク　状況確認　→　リダイレクト先変更
		if ("済".equals(t.getSituation())) {
			result = "redirect:/taskList/finish";
		}

		taskRepository.deleteById(id);

		return result;

	}

}
