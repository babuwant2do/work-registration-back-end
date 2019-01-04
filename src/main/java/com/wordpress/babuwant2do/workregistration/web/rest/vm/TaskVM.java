package com.wordpress.babuwant2do.workregistration.web.rest.vm;


import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.wordpress.babuwant2do.workregistration.domain.Invoice;
import com.wordpress.babuwant2do.workregistration.domain.Project;
import com.wordpress.babuwant2do.workregistration.domain.User;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.TaskStatusEnum;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.TaskTypeEnum;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.UniteTypeEnum;

public class TaskVM{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;

    @NotNull
    @Size(min = 3)
    private String name;

    private String description;

    @NotNull
    private TaskTypeEnum type;

    @NotNull
    private TaskStatusEnum status;

    private Instant createDate;

    @NotNull
    private Project project;

//	private TaskTypeEnum taskType;
	private Float quantity;
	private Float price;
	private UniteTypeEnum unit;
	private Invoice invoice;
	private List<ResourceVM> resources = new ArrayList<>();
	private Set<User> executors = new HashSet<>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public TaskTypeEnum getType() {
		return type;
	}
	public void setType(TaskTypeEnum type) {
		this.type = type;
	}
	public TaskStatusEnum getStatus() {
		return status;
	}
	public void setStatus(TaskStatusEnum status) {
		this.status = status;
	}
	public Instant getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Instant createDate) {
		this.createDate = createDate;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Float getQuantity() {
		return quantity;
	}
	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public UniteTypeEnum getUnit() {
		return unit;
	}
	public void setUnit(UniteTypeEnum unit) {
		this.unit = unit;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	public List<ResourceVM> getResources() {
		return resources;
	}
	public void setResources(List<ResourceVM> resources) {
		this.resources = resources;
	}
	public Set<User> getExecutors() {
		return executors;
	}
	public void setExecutors(Set<User> executors) {
		this.executors = executors;
	}	
	
	
	
}
