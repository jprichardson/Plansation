package com.reflect7.plansation.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

//@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Task implements Serializable {
	
	public Task() {}
	
	public Task(String taskName){
		this.name = taskName;
	}
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	public Key getKey() { return this.key; }
	public void setKey(Key key){ this.key = key; }
	
	@Persistent
	private String name;
	public String getName(){ return name; }
	public void setName(String name) { this.name = name; }
	
	@Persistent
	private String description;
	public String getDescription(){ return description; }
	public void setDescription(String description) { this.description = description; }
	
	@Persistent
	private Boolean isProject;
	public Boolean isProject() { return isProject; }
	public void setIsProject(Boolean isProject) { this.isProject = isProject; }
	
	@Persistent
	private Boolean isCompleted = false;
	public Boolean isCompleted() { return isCompleted; }
	public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }
	
	@Persistent
	private Date createdAt;
	public Date getCreatedAt() { return this.createdAt; }
	
	@Persistent
	private Date completedAt;
	public Date getCompletedAt() { return this.completedAt; }
	
	@Persistent
	private List<Task> subTasks = new ArrayList<Task>();
	public List<Task> getSubTasks() { return subTasks; }
	
	public Task(Boolean isProject){
		this.isProject = isProject;
	}
	
}
