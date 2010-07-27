package com.reflect7.plansation.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;


@SuppressWarnings("serial")
public class Iteration implements Serializable {
	
	public Iteration() {};
	
	@Id Long id;
	Date startedAt;
	Date endedAt;
	Boolean hasEnded = false;

	@Embedded List<Key<Task>> tasks = new ArrayList<Key<Task>>();
	@Embedded List<Key<User>> users = new ArrayList<Key<User>>();
}
