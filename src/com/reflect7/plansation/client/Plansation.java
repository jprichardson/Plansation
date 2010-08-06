package com.reflect7.plansation.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.reflect7.commongwt.client.event.Action;
import com.reflect7.plansation.client.data.TaskRepository;
import com.reflect7.plansation.client.remoteservice.IterationService;
import com.reflect7.plansation.client.remoteservice.IterationServiceAsync;
import com.reflect7.plansation.client.remoteservice.IterationServiceClient;
import com.reflect7.plansation.client.remoteservice.TaskService;
import com.reflect7.plansation.client.remoteservice.TaskServiceAsync;
import com.reflect7.plansation.client.remoteservice.TaskServiceClient;
import com.reflect7.plansation.client.view.MainView;
import com.reflect7.plansation.client.view.model.TreeTaskPanel;
import com.reflect7.plansation.shared.model.Iteration;
import com.reflect7.plansation.shared.model.Task;

/*****************************
 * 
 * @author JP
 * This is becoming very tightly coupled. I hope Google releases their MVP framework soon!
 *
 */

public class Plansation implements EntryPoint {
	
	private static TaskRepository _taskRepo;
	private static TaskServiceAsync _taskService = GWT.create(TaskService.class);
	private static TaskServiceClient _taskServiceClient;
	
	private static IterationServiceAsync _iterationService = GWT.create(IterationService.class);
	private static IterationServiceClient _iterationServiceClient;
	
	private static MainView _mainView = new MainView();
	
	public void onModuleLoad() {
		RootLayoutPanel.get().add(_mainView);
		configure();
	}
	
	private void configure(){
		_taskServiceClient = new TaskServiceClient(_taskService);
		_iterationServiceClient = new IterationServiceClient(_iterationService);
		
		_taskRepo = new TaskRepository(_taskServiceClient);
		
		_iterationServiceClient.loadCurrentIteration(new Action<Iteration>(){
			@Override public void execute(Iteration object) {
				_mainView.getIterationPanel().setIteration(object);
			}
		});
		
		_taskRepo.loadRootTasks(new Action<List<Task>>(){
			@Override public void execute(List<Task> tasks) {
				_mainView.getTreeTaskPanel().setTaskRepo(_taskRepo);
				_mainView.getTreeTaskPanel().addTasks(tasks);
			}
		});
	}
	
	//Not ideal, but until Google stabilizes their MVP framework, I'm stuck with using Singletons
	//public static TaskRepository getTaskRepo() { return _taskRepo; }
	//public static TaskServiceClient getTaskServiceClient() { return _taskServiceClient; }
	//public static IterationServiceClient getIterationServiceClient() { return _iterationServiceClient; }
}
