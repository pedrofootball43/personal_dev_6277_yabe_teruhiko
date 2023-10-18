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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Category;
import com.example.demo.entity.Task;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;

@Controller
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	Account account;

	//	カテゴリー一覧画面　表示
	@GetMapping("/category")
	public String index(
			Model model) {

		List<Category> categoryList = categoryRepository.findAllByOrderByIdAsc();
		model.addAttribute("categories", categoryList);

		@SuppressWarnings("unchecked")
		List<String> errList = (List<String>) model.getAttribute("errList");
		model.addAttribute("errs", errList);

		return "category";

	}

	//	カテゴリー追加画面　表示
	@GetMapping("/category/add")
	public String create() {
		return "categoryAdd";

	}

	//	カテゴリー更新画面　表示
	@GetMapping("/category/{id}/edit")
	public String edit(
			@PathVariable("id") Integer id,
			Model model) {

		Category category = categoryRepository.findById(id).get();
		model.addAttribute("category", category);

		@SuppressWarnings("unchecked")
		List<String> errs = (List<String>) model.getAttribute("errList");
		Integer errKind = (Integer) model.getAttribute("errKind");
		String categoryName = (String) model.getAttribute("categoryName");

		model.addAttribute("errs", errs);
		model.addAttribute("errKind", errKind);
		model.addAttribute("categoryName", categoryName);

		return "categoryEdit";

	}

	//	入力情報　保持　未実装
	//	カテゴリー追加　実行
	@PostMapping("/category/add")
	public String add(
			@RequestParam(name = "name", defaultValue = "") String name,
			Model model) {

		//		ページ遷移先の指定
		String result = "redirect:/category";

		//		エラーメッセージ表示用リスト
		List<String> errList = new ArrayList<>();

		//		-----カテゴリー名の重複チェック-----
		List<Category> checkCategory = categoryRepository.findByName(name);

		if (checkCategory.size() == 0) {
			Category category = new Category(name);
			categoryRepository.save(category);

		} else {
			errList.add("カテゴリー名：【" + name + "】　は既に使用されています");
			model.addAttribute("errs", errList);
			result = "categoryAdd";
		}

		return result;

	}

	//	月・日付　一桁の時内容反映されない
	//	カテゴリー更新　実行
	@PostMapping("/category/{id}/edit")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam(name = "name", defaultValue = "") String name,
			RedirectAttributes redirectAttributes) {

		String result = "redirect:/category";
		Integer errKind = 0;

		List<String> errList = new ArrayList<>();
		List<Category> checkCategories = categoryRepository.findByName(name);

		if (name.equals("")) {
			errList.add("【カテゴリー名】　は入力必須です");
			errKind = 1;

		} else if (checkCategories.size() != 0) {
			errList.add("カテゴリー名：【" + name + "】　は既に使われています");
			redirectAttributes.addFlashAttribute("categoryName", name);
			errKind = 2;

		}

		if (errList.size() != 0) {
			redirectAttributes.addFlashAttribute("errList", errList);
			redirectAttributes.addFlashAttribute("errKind", errKind);
			result = "redirect:/category/{id}/edit";

		} else {
			Category category = new Category(id, name);
			categoryRepository.save(category);

		}

		return result;

	}

	//	カテゴリー削除　実行
	@PostMapping("/category/{id}/delete")
	public String delete(
			@PathVariable("id") Integer id,
			RedirectAttributes redirectAttributes) {

		Integer userId = account.getUserId();
		Category category = categoryRepository.findById(id).get();

		List<String> errList = new ArrayList<>();
		List<Task> taskList = taskRepository.findByCategoryIdAndUserId(id, userId);

		if (taskList.size() != 0) {
			errList.add("カテゴリ　：" + category.getName() + "　に属したToDoがあるため削除できません");
			errList.add("対象ToDo：" + (int) taskList.size() + "コ");
			redirectAttributes.addFlashAttribute("errList", errList);

		} else {
			categoryRepository.deleteById(id);

		}

		return "redirect:/category";

	}

}
