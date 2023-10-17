package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.VTask;

public interface VTaskRepository extends JpaRepository<VTask, Integer> {

	//	検索：無　・　並び替え：taskのId昇順
	List<VTask> findByUserIdOrderById(Integer userId);

	//	カテゴリ　検索
	//	List<VTask> findByCategoryIdAndUserId(Integer categoryId, Integer userId);

	//	検索：カテゴリ　・　並び替え：taskのId昇順
	List<VTask> findByCategoryIdAndUserIdOrderById(Integer categoryId, Integer userId);

	//	検索：カテゴリ　・　並び替え：期日	
	List<VTask> findByCategoryIdAndUserIdOrderByDeadline(Integer categoryId, Integer userId);

	//	検索：無　・　並び替え：カテゴリ・期日
	List<VTask> findByUserIdOrderByCategoryIdAscDeadlineAsc(Integer userId);

	//	並び替え：期日
	List<VTask> findByUserIdOrderByDeadline(Integer userId);

	//	並び替え：カテゴリ
	List<VTask> findByUserIdOrderByCategoryId(Integer userId);

}
