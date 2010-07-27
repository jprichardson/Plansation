package com.reflect7.plansation.client.widget;

public class TextBox extends com.google.gwt.user.client.ui.TextBox {
	
	public String getPlaceholder(){
		return this.getElement().getAttribute("placeholder");
	}
	
	public void setPlaceholder(String text){
		this.getElement().setAttribute("placeholder", text);
	}
	
	public void setAutoFocus(Boolean value){
		if (value)
			this.getElement().setAttribute("autofocus", null);
		else
			this.getElement().removeAttribute("autofocus");
	}
}
