package com.reflect7.plansation.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
public class Iteration implements Serializable {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	public Key getKey() { return this.key; }
	public void setKey(Key key){ this.key = key; }
	
	@Persistent
	private Date startedAt;
	public Date getStartedAt() { return startedAt; }
	public void setStarted() { startedAt = new Date();}
	
	@Persistent
	private Date endedAt;
	public Date getEndedAt() { return endedAt; }
	public void setEnded() { endedAt = new Date();}
	
	@Persistent
	private Boolean hasEnded = false;
	public Boolean hasEnded() { return hasEnded; }
	public void setHasEnded(Boolean hasEnded) { this.hasEnded = hasEnded; }
	
	@Persistent
	private List<Requirement> requirements = new ArrayList<Requirement>();
	public List<Requirement> getRequirements() { return requirements; }
	
}
