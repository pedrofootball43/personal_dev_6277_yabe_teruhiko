package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {

	//	フィールド
	//	主キー
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "category_id")
	private Integer categroyId;
	
	private String task;
	
	@Column(name = "task_detail")
	private String taskDetail;
	
	private LocalDate deadline;
	
	//	ゲッター・セッター
	public Integer getId() {
		return id;
	}

//	public void setId(Integer id) {
//		this.id = id;
//	}

	public Integer getUserId() {
		return userId;
	}

//	public void setUserId(Integer userId) {
//		this.userId = userId;
//	}

	public Integer getCategroyId() {
		return categroyId;
	}

//	public void setCategroyId(Integer categroyId) {
//		this.categroyId = categroyId;
//	}

	public String getTask() {
		return task;
	}

//	public void setTask(String task) {
//		this.task = task;
//	}

	public String getTaskDetail() {
		return taskDetail;
	}

//	public void setTaskDetail(String taskDetail) {
//		this.taskDetail = taskDetail;
//	}

	public LocalDate getDeadline() {
		return deadline;
	}

//	public void setDeadline(LocalDate deadline) {
//		this.deadline = deadline;
//	}
	
}
