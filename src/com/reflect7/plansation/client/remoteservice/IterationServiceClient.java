package com.reflect7.plansation.client.remoteservice;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;
import com.reflect7.commongwt.client.event.Action;
import com.reflect7.plansation.shared.model.Iteration;

public class IterationServiceClient {

private IterationServiceAsync _service;
	
	public IterationServiceClient(IterationServiceAsync service){
		_service = service;
	}
	
	public void loadCurrentIteration(final Action<Iteration> action){
		_service.loadCurrentIteration(new AsyncCallback<Iteration>(){
			@Override public void onFailure(Throwable caught) {
				Window.alert("Load Current Iteration - Failure");
			}

			@Override public void onSuccess(Iteration result) {
				if (action != null)
					action.execute(result);
			}
		});
	}
	
	public void saveIteration(final Iteration iteration, final Action<Key<Iteration>> action){
		_service.saveIteration(iteration, new AsyncCallback<Key<Iteration>>(){
			@Override public void onFailure(Throwable caught) {
				Window.alert("Save Iteration - Failure");
			}

			@Override public void onSuccess(Key<Iteration> result) {
				iteration.id = result.getId();
				if (action != null)
					action.execute(result);
			}
		});
	}
}
