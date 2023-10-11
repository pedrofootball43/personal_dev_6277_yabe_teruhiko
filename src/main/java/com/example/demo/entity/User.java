package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	//	フィールド
	//	主キー
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "login_id")
	private String loginId;
	
	private String name;
	private String password;
		
	//	ゲッター・セッター
	public Integer getId() {
		return id;
	}
	
//	public void setId(Integer id) {
//		this.id = id;
//	}
	
	public String getLoginId() {
		return loginId;
	}
	
//	public void setLoginId(String loginId) {
//		this.loginId = loginId;
//	}
	
	public String getName() {
		return name;
	}
	
//	public void setName(String name) {
//		this.name = name;
//	}
	
	public String getPassword() {
		return password;
	}
	
//	public void setPassword(String password) {
//		this.password = password;
//	}
	
	
}
