package com.wordpress.babuwant2do.workregistration.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

import com.wordpress.babuwant2do.workregistration.domain.enumeration.UniteTypeEnum;

/**
 * A InvoiceLine.
 */
@Entity
@Table(name = "invoice_line")
public class InvoiceLine  extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "details", nullable = false)
    private String details;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Float quantity;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    @Column(name = "tax_percent")
    private Float taxPercent;

    @Column(name = "total_price")
    private Float totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "unite_type")
    private UniteTypeEnum uniteType;

    @ManyToOne(optional = false)
    @NotNull
    private Invoice invoice;

    public String getDetails() {
        return details;
    }

    public InvoiceLine details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Float getQuantity() {
        return quantity;
    }

    public InvoiceLine quantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public InvoiceLine unitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getTaxPercent() {
        return taxPercent;
    }

    public InvoiceLine taxPercent(Float taxPercent) {
        this.taxPercent = taxPercent;
        return this;
    }

    public void setTaxPercent(Float taxPercent) {
        this.taxPercent = taxPercent;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public InvoiceLine totalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UniteTypeEnum getUniteType() {
        return uniteType;
    }

    public InvoiceLine uniteType(UniteTypeEnum uniteType) {
        this.uniteType = uniteType;
        return this;
    }

    public void setUniteType(UniteTypeEnum uniteType) {
        this.uniteType = uniteType;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public InvoiceLine invoice(Invoice invoice) {
        this.invoice = invoice;
        return this;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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
        InvoiceLine invoiceLine = (InvoiceLine) o;
        if (invoiceLine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoiceLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoiceLine{" +
            "id=" + getId() +
            ", details='" + getDetails() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", unitPrice='" + getUnitPrice() + "'" +
            ", taxPercent='" + getTaxPercent() + "'" +
            ", totalPrice='" + getTotalPrice() + "'" +
            ", uniteType='" + getUniteType() + "'" +
            "}";
    }
}
