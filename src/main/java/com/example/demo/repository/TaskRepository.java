package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	//	カテゴリーID・ユーザーID　カテゴリ検索
	List<Task> findByCategoryIdAndUserId(Integer categoryId, Integer userId);

	//	ユーザーID　一覧表示
	List<Task> findByUserId(Integer userId);

}
