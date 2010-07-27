package com.reflect7.plansation.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Unindexed;



@SuppressWarnings("serial")
public class Task implements Serializable {
	
	public Task() {}
	
	public Task(String taskName){
		this.name = taskName;
	}
	
	public @Id Long id;
	public String name;
	public Boolean isProject = false;
	public Boolean isCompleted = false;
	public Date createdAt;
	public Date completedAt;
	public Key<Task> parent = null;
	public Key<Task> root = null;
	
	@Unindexed
	public @Embedded List<Key<Task>> subTasks = new ArrayList<Key<Task>>();
	
	@Unindexed
	public String description;
}

