package com.example.demo.entity;

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
	
	private String name;
	
	//	コンストラクタ
	//	デフォルト
	public Category() {
		
	}
	
	//	新規
	public Category(String name) {
		this.name = name;
	}
	
	//	更新
	public Category(Integer id, String name) {
		this(name);
		this.id = id;
	}
	
	
	//	ゲッター・セッター
	public Integer getId() {
		return id;
	}

//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getName() {
		return name;
	}

//	public void setName(String name) {
//		this.name = name;
//	}
	
}
