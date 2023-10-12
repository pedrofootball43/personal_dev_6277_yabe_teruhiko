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
import com.example.demo.repository.CategoryRepository;

@Controller
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;

	
	//	カテゴリー一覧画面　表示
	@GetMapping("/category")
	public String index(Model model) {
	
		List<Category> categoryList = categoryRepository.findAll();
		
		model.addAttribute("categories", categoryList);
		
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
			Model model
			) {
		
		Category category =categoryRepository.findById(id).get();
		
		model.addAttribute("category", category);
		
		return "categoryEdit";
	}
	
	//	カテゴリー追加　実行
	@PostMapping("/category/add")
	public String add(
			@RequestParam(name = "name", defaultValue = "") String name
			) {
		
		Category category = new Category(name);
		
		categoryRepository.save(category);
		
		return "redirect:/category";
		
	}
	
	//	カテゴリー更新　実行
	@PostMapping("/category/{id}/edit")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam(name = "name", defaultValue = "") String name
			) {
		
		Category category = new Category(id, name);
		
		categoryRepository.save(category);
		
		return "redirect:/category";
		
	}
	
	//	カテゴリー削除　実行
	@PostMapping("category/{id}/delete")
	public String delete(@PathVariable("id") Integer id) {
		
		categoryRepository.deleteById(id);
		
		return "redirect:/category";
		
		
	}
	
	
	
}




