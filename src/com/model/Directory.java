package com.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_directory")
public class Directory implements Serializable{
	private int id;
	private User user;
	private Directory pdir;
	private MyFile myFile;
	private String path;
	private String dir;
	private boolean leaf;
	private boolean folder;
	private Timestamp cdate;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="userid")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne
	@JoinColumn(name="pid")
	public Directory getPdir() {
		return pdir;
	}
	public void setPdir(Directory pdir) {
		this.pdir = pdir;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public Timestamp getCdate() {
		return cdate;
	}
	public void setCdate(Timestamp cdate) {
		this.cdate = cdate;
	}
	public boolean isFolder() {
		return folder;
	}
	public void setFolder(boolean folder) {
		this.folder = folder;
	}
	@OneToOne
	@JoinColumn(name="fid")
	public MyFile getMyFile() {
		return myFile;
	}
	public void setMyFile(MyFile file) {
		this.myFile = file;
	}
	
	public boolean category(){
		if(this.dir == null || this.dir.trim().equals("")){
			return false;
		}

		int index = this.dir.lastIndexOf(".");
		if(index == -1 || index == this.dir.length()-1){
			return true;
		}
		
		String category = this.dir.substring(index+1);
		
		ArrayList<String> categories = new ArrayList<String>();
		categories.add("avi");
		categories.add("mp4");
		categories.add("flv");
		
		if(categories.contains(category)){
			return true;
		}
		
		return false;
	}
	
}
