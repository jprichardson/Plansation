package com.reflect7.plansation.client.remoteservice;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.reflect7.plansation.shared.model.Task;

@RemoteServiceRelativePath("task")
public interface TaskService extends RemoteService{
	public Iterable<Task> loadRootTasks() throws IllegalArgumentException;
	public Iterable<Task> loadChildTasks(Task parent) throws IllegalArgumentException;
	public String saveTask(Task task) throws IllegalArgumentException;
	public String saveTask(Task parent, Task child) throws IllegalArgumentException;
}
