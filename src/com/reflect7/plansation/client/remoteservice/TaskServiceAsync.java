package com.reflect7.plansation.client.remoteservice;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;
import com.reflect7.plansation.shared.model.Task;

public interface TaskServiceAsync {
	void loadRootTasks(AsyncCallback<List<Task>> callback) throws IllegalArgumentException;
	void loadChildTasks(Task parent, AsyncCallback<List<Task>> callback) throws IllegalArgumentException;
	void saveTask(Task task, AsyncCallback<Key<Task>> callback) throws IllegalArgumentException;
	void saveTask(Task parent, Task child, AsyncCallback<List<Key<Task>>> callback) throws IllegalArgumentException;
}