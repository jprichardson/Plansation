package com.reflect7.plansation.client.remoteservice;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;
import com.reflect7.plansation.shared.model.Iteration;
import com.reflect7.plansation.shared.model.Task;

public interface IterationServiceAsync {
	void loadCurrentIteration(AsyncCallback<Iteration> callback) throws IllegalArgumentException;
	void saveIteration(Iteration iteration, AsyncCallback<Key<Iteration>> callback) throws IllegalArgumentException;
}
