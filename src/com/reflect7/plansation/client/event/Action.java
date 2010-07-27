package com.reflect7.plansation.client.event;

public interface Action<T> {
	public void execute(T object);
}
