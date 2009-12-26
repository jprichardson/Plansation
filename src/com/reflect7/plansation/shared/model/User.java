package com.reflect7.plansation.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

//@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class User implements Serializable {
	
	private enum NameStyle {FirstLast, LastFirst};
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String email;
	public String getEmail() { return this.email; }
	public void setEmail(String email){ this.email = email; }
	
	@Persistent
	private String firstName;
	public String getFirstName(){ return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	@Persistent
	private String lastName;
	public String getLastName(){ return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	public String getWholeName(NameStyle ns) {
		if (ns == NameStyle.FirstLast)
			return firstName + " " + lastName;
		else if (ns == NameStyle.LastFirst)
			return lastName + ", " + firstName;
		
		return "";
	}
	
	@Persistent
	private List<Task> tasks = new ArrayList<Task>();
	public List<Task> getTasks() { return tasks; }
}
