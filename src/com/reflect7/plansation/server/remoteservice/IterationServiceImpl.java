package com.reflect7.plansation.server.remoteservice;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.reflect7.plansation.client.remoteservice.IterationService;
import com.reflect7.plansation.shared.model.Iteration;
import com.reflect7.plansation.shared.model.Task;
import com.reflect7.plansation.shared.model.User;

@SuppressWarnings("serial")
public class IterationServiceImpl extends RemoteServiceServlet implements IterationService {
	
	private static boolean _objectifyInitialized = false;
	private static Objectify _ofy = null;
	
	public Iteration loadCurrentIteration() throws IllegalArgumentException {
		Objectify ofy = initObjectify();
		Query<Iteration> query = ofy.query(Iteration.class).filter("hasEnded", false).limit(1);
		
		Iteration iteration = null;
		if (query.countAll() == 0){
			iteration = new Iteration();
			iteration.id = ofy.put(iteration).getId();
		} else
			iteration = query.get();
		
		return iteration;
	}
	
	public Key<Iteration> saveIteration(Iteration iteration) throws IllegalArgumentException {
		Objectify ofy = initObjectify();
		Key<Iteration> key = ofy.put(iteration);
		return key;
	}
	
	private static Objectify initObjectify(){
		if (!_objectifyInitialized){
			ObjectifyService.register(Task.class);
			ObjectifyService.register(Iteration.class);
			ObjectifyService.register(User.class);
			
			_ofy = ObjectifyService.begin();
			_objectifyInitialized = true;
		}
		
		return _ofy;
	}
}
