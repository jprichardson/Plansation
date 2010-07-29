package com.reflect7.plansation.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Unindexed;
import com.reflect7.commongwt.client.util.DateTimeUtil;



@SuppressWarnings("serial")
public class Task extends ModelBase implements Serializable {
	
	public Task() {}
	
	public Task(String taskName){
		this.name = taskName;
	}
	
	public @Id Long id;
	
	public String name;
	public Boolean isCompleted = false;
	
	private long createdAt = 0;
		public Date getDateCreatedAt(){
			Date d = new Date();
			d.setTime(this.createdAt);
			return d;
		}
		
		public String getDateTimeCreatedAt(){
			return DateTimeUtil.getShortDateTime(this.getDateCreatedAt());
		}
	
	private long completedAt = 0;
		public Date getDateCompletedAt(){
			Date d = new Date();
			d.setTime(this.createdAt);
			return d;
		}
	
		public String getDateTimeCompletedAt(){
			return DateTimeUtil.getShortDateTime(this.getDateCompletedAt());
		}
	
	public Key<Task> parent = null;
	public Key<Task> root = null;
	
	@Unindexed
	public @Embedded List<Key<Task>> subTasks = new ArrayList<Key<Task>>();
	
	@Unindexed
	public String description;
	
	@Override //SERVER ONLY
	protected void beforePersist(){
		if (this.createdAt == 0){
			this.createdAt = (new Date()).getTime();
		}
		
		//DO SOMETHING ABOUT COMPLETETION TIME
	}
	
}

