package com.reflect7.plansation.client.remoteservice;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.reflect7.plansation.client.event.Action;
import com.reflect7.plansation.shared.model.Task;

public class TaskServiceClient {

	private TaskServiceAsync _service;
	
	public TaskServiceClient(TaskServiceAsync service){
		_service = service;
	}
	
	public void loadTasks(final Action<Iterable<Task>> action){
		/*Throwable t = new Throwable();
		StackTraceElement[] stes = t.getStackTrace();
		for (StackTraceElement ste : stes){
			System.out.println(ste.getMethodName());
		}*/
		
		_service.loadRootTasks(new AsyncCallback<Iterable<Task>>(){
			public void onFailure(Throwable caught) {
				Window.alert("Load Tasks - Failure");
			}

			public void onSuccess(Iterable<Task> result) {
				action.execute(result);
			}
		});
	}
	
	public void loadChildTasks(Task parent, final Action<Iterable<Task>> action){
		_service.loadChildTasks(parent, new AsyncCallback<Iterable<Task>>(){
			public void onFailure(Throwable caught) {
				Window.alert("Load Child Tasks - Failure");
			}

			public void onSuccess(Iterable<Task> result) {
				action.execute(result);
			}
		});
	}
	
	public void saveTask(Task task){
		_service.saveTask(task, new AsyncCallback<String>(){
			public void onFailure(Throwable caught) {
				Window.alert("Save Task - Failure");
			}

			public void onSuccess(String result) {}
		});
	}
	
	public void saveTask(Task parent, Task child){
		_service.saveTask(parent, child, new AsyncCallback<String>(){
			public void onFailure(Throwable caught) {
				Window.alert("Save Task Parent/Child - Failure");
			}

			public void onSuccess(String result) {}
		});
	}
	
}
