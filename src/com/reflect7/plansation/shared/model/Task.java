package com.reflect7.plansation.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Unindexed;
import com.reflect7.commongwt.client.util.DateTimeUtil;

/*
 * See: http://code.google.com/p/objectify-appengine/wiki/ObjectifyWithGWT for
 * @Transient vs transient
 * In short:
 * transient = will not be serialized by GWT
 * @Transient = will not be persisted by App Engine datastore
 * 
 * 
 * 
 */


@SuppressWarnings("serial")
public class Task extends ModelBase implements Serializable {
	
	public Task() {}
	
	public Task(String taskName){
		this.name = taskName;
	}
	
	public @Id Long id;
	
	public String name;
	
	public boolean hasChildren = false;
	
	public boolean isCompleted = false;
	public boolean inProgress = false;
		public void setCompleted(){
			this.isCompleted = true;
			this.inProgress = false;
		}
	
	private long createdAt = 0;
		public Date getDateCreatedAt(){
			Date d = new Date();
			d.setTime(this.createdAt);
			return d;
		}
		
		public String getDateTimeCreatedAt(){ //CLIENT ONLY
			return DateTimeUtil.getShortDateTime(this.getDateCreatedAt());
		}
	
	private long completedAt = 0;
		public Date getDateCompletedAt(){
			Date d = new Date();
			d.setTime(this.completedAt);
			return d;
		}
	
		public String getDateTimeCompletedAt(){ //CLIENT ONLY
			return DateTimeUtil.getShortDateTime(this.getDateCompletedAt());
		}
		
	private @Transient transient List<Task> _children = new ArrayList<Task>();
		public List<Task> getChildren() { return _children; }
	
	public Key<Task> parent = null;
	private @Transient transient Task _parent = null;
		public Task geParent() { return _parent; }
		public void setParent(Task parent) { _parent = parent; }
	
	public Key<Task> root = null;
	private @Transient Task _root = null;
		public Task getRoot() { return _root; }
		public void setRoot(Task root) { _root = root; }
	
	public Key<Iteration> iteration = null;
		
	/*@Unindexed
	public @Embedded List<Key<Task>> subTasks = new ArrayList<Key<Task>>();*/
	
	@Unindexed
	public String description;
	
	
	public String toString(){
		return this.name;
	}
	
	@Override //SERVER ONLY
	protected void beforePersist(){
		if (this.createdAt == 0)
			this.createdAt = (new Date()).getTime();
		
		if ((this.completedAt == 0) && this.isCompleted && !this.inProgress)
			this.completedAt = (new Date()).getTime();
	}
	
}

