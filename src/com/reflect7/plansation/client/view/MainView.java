package com.reflect7.plansation.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.reflect7.plansation.client.view.model.TreeTaskPanel;
import com.reflect7.plansation.shared.model.Task;

public class MainView extends Composite {

	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);
	interface MainViewUiBinder extends UiBinder<Widget, MainView> {}

	@UiField SplitLayoutPanel splitterPanel;
	@UiField TreeTaskPanel treeTaskPanel;
	@UiField IterationPanel iterationPanel;


	//@UiField ListBox listTasks;
	
	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
		iterationPanel.setTreeTaskPanel(treeTaskPanel);
		
		//this.addDomHandler(splitterPanel, ResizeEvent.getType());
		
	}
	
	@Override protected void onAttach(){
		super.onAttach();
		splitterPanel.forceLayout();
	}
}
