package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "v_tasks")
public class VTask {

	//	フィールド
	//	主キー
	@Id
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "category_id")
	private Integer categoryId;

	@Column(name = "category_name")
	private String categoryName;

	private String task;

	@Column(name = "task_detail")
	private String taskDetail;

	private LocalDate deadline;

	private String situation;

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

	public Integer getCategoryId() {
		return categoryId;
	}

	//	public void setCategoryId(Integer categoryId) {
	//		this.categoryId = categoryId;
	//	}

	public String getCategoryName() {
		return categoryName;
	}

	//	public void setCategoryName(String categoryName) {
	//		this.categoryName = categoryName;
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

	public String getDeadline() {
		return deadline.getYear() + "/" +
				deadline.getMonthValue() + "/" +
				deadline.getDayOfMonth();
	}

	//	public void setDeadline(LocalDate deadline) {
	//		this.deadline = deadline;
	//	}

	public String getSituation() {
		return situation;
	}

	//	public void setSituation(String situation) {
	//		this.situation = situation;
	//	}
}
