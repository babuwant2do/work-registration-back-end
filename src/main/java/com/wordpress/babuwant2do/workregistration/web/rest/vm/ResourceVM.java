package com.wordpress.babuwant2do.workregistration.web.rest.vm;

import java.time.LocalDate;

import com.wordpress.babuwant2do.workregistration.domain.Resource;

public class ResourceVM extends Resource{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalDate executionDate;

	public LocalDate getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(LocalDate executionDate) {
		this.executionDate = executionDate;
	}

}
