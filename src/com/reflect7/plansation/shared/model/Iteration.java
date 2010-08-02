package com.reflect7.plansation.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.reflect7.commongwt.client.util.DateTimeUtil;


@SuppressWarnings("serial")
public class Iteration extends ModelBase implements Serializable {
	
	public Iteration() {};
	
	public @Id Long id;
	
	public long startedAt;
		public Date getDateStartedAt(){
			Date d = new Date();
			d.setTime(this.startedAt);
			return d;
		}
		
		public String getDateTimeStartedAt(){ //CLIENT ONLY
			return DateTimeUtil.getShortDateTime(this.getDateStartedAt());
		}
	
	public long endedAt;
		public Date getDateEndedAt(){
			Date d = new Date();
			d.setTime(this.endedAt);
			return d;
		}
		
		public String getDateTimeEndedAt(){ //CLIENT ONLY
			return DateTimeUtil.getShortDateTime(this.getDateEndedAt());
		}
	
	boolean hasStarted = false;
	boolean hasEnded = false;
	
	@Override //SERVER ONLY
	protected void beforePersist(){
		if (this.startedAt == 0 && hasStarted)
			this.startedAt = (new Date()).getTime();
		
		if (this.endedAt == 0 && hasEnded)
			this.endedAt = (new Date()).getTime();
	}
}
