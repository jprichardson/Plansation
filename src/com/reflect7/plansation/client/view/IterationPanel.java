package com.reflect7.plansation.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class IterationPanel extends Composite {

	private static IterationPanelUiBinder uiBinder = GWT
			.create(IterationPanelUiBinder.class);

	interface IterationPanelUiBinder extends UiBinder<Widget, IterationPanel> {
	}

	public IterationPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
