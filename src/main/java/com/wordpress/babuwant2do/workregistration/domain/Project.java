package com.wordpress.babuwant2do.workregistration.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.ProjectStatusEnum;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
public class Project  extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatusEnum status;
    @Column(name = "closed_date")
    private Instant closedDate;

    @ManyToOne(optional = false)
//    @NotNull
    private Client client;

    @ManyToOne(optional = false)
//    @NotNull
    private User owner;
    
    @OneToMany(mappedBy = "project", cascade=CascadeType.REMOVE)
    @JsonIgnore
    private List<Task> tasks = new ArrayList<>();

    public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getName() {
        return name;
    }

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStatusEnum getStatus() {
        return status;
    }

    public Project status(ProjectStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProjectStatusEnum status) {
        this.status = status;
    }
    public Instant getClosedDate() {
        return closedDate;
    }

    public Project closedDate(Instant closedDate) {
        this.closedDate = closedDate;
        return this;
    }

    public void setClosedDate(Instant closedDate) {
        this.closedDate = closedDate;
    }

    public Client getClient() {
        return client;
    }

    public Project client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getOwner() {
        return owner;
    }

    public Project owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", closedDate='" + getClosedDate() + "'" +
            "}";
    }
}
