package com.reflect7.plansation.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.reflect7.plansation.shared.model.Task;

public class ListTaskPanel extends Composite {
	
	private static ListTaskPanelUiBinder uiBinder = GWT
			.create(ListTaskPanelUiBinder.class);

	interface ListTaskPanelUiBinder extends UiBinder<Widget, ListTaskPanel> {
	}
	
	@UiField ScrollPanel scrollPanel;
	@UiField VerticalPanel verticalPanel;
	
	public ListTaskPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void addTask(Task task){
		verticalPanel.add(new HTML(task.name));
	}
	
}
