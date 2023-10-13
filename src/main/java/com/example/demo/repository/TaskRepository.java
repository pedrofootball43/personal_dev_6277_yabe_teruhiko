package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findByCategoryIdAndUserId(Integer categoryId, Integer userId);

	List<Task> findByUserId(Integer userId);

	//	List<Task> findByCategoryIdAndUserIdByOrderByIdAsc(Integer categoryId, Integer userId);
	//
	//	List<Task> findByCategoryIdAndUserIdOrderByIdAsc(Integer categoryId, Integer userId);
	//
	//	List<Task> findByUserIdByOrderByIdAsc(Integer userId);
	//
	//	List<Task> findByUserIdOrderByIdAsc(Integer userId);

	List<Task> findAllByOrderByIdAsc();

}
