package com.yc.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable{
	private Long id;
	private Long titleId;
	private Long userId;
	private String content;
	private Timestamp commentTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTitleId() {
		return titleId;
	}
	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Timestamp commentTime) {
		this.commentTime = commentTime;
	}
	
	
}
