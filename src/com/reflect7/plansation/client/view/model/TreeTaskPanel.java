package com.reflect7.plansation.client.view.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.google.gwt.core.client.GWT;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.shared.HandlerRegistration;
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
import com.reflect7.commongwt.client.ui.widget.TextBox;
import com.reflect7.plansation.client.Plansation;
import com.reflect7.plansation.client.data.TaskRepository;
import com.reflect7.plansation.client.event.Action;
import com.reflect7.plansation.client.remoteservice.TaskService;
import com.reflect7.plansation.client.remoteservice.TaskServiceAsync;
import com.reflect7.plansation.client.remoteservice.TaskServiceClient;
import com.reflect7.plansation.shared.model.Task;

public class TreeTaskPanel extends Composite implements MouseOutHandler {

	private static TreeTaskPanelUiBinder uiBinder = GWT.create(TreeTaskPanelUiBinder.class);
	interface TreeTaskPanelUiBinder extends UiBinder<Widget, TreeTaskPanel> {}

	@UiField Tree treeTasks;
	@UiField TextBox textTask;

	//HashMap<TreeItem, Task> _tasks = new HashMap<TreeItem, Task>();
	TaskServiceClient taskServiceClient;
	TaskRepository _taskRepo;
	
	private static String TREEITEM_LOADING = "Loading...";
	
	public TreeTaskPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		taskServiceClient = Plansation.getTaskServiceClient();
		_taskRepo = Plansation.getTaskRepo();
		
		//_tasks.put(_loadingItem, null);
		
		_taskRepo.loadRootTasks(new Action<List<Task>>(){
			public void execute(List<Task> result){
				addTasks(result);
			}
		});
		
		this.addDomHandler(this, MouseOutEvent.getType());
	}

	
	
/******************************************************
 * UI METHODS
 ******************************************************/
	/** 
     * MouseDownHandler
     */
    public void onMouseOut(MouseOutEvent event) {
		treeTasks.setSelectedItem(null);
    }

	
	@UiHandler("textTask")
	void handleChange(ChangeEvent e){
		
	}
	
	@UiHandler("textTask")
	void handleKeyPressf(KeyPressEvent e){
		byte b = (byte)e.getCharCode();
		if (b == 13){//[ENTER] key pressed
			String task = textTask.getText().trim();
			textTask.setText("");
			this.addAndSaveTask(task);
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
		final TreeItem pi = e.getTarget();
		Task parent = (Task)pi.getUserObject();	
		
		if (pi.getChild(0).getText().equals(TREEITEM_LOADING)){
			_taskRepo.loadChildTasks(parent, new Action<List<Task>>(){
				@Override public void execute(List<Task> tasks) {
					addTasks(pi, tasks);
					pi.removeItem(pi.getChild(0));
				}
			});
		}
	}
	
	@UiHandler("treeTasks")
	void handleMouseOut(MouseOutEvent e){
		/*if (treeTasks.getSelectedItem() != null)
			treeTasks.setSelectedItem(null);*/
	}

/******************************************************
 * PUBLIC METHODS
 ******************************************************/
	
	public Task getSelectedTask(){
		TreeItem selectedItem = treeTasks.getSelectedItem();
		if (selectedItem != null){
			//return _tasks.get(selectedItem);
			return (Task)selectedItem.getUserObject();
		}else
			return null;
	}
	
/******************************************************
 * PRIVATE METHODS
 ******************************************************/
	private void addAndSaveTask(String task){
		Task parent = getTaskOfSelectedItem();
		Task child =  null;
		if (parent != null)
			child = _taskRepo.createTask(parent, task);
		else
			child = _taskRepo.createTask(task);
	
		TreeItem selectedItem = treeTasks.getSelectedItem();
		this.addTask(selectedItem, child);
		
		if (selectedItem.getChildCount() == 1)
			selectedItem.setState(true);
	}
	
	private void addTask(TreeItem parentItem, Task task){
		TreeItem newItem = new TreeItem(task.name);
		
		if (parentItem != null){
			parentItem.addItem(newItem);
		}else
			treeTasks.addItem(newItem);
		
		//_tasks.put(newItem, task);
		newItem.setUserObject(task);
		
		if (task.hasChildren)
			newItem.addItem(new TreeItem(TREEITEM_LOADING));
	}
	
	private void addTasks(List<Task> tasks){
		for (Task t : tasks)
			addTask(null, t);
	}
	
	private void addTasks(TreeItem parentItem, List<Task> tasks){
		for (Task t : tasks)
			this.addTask(parentItem, t);
	}
	
	private Task getTaskOfSelectedItem(){
		TreeItem selectedItem = treeTasks.getSelectedItem();
		Task t = null;
		if (selectedItem != null)
			t = (Task)selectedItem.getUserObject();//_tasks.get(selectedItem);
		return t;
	}
}
