package com.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_user")
public class User {
	private int id;
	private String name;
	private String password_md5;
	private String  email;
	private Timestamp rdate;
	
	public User(){
		
	}
	
	public User(int id){
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public User setName(String name) {
		this.name = name;
		return this;
	}
	public String getPassword_md5() {
		return password_md5;
	}
	public User setPassword_md5(String password_md5) {
		this.password_md5 = password_md5;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public User setEmail(String email) {
		this.email = email;
		return this;
	}
	public Timestamp getRdate() {
		return rdate;
	}
	public User setRdate(Timestamp rdate) {
		this.rdate = rdate;
		return this;
	}
}
