package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.VTask;

public interface VTaskRepository extends JpaRepository<VTask, Integer> {

	//	---未完了---
	//	検索：無　・　並び替え：taskのId昇順
	//	List<VTask> findByUserIdOrderById(Integer userId);

	List<VTask> findByUserIdAndSituationOrderById(Integer userId, String situation);

	//	カテゴリ　検索
	//	List<VTask> findByCategoryIdAndUserId(Integer categoryId, Integer userId);

	//	検索：カテゴリ　・　並び替え：taskのId昇順
	List<VTask> findByCategoryIdAndUserIdAndSituationOrderById(Integer categoryId, Integer userId, String situation);

	//	検索：カテゴリ　・　並び替え：期日	
	List<VTask> findByCategoryIdAndUserIdAndSituationOrderByDeadline(Integer categoryId, Integer userId,
			String situation);

	//	検索：無　・　並び替え：カテゴリ・期日
	List<VTask> findByUserIdAndSituationOrderByCategoryIdAscDeadlineAsc(Integer userId, String situation);

	//	並び替え：期日
	List<VTask> findByUserIdAndSituationOrderByDeadline(Integer userId, String situation);

	//	並び替え：カテゴリ
	List<VTask> findByUserIdAndSituationOrderByCategoryId(Integer userId, String situation);

}
