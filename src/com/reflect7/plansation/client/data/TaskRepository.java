package com.reflect7.plansation.client.data;

import java.util.List;

import com.googlecode.objectify.Key;
import com.reflect7.plansation.client.event.Action;
import com.reflect7.plansation.client.remoteservice.TaskServiceClient;
import com.reflect7.plansation.shared.model.Task;

public class TaskRepository {
	
	private TaskServiceClient _tsc;
	
	public TaskRepository(TaskServiceClient tsc){
		_tsc = tsc;
	}
	
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
	
	public void loadRootTasks(Action<List<Task>> action){
		_tsc.loadRootTasks(action);
	}
	
	public void loadChildTasks(final Task parent, final Action<List<Task>> action){
		_tsc.loadChildTasks(parent, new Action<List<Task>>(){
			@Override public void execute(List<Task> tasks) {
				for (Task t : tasks)
					t.setParent(parent);
				
				parent.getChildren().clear();
				parent.getChildren().addAll(tasks);
				
				action.execute(tasks);
			}
		});
	}
	
	public void removeTask(Task task){
		
	}
	
	public void removeChildTask(Task parent, Task child){
		
	}
	
	private void persist(Task task){
		_tsc.saveTask(task);
	}
	
	private void persist(Task parent, Task child){
		_tsc.saveTask(parent, child);
	}
}
