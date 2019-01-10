package com.wordpress.babuwant2do.workregistration.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

import com.wordpress.babuwant2do.workregistration.domain.enumeration.UniteTypeEnum;

/**
 * A Resource.
 */
@Entity
@Table(name = "resource")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "resource_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("GENERAL_RESOURCE")
public class Resource  extends BaseEntity {

    private static final long serialVersionUID = 1L;

//    @NotNull
    @Column(name = "resource_type", insertable = false, updatable = false)
    protected String type;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "unit_qty", nullable = false)
    private Float unitQty;

//    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_type")
    private UniteTypeEnum unitType;


    @ManyToOne(optional = false)
    @NotNull
    private Task task;

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Resource description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getUnitQty() {
        return unitQty;
    }

    public Resource unitQty(Float unitQty) {
        this.unitQty = unitQty;
        return this;
    }

    public void setUnitQty(Float unitQty) {
        this.unitQty = unitQty;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public Resource unitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public UniteTypeEnum getUnitType() {
        return unitType;
    }

    public Resource unitType(UniteTypeEnum unitType) {
        this.unitType = unitType;
        return this;
    }

    public void setUnitType(UniteTypeEnum unitType) {
        this.unitType = unitType;
    }

    public Task getTask() {
        return task;
    }

    public Resource task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resource resource = (Resource) o;
        if (resource.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resource.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resource{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", unitQty='" + getUnitQty() + "'" +
            ", unitPrice='" + getUnitPrice() + "'" +
            ", unitType='" + getUnitType() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            "}";
    }
}
