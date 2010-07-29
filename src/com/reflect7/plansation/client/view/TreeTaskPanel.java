package com.reflect7.plansation.client.view;

import java.util.HashMap;
import java.util.HashSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;

import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.reflect7.plansation.client.event.Action;
import com.reflect7.plansation.client.remoteservice.TaskService;
import com.reflect7.plansation.client.remoteservice.TaskServiceAsync;
import com.reflect7.plansation.client.remoteservice.TaskServiceClient;
import com.reflect7.plansation.client.widget.TextBox;
import com.reflect7.plansation.shared.model.Task;

public class TreeTaskPanel extends Composite {

	private static TreeTaskPanelUiBinder uiBinder = GWT.create(TreeTaskPanelUiBinder.class);
	interface TreeTaskPanelUiBinder extends UiBinder<Widget, TreeTaskPanel> {}

	@UiField Tree treeTasks;
	@UiField TextBox textTask;

	HashMap<TreeItem, Task> _tasks = new HashMap<TreeItem, Task>();
	
	private static TaskServiceAsync taskService = GWT.create(TaskService.class);
	private static TaskServiceClient taskServiceClient = null;
	public TreeTaskPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		taskServiceClient = new TaskServiceClient(taskService);
		taskServiceClient.loadTasks(new Action<Iterable<Task>>(){
			public void execute(Iterable<Task> result){
				for (Task t : result)
					addTask(t);
			}
		});
	}

	
	
/******************************************************
 * UI METHODS
 ******************************************************/
	@UiHandler("textTask")
	void handleChange(ChangeEvent e){
		
	}
	
	@UiHandler("textTask")
	void handleKeyPressf(KeyPressEvent e){
		byte b = (byte)e.getCharCode();
		if (b == 13){//[ENTER] key pressed
			String task = textTask.getText().trim();
			Task child = new Task(task);
			Task parent = addTask(child);
			textTask.setText("");
			
			if (parent == null)
				taskServiceClient.saveTask(child);
			else
				taskServiceClient.saveTask(parent, child);
		}
	}
	
	@UiHandler("treeTasks")
	void handleKeyPress(KeyPressEvent e){
		byte b = (byte)e.getCharCode();
		if (b == 8){//[DELETE] key pressed
			TreeItem selectedItem = treeTasks.getSelectedItem();
			if (selectedItem != null){
				TreeItem parentItem = selectedItem.getParentItem();
				if (parentItem == null)
					treeTasks.removeItem(selectedItem);
				else
					selectedItem.getParentItem().removeItem(selectedItem);
			}
		}
	}
	
	@UiHandler("treeTasks")
	void handleOpen(OpenEvent<TreeItem> e){
		TreeItem pi = e.getTarget();
		Task parent = _tasks.get(pi);
		
		taskServiceClient.loadChildTasks(parent, new Action<Iterable<Task>>(){
			public void execute(Iterable<Task> result){
				for (Task t : result)
					addTask(t);
			}
		});
	}

/******************************************************
 * PUBLIC METHODS
 ******************************************************/
	
	public Task getSelectedTask(){
		TreeItem selectedItem = treeTasks.getSelectedItem();
		if (selectedItem != null)
			return _tasks.get(selectedItem);
		else
			return null;
	}
	
/******************************************************
 * PRIVATE METHODS
 ******************************************************/
	private Task addTask(Task task){
		TreeItem newItem = new TreeItem(task.getDateTimeCreatedAt());
		
		TreeItem selectedItem = treeTasks.getSelectedItem();
		
		Task parent = null;
		if (selectedItem != null){
			selectedItem.addItem(newItem);
			parent = _tasks.get(selectedItem);
		}else
			treeTasks.addItem(newItem);
		
		_tasks.put(newItem, task);
		
		return parent;
	}
}
