package com.reflect7.plansation.client.remoteservice;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.googlecode.objectify.Key;
import com.reflect7.plansation.shared.model.Iteration;

@RemoteServiceRelativePath("iteration")
public interface IterationService extends RemoteService{
	public Iteration loadCurrentIteration() throws IllegalArgumentException;
	public Key<Iteration> saveIteration(Iteration iteration) throws IllegalArgumentException;
}
