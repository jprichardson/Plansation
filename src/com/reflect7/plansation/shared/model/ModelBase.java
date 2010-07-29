package com.reflect7.plansation.shared.model;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;

public abstract class ModelBase {
	@PrePersist
	protected void beforePersist(){}
	
	@PostLoad
	protected void afterLoad(){}
}
