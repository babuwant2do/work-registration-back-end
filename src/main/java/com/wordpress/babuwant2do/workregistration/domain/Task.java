package com.wordpress.babuwant2do.workregistration.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.wordpress.babuwant2do.workregistration.domain.enumeration.TaskStatusEnum;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "task_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("GENERAL_TASK")
public class Task extends BaseEntity {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

//    @NotNull
    @Column(name = "task_type", insertable = false, updatable = false)
    protected String type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatusEnum status;

//    @Column(name = "create_date")
//    private Instant createDate;

    @ManyToOne(optional = false)
    @NotNull
    private Project project;

    @ManyToMany
    @JoinTable(name = "task_executors",
               joinColumns = @JoinColumn(name="tasks_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="executors_id", referencedColumnName="id"))
    private Set<User> executors = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public Task name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

//    public Task type(String type) {
//        this.type = type;
//        return this;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

    public TaskStatusEnum getStatus() {
        return status;
    }

    public Task status(TaskStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }

//    public Instant getCreateDate() {
//        return createDate;
//    }
//
//    public Task createDate(Instant createDate) {
//        this.createDate = createDate;
//        return this;
//    }
//
//    public void setCreateDate(Instant createDate) {
//        this.createDate = createDate;
//    }

    public Project getProject() {
        return project;
    }

    public Task project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<User> getExecutors() {
        return executors;
    }

    public Task executors(Set<User> users) {
        this.executors = users;
        return this;
    }

    public Task addExecutors(User user) {
        this.executors.add(user);
        // user.getTasks().add(this);
        return this;
    }

    public Task removeExecutors(User user) {
        this.executors.remove(user);
        // user.getTasks().remove(this);
        return this;
    }

    public void setExecutors(Set<User> users) {
        this.executors = users;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        if (task.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            "}";
    }
}
