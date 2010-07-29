package com.reflect7.plansation.client.remoteservice;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.reflect7.plansation.shared.model.Task;

public interface TaskServiceAsync {
	void loadRootTasks(AsyncCallback<Iterable<Task>> callback) throws IllegalArgumentException;
	void loadChildTasks(Task parent, AsyncCallback<Iterable<Task>> callback) throws IllegalArgumentException;
	void saveTask(Task task, AsyncCallback<String> callback) throws IllegalArgumentException;
	void saveTask(Task parent, Task child, AsyncCallback<String> callback) throws IllegalArgumentException;
}