package com.reflect7.plansation.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.reflect7.plansation.client.view.model.TreeTaskPanel;
import com.reflect7.plansation.shared.model.Task;

public class IterationPanel extends Composite {

	private static IterationPanelUiBinder uiBinder = GWT.create(IterationPanelUiBinder.class);
	interface IterationPanelUiBinder extends UiBinder<Widget, IterationPanel> {}
	
	@UiField ListTaskPanel listTaskPanel;
	TreeTaskPanel treeTaskPanel;

	public IterationPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setTreeTaskPanel(TreeTaskPanel ttp){
		treeTaskPanel = ttp;
	}
	
	@UiHandler("buttonAddTask")
	void handleClick(ClickEvent e){
		Task selectedTask = treeTaskPanel.getSelectedTask();
		if (selectedTask == null)
			Window.alert("You must select a task before you can add it.");
		else {
			//listTaskPanel.addTask(treeTaskPanel.getSelectedTask());
			listTaskPanel.addTreeItem(treeTaskPanel.getSelectedItem());
		}
	}

}
