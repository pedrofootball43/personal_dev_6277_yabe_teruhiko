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

		List<String> errList = new ArrayList<>();

		//		List<Category> categoryList = categoryRepository.findAll();
		List<Category> categoryList = categoryRepository.findAllByOrderByIdAsc();
		model.addAttribute("categories", categoryList);

		//		テストコード　動く
		//		String abc = (String) model.getAttribute("abc");
		//		model.addAttribute("abc", abc);

		String err1 = (String) model.getAttribute("err1");
		String err2 = (String) model.getAttribute("err2");
		errList.add(err1);
		errList.add(err2);

		//		警告が出てしまう
		//		List<String> errList =  (List<String>)model.getAttribute("errList");

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

		return "categoryEdit";
	}

	//	カテゴリー追加　実行
	@PostMapping("/category/add")
	public String add(
			@RequestParam(name = "name", defaultValue = "") String name) {

		Category category = new Category(name);

		categoryRepository.save(category);

		return "redirect:/category";

	}

	//	カテゴリー更新　実行
	@PostMapping("/category/{id}/edit")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam(name = "name", defaultValue = "") String name) {

		Category category = new Category(id, name);

		categoryRepository.save(category);

		return "redirect:/category";

	}

	//	カテゴリー削除　実行
	@PostMapping("/category/{id}/delete")
	public String delete(
			@PathVariable("id") Integer id,
			RedirectAttributes redirectAttributes) {

		String err1 = "";
		String err2 = "";

		Integer userId = account.getUserId();

		//		List<String> errList = new ArrayList<>();
		List<Task> taskList = taskRepository.findByCategoryIdAndUserId(id, userId);

		Category category = categoryRepository.findById(id).get();

		if (taskList.size() != 0) {

			//			出力先で警告発生
			//			errList.add("カテゴリ　：" + category.getName() + "　に属したToDoがあるため削除できません");
			//			errList.add("対象ToDo：" + (int) taskList.size() + "コ");
			//			redirectAttributes.addFlashAttribute("errList", errList);

			err1 = ("カテゴリ：【" + category.getName() + "】　に属したToDoがあるため削除できません");
			err2 = ("対象ToDo：" + (int) taskList.size() + "コ");

		} else {
			categoryRepository.deleteById(id);
		}

		redirectAttributes.addFlashAttribute("err1", err1);
		redirectAttributes.addFlashAttribute("err2", err2);

		//		テストコード　動く
		//		redirectAttributes.addFlashAttribute("abc", "ドラえもん");

		return "redirect:/category";

	}

}
