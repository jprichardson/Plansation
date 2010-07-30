package com.reflect7.plansation.client.remoteservice;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.googlecode.objectify.Key;
import com.reflect7.plansation.shared.model.Task;

@RemoteServiceRelativePath("task")
public interface TaskService extends RemoteService{
	public List<Task> loadRootTasks() throws IllegalArgumentException;
	public List<Task> loadChildTasks(Task parent) throws IllegalArgumentException;
	public Key<Task> saveTask(Task task) throws IllegalArgumentException;
	public Key<Task> saveTask(Task parent, Task child) throws IllegalArgumentException;
}
