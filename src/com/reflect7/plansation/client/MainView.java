package com.reflect7.plansation.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite {

	private static MainViewUiBinder uiBinder = GWT
			.create(MainViewUiBinder.class);

	interface MainViewUiBinder extends UiBinder<Widget, MainView> {}

	@UiField SplitLayoutPanel splitterPanel;
	@UiField Tree treeTasks;
	@UiField TextBox textTask;
	
	
	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void onAttach(){
		super.onAttach();
		
		//hack until GWT Team fixes Issue 4384/4417
		Element splitterElement = splitterPanel.getElement();
		NodeList<Node> nodes = splitterElement.getChildNodes();
		//Window.alert(nodes.getLength() + "");
		for (int x = 0; x < nodes.getLength(); ++x)
			if (nodes.getItem(x) instanceof Element){
				Element e = (Element)nodes.getItem(x);
				String s = e.getFirstChildElement().getClassName();
				//Window.alert(s);
				if (s != null)
					if (s.equals("gwt-SplitLayoutPanel-HDragger") || s.equals("gwt-SplitLayoutPanel-VDragger")){
						e.getFirstChildElement().getStyle().clearBackgroundColor();
					}
						
			}	
	}
	
	@UiHandler("textTask")
	void handleChange(ChangeEvent e){
		
	}
	
	@UiHandler("textTask")
	void handleKeyPressf(KeyPressEvent e){
		byte b = (byte)e.getCharCode();
		if (b == 13){//[ENTER] key pressed
			String task = textTask.getText().trim();
			TreeItem newItem = new TreeItem(task);
			
			TreeItem selectedItem = treeTasks.getSelectedItem();
			if (selectedItem != null)
				selectedItem.addItem(newItem);
			else
				treeTasks.addItem(newItem);
			
			textTask.setText("");
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
}
