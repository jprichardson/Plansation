package com.reflect7.plansation.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.reflect7.commongwt.client.event.Action;
import com.reflect7.commongwt.client.util.TreeItemUtil;
import com.reflect7.plansation.shared.model.Task;

public class ListTaskPanel extends Composite {
	
	private static ListTaskPanelUiBinder uiBinder = GWT.create(ListTaskPanelUiBinder.class);

	interface ListTaskPanelUiBinder extends UiBinder<Widget, ListTaskPanel> {}
	
	@UiField ScrollPanel scrollPanel;
	@UiField VerticalPanel verticalPanel;
	
	private HashMap<Task, Tree> _projectTrees = new HashMap<Task, Tree>();
	
	public ListTaskPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/*public void addTask(Task task){
		DisclosurePanel pnl = null;
		Task project = task.getRoot();
		if (_projectTrees.containsKey(project)){
			pnl = _projectTrees.get(project);
		} else {
			pnl = new DisclosurePanel(project.name);
			pnl.setAnimationEnabled(true);
			_projectTrees.put(project, pnl);
			verticalPanel.add(pnl);
		}
		
		pnl.add(new HTML(task.name));
	}*/
	
	public void addTreeItem(TreeItem treeItemTask){
		Task current = (Task)treeItemTask.getUserObject();
		Task project = current.getRoot();
		
		Tree projectTree = createOrFetchTree(project);
		//projectTree.addItem(treeItemTask);
		//projectTree.addItem(cloneToCheckboxes(treeItemTask));
		projectTree.addItem(cloneTreeItemsWithCheckboxes(treeItemTask));
		treeItemTask.setVisible(false);
	}
	
	private Tree createOrFetchTree(Task project){
		DisclosurePanel projectPanel = null;
		Tree projectTree = null;
		
		if (_projectTrees.containsKey(project)){
			projectTree = _projectTrees.get(project);
		} else {
			projectPanel = new DisclosurePanel(project.name);
			projectPanel.setAnimationEnabled(true);
			projectTree = new Tree();
			projectPanel.add(projectTree);
			verticalPanel.add(projectPanel);
			_projectTrees.put(project, projectTree);
		}
		
		return projectTree;
	}
	
	private TreeItem cloneTreeItemsWithCheckboxes(TreeItem root){
		final HashMap<TreeItem, TreeItem> oldNew = new HashMap<TreeItem, TreeItem>();
		final HashSet<TreeItem> didAddHandler = new HashSet<TreeItem>();
		
		TreeItemUtil.traverseDfs(root, new Action<TreeItem>(){
			public void execute(TreeItem current){
				TreeItem newTi = new TreeItem(new CheckBox(current.getText()));
				newTi.setUserObject(current.getUserObject());
				oldNew.put(current, newTi);
				
				if (current.getParentItem() != null && oldNew.containsKey(current.getParentItem())){
					TreeItem newParent = oldNew.get(current.getParentItem());
					newParent.addItem(newTi);
					
					addCheckboxHandlerChild(newTi);
					
					if (!didAddHandler.contains(newParent)){
						addCheckboxHandlerParent(newParent);
						didAddHandler.add(newParent);
					}
				}
			}
		});
		
		return oldNew.get(root);
	}
	
	//if parent is checked, check all children
	private void addCheckboxHandlerParent(final TreeItem root){
		final CheckBox checkboxParent = (CheckBox)root.getWidget();

		checkboxParent.addClickHandler(new ClickHandler(){
			@Override public void onClick(ClickEvent event) {
				List<TreeItem> children = TreeItemUtil.fetchChildren(root);
				if (checkboxParent.getValue()){
					for (TreeItem child : children){
						CheckBox checkboxChild = (CheckBox)child.getWidget();
						checkboxChild.setValue(true); //check child
					}
				}
			}
		});
	}
	
	//if child is unchecked, uncheck parent
	private void addCheckboxHandlerChild(final TreeItem child){
		final CheckBox checkboxChild = (CheckBox)child.getWidget();
		
		checkboxChild.addClickHandler(new ClickHandler(){
			@Override public void onClick(ClickEvent event) {
				if (child.getParentItem() != null){
					if (!checkboxChild.getValue()){
						CheckBox checkboxParent = (CheckBox)child.getParentItem().getWidget();
						checkboxParent.setValue(false); //uncheck parent
					}
				}
			}
		});
	}
}
