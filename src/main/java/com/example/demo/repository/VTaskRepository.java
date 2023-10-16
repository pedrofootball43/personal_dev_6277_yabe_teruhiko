package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.VTask;

public interface VTaskRepository extends JpaRepository<VTask, Integer> {

	//	全表示　taskのId　昇順
	List<VTask> findByUserIdOrderById(Integer userId);

	//	カテゴリ　絞込
	List<VTask> findByCategoryIdAndUserId(Integer categoryId, Integer userId);

	List<VTask> findByCategoryIdAndUserIdOrderById(Integer categoryId, Integer userId);

}
