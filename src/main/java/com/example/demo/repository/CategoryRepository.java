package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	//	カテゴリー一覧　表示
	List<Category> findAllByOrderByIdAsc();

	List<Category> findByUserIdOrderByIdAsc(Integer userId);

	//	カテゴリー名　重複チェック
	List<Category> findByName(String name);

	List<Category> findByUserIdAndName(Integer userId, String name);

}
