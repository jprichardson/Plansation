package com.reflect7.plansation.server.remoteservice;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.reflect7.plansation.client.remoteservice.TaskService;
import com.reflect7.plansation.shared.model.Iteration;
import com.reflect7.plansation.shared.model.Task;
import com.reflect7.plansation.shared.model.User;

@SuppressWarnings("serial")
public class TaskServiceImpl extends RemoteServiceServlet implements TaskService {

	private static Boolean _objectifyInitialized = false;
	private static Objectify _ofy = null;
	
	public Iterable<Task> loadRootTasks() throws IllegalArgumentException {
		Objectify ofy = initObjectify();
		//QueryResultIterable<Task> results = ofy.query(Task.class).fetch();
		
		Query<Task> result = ofy.query(Task.class).filter("parent", "");
		
		List<Task> tasks = new ArrayList<Task>();
		for (Task t : result)
			tasks.add(t);
		
		return tasks;
	}
	
	public String saveTask(Task task) throws IllegalArgumentException {
		Objectify ofy = initObjectify();
		ofy.put(task);
		
		return null;
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
