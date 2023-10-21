package com.example.demo.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

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
	private Integer categoryId;

	private String task;

	@Column(name = "task_detail")
	private String taskDetail;

	//	private String deadline;
	private LocalDate deadline;

	private String situation;

	@Column(name = "finish_date")
	private LocalDate finishDate;

	@Transient
	private final static DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	//	コンストラクタ
	//	デフォルト
	public Task() {

	}

	//	追加
	public Task(
			Integer userId, Integer categoryId, String task, String taskDetail, LocalDate deadline, String situation) {
		this.userId = userId;
		this.categoryId = categoryId;
		this.task = task;
		this.taskDetail = taskDetail;
		this.deadline = deadline;
		this.situation = situation;
	}

	//　更新
	public Task(
			Integer id, Integer userId, Integer categoryId, String task, String taskDetail, LocalDate deadline,
			String situation) {
		this(userId, categoryId, task, taskDetail, deadline, situation);
		this.id = id;
	}

	//	完了
	public Task(
			Integer id, Integer userId, Integer categoryId, String task, String taskDetail, LocalDate deadline,
			String situation, LocalDate finishDate) {
		this(id, userId, categoryId, task, taskDetail, deadline, situation);
		this.situation = "済";
		this.finishDate = finishDate;
	}

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

	//	public void setCategoryId(Integer categroyId) {
	//		this.categoryId = categoryId;
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

		return deadline.format(FMT);

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

	public static DateTimeFormatter getFmt() {
		return FMT;
	}

	public LocalDate getLDeadline() {
		return deadline;
	}

	public String getFinishDate() {
		return finishDate.format(FMT);
	}

	//	public void setFinishDate(LocalDate finishDate) {
	//		this.finishDate = finishDate;
	//	}
}
