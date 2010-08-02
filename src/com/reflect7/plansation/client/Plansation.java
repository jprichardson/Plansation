package com.reflect7.plansation.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.reflect7.plansation.client.data.TaskRepository;
import com.reflect7.plansation.client.remoteservice.TaskService;
import com.reflect7.plansation.client.remoteservice.TaskServiceAsync;
import com.reflect7.plansation.client.remoteservice.TaskServiceClient;
import com.reflect7.plansation.client.view.MainView;
import com.reflect7.plansation.client.view.model.TreeTaskPanel;


public class Plansation implements EntryPoint {
	
	private static TaskRepository _taskRepo;
	private static TaskServiceAsync _taskService = GWT.create(TaskService.class);
	private static TaskServiceClient _taskServiceClient;
	
	public void onModuleLoad() {
		configure();
		RootLayoutPanel.get().add(new MainView());
		//RootLayoutPanel.get().add(new TreeTaskPanel());
	}
	
	private void configure(){
		_taskServiceClient = new TaskServiceClient(_taskService);
		_taskRepo = new TaskRepository(_taskServiceClient);
	}
	
	//Not ideal, but until Google stablizes their MVP framework, I'm stuck with using Singletons
	public static TaskRepository getTaskRepo() { return _taskRepo; }
	public static TaskServiceClient getTaskServiceClient() { return _taskServiceClient; }
}
