package com.wordpress.babuwant2do.workregistration.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WORKING_HOUR_RESOURCE")
public class WorkingHourResource extends Resource{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	private LocalDate executionDate;

	public LocalDate getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(LocalDate executionDate) {
		this.executionDate = executionDate;
	}
	

}
