package com.reflect7.plansation.client.data;

import java.util.HashMap;
import java.util.List;

import com.googlecode.objectify.Key;
import com.reflect7.commongwt.client.event.Action;
import com.reflect7.plansation.client.remoteservice.TaskServiceClient;
import com.reflect7.plansation.shared.model.Task;

public class TaskRepository {
	
	private TaskServiceClient _tsc;
	
/***************************************************
 * CONSTRUCTORS
 ***************************************************/
	
	public TaskRepository(TaskServiceClient tsc){
		_tsc = tsc;
	}
	
/***************************************************
 * PROPERTIES
 ***************************************************/
	
	private HashMap<Long, Task> _tasks = new HashMap<Long, Task>();
		public HashMap<Long, Task> getTasks(){
			return new HashMap<Long, Task>(_tasks); //return shallow copy
		}
		
	public int getCount(){ return _tasks.size(); }
	
	public int size() { return this.getCount(); }
	
/***************************************************
 * PUBLIC METHODS
 ***************************************************/
		
	public Task createTask(String name){
		Task t = new Task();
		t.name = name;
		this.persist(t);
		return t;
	}
	
	public Task createTask(Task parent, String name){
		Task t = new Task(name);
		t.name = name;
		parent.hasChildren = true;
		
		/*if (parent.id != null)
		t.parent = new Key<Task>(Task.class, parent.id);*/
		
		this.persist(parent, t);
		return t;
	}
	
	public void loadRootTasks(final Action<List<Task>> action){
		_tsc.loadRootTasks(new Action<List<Task>>(){
			@Override public void execute(List<Task> tasks) {
				for (Task t : tasks){
					_tasks.put(t.id, t);
				}
				
				action.execute(tasks);
			}
		});
	}
	
	public void loadChildTasks(final Task parent, final Action<List<Task>> action){
		_tsc.loadChildTasks(parent, new Action<List<Task>>(){
			@Override public void execute(List<Task> tasks) {
				for (Task t : tasks){
					_tasks.put(t.id, t);
					t.setParent(parent);
					t.setRoot(_tasks.get(t.root.getId()));
				}
				
				action.execute(tasks);
			}
		});
	}
	
	public void removeTask(Task task){
		
	}
	
	public void removeChildTask(Task parent, Task child){
		
	}
	
/***************************************************
 * PRIVATE METHODS
 ***************************************************/
	
	private void persist(Task task){
		_tsc.saveTask(task, new Action<Task>(){
			@Override public void execute(Task task) {
				_tasks.put(task.id, task);
			}
		});
	}
	
	private void persist(Task parent, Task child){
		_tsc.saveTask(parent, child, new Action<Task>(){
			@Override public void execute(Task task) {
				_tasks.put(task.id, task);
			}
		});
	}
}
