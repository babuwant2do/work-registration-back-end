package com.wordpress.babuwant2do.workregistration.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.InvoiceStatusEnum;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.TaskStatusEnum;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
public class Invoice extends BaseEntity {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatusEnum status;

//    @Column(name = "create_date")
//    private Instant createDate;

    @Column(name = "sent_date")
    private Instant sentDate;

    @Column(name = "feedback_date")
    private Instant feedbackDate;

    @NotNull
    @Column(name = "adderss", nullable = false)
    private String adderss;

    @ManyToOne(optional = false)
    @NotNull
    private Project project;
    
    @OneToMany(mappedBy = "invoice", cascade=CascadeType.ALL)
    @JsonIgnore
    private List<InvoiceLine> invoiceLines = new ArrayList<>();

    @OneToMany(mappedBy = "invoice", cascade=CascadeType.PERSIST)
    @JsonIgnore
    private List<InvoiceableTask> tasks = new ArrayList<>();


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

    public Invoice name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InvoiceStatusEnum getStatus() {
        return status;
    }

    public Invoice status(InvoiceStatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(InvoiceStatusEnum status) {
        this.status = status;
    }

//    public Instant getCreateDate() {
//        return createDate;
//    }
//
//    public Invoice createDate(Instant createDate) {
//        this.createDate = createDate;
//        return this;
//    }
//
//    public void setCreateDate(Instant createDate) {
//        this.createDate = createDate;
//    }

    public Instant getSentDate() {
        return sentDate;
    }

    public Invoice sentDate(Instant sentDate) {
        this.sentDate = sentDate;
        return this;
    }

    public void setSentDate(Instant sentDate) {
        this.sentDate = sentDate;
    }

    public Instant getFeedbackDate() {
        return feedbackDate;
    }

    public Invoice feedbackDate(Instant feedbackDate) {
        this.feedbackDate = feedbackDate;
        return this;
    }

    public void setFeedbackDate(Instant feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getAdderss() {
        return adderss;
    }

    public Invoice adderss(String adderss) {
        this.adderss = adderss;
        return this;
    }

    public void setAdderss(String adderss) {
        this.adderss = adderss;
    }

    public Project getProject() {
        return project;
    }

    public Invoice project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    public List<InvoiceLine> getInvoiceLines() {
		return invoiceLines;
	}

	public void setInvoiceLines(List<InvoiceLine> invoiceLines) {
		this.invoiceLines = invoiceLines;
	}

	
	
	

	public List<InvoiceableTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<InvoiceableTask> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(InvoiceableTask invoiceableTask) {
		invoiceableTask.setInvoice(this);
		invoiceableTask.setStatus(TaskStatusEnum.INVOICED);
		this.tasks.add(invoiceableTask);
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Invoice invoice = (Invoice) o;
        if (invoice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", sentDate='" + getSentDate() + "'" +
            ", feedbackDate='" + getFeedbackDate() + "'" +
            ", adderss='" + getAdderss() + "'" +
            "}";
    }
}
