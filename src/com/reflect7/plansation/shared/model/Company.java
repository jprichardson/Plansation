package com.reflect7.plansation.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

//@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Company implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	public Key getKey() { return this.key; }
	public void setKey(Key key){ this.key = key; }
	
	@Persistent
	private String name;
	public String getName(){ return name; }
	public void setName(String name) { this.name = name; }
	
	@Persistent
	private List<User> users = new ArrayList<User>();
	public List<User> getUser() { return users; }
}
