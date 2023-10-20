package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

	//	フィールド
	//	主キー
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;

	private String name;

	//	コンストラクタ
	//	デフォルト
	public Category() {

	}

	//	新規
	public Category(Integer userId, String name) {
		this.userId = userId;
		this.name = name;
	}

	//	public Category(String name) {
	//		this.name = name;
	//	}

	//	更新
	public Category(Integer id, Integer userId, String name) {
		this(userId, name);
		this.id = id;
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
	//	
	//	}

	public String getName() {
		return name;
	}

	//	public void setName(String name) {
	//		this.name = name;
	//	}

}
