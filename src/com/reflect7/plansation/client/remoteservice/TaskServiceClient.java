package com.reflect7.plansation.client.remoteservice;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;
import com.reflect7.commongwt.client.event.Action;
import com.reflect7.plansation.shared.model.Task;

public class TaskServiceClient {

	private TaskServiceAsync _service;
	
	public TaskServiceClient(TaskServiceAsync service){
		_service = service;
	}
	
	public void loadRootTasks(final Action<List<Task>> action){
		/*Throwable t = new Throwable();
		StackTraceElement[] stes = t.getStackTrace();
		for (StackTraceElement ste : stes){
			System.out.println(ste.getMethodName());
		}*/
		
		_service.loadRootTasks(new AsyncCallback<List<Task>>(){
			public void onFailure(Throwable caught) {
				Window.alert("Load Tasks - Failure");
			}

			public void onSuccess(List<Task> result) {
				action.execute(result);
			}
		});
	}
	
	public void loadChildTasks(Task parent, final Action<List<Task>> action){
		_service.loadChildTasks(parent, new AsyncCallback<List<Task>>(){
			public void onFailure(Throwable caught) {
				Window.alert("Load Child Tasks - Failure");
			}

			public void onSuccess(List<Task> result) {
				action.execute(result);
			}
		});
	}
	
	public void saveTask(final Task task, final Action<Task> successAction){
		_service.saveTask(task, new AsyncCallback<Key<Task>>(){
			public void onFailure(Throwable caught) {
				Window.alert("Save Task - Failure");
			}

			public void onSuccess(Key<Task> key) {
				task.id = key.getId();
				
				if (successAction != null)
					successAction.execute(task);
			}
		});
	}
	
	public void saveTask(final Task parent, final Task child){		
		this.saveTask(parent, child, null);
	}
	
	public void saveTask(final Task parent, final Task child, final Action<Task> successAction){
		_service.saveTask(parent, child, new AsyncCallback<List<Key<Task>>>(){
			public void onFailure(Throwable caught) {
				Window.alert("Save Task Parent/Child - Failure");
			}

			public void onSuccess(List<Key<Task>> keys){
				Key<Task> rootKey = keys.get(0);
				Key<Task> parentKey = keys.get(1);
				Key<Task> childKey = keys.get(2);
				
				child.root = rootKey;
				child.id = childKey.getId();
				child.parent = parentKey;
				
				child.setParent(parent);
				if (parent.root == null)
					child.setRoot(parent);
				else
					child.setRoot(parent.getRoot());
				
				if (successAction != null)
					successAction.execute(child);
			}
		});
	}
	
}
