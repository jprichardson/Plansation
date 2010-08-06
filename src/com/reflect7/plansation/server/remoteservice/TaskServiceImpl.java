package com.reflect7.plansation.server.remoteservice;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.reflect7.plansation.client.remoteservice.TaskService;
import com.reflect7.plansation.shared.model.Iteration;
import com.reflect7.plansation.shared.model.Task;
import com.reflect7.plansation.shared.model.User;

@SuppressWarnings("serial")
public class TaskServiceImpl extends RemoteServiceServlet implements TaskService {

	private static boolean _objectifyInitialized = false;
	private static Objectify _ofy = null;
	
	public List<Task> loadRootTasks() throws IllegalArgumentException {
		Objectify ofy = initObjectify();
		//QueryResultIterable<Task> results = ofy.query(Task.class).fetch();
		
		Query<Task> result = ofy.query(Task.class).filter("parent", null);
		
		List<Task> tasks = new ArrayList<Task>();
		for (Task t : result)
			tasks.add(t);
		
		return tasks;
	}
	
	public List<Task> loadChildTasks(Task parent) throws IllegalArgumentException {
		Objectify ofy = initObjectify();
		
		Query<Task> result = ofy.query(Task.class).filter("parent", parent);
		
		List<Task> tasks = new ArrayList<Task>();
		for (Task t : result)
			tasks.add(t);
		
		return tasks;
	}
	
	public Key<Task> saveTask(Task task) throws IllegalArgumentException {
		Objectify ofy = initObjectify();
		Key<Task> key = ofy.put(task);
		
		return key;
	}
	
	public List<Key<Task>> saveTask(Task parent, Task child) throws IllegalArgumentException {
		Objectify ofy = initObjectify();
		Key<Task> parentKey = ofy.put(parent);
		child.parent = parentKey;
		
		if (parent.root == null)
			child.root = parentKey;
		else
			child.root = parent.root;
		
		Key<Task> childKey = ofy.put(child);
		
		List<Key<Task>> keys = new ArrayList<Key<Task>>();
		keys.add(child.root);
		keys.add(parentKey);
		keys.add(childKey);
		
		return keys;
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
