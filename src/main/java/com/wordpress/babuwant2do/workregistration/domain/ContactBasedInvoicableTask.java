package com.wordpress.babuwant2do.workregistration.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.UniteTypeEnum;

@Entity
@DiscriminatorValue("INVOICABLE_CONTACT_BASED_TASK")
public class ContactBasedInvoicableTask extends InvoiceableTask{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private Float quantity;
	@Column
	private Float price;
	
	 @Enumerated(EnumType.STRING)
	 @Column(name = "unit")
	private UniteTypeEnum unit;
	
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
	@Override
	@JsonIgnore
	public List<InvoiceLine> getInvoiceLineAsList() {
		List<InvoiceLine> invoiceLines = new ArrayList<>();

		InvoiceLine invoiceLine = new InvoiceLine();
		invoiceLine.setDetails(String.format("%s : %s", this.getName(), this.getDescription()));
		invoiceLine.setTaxPercent(1.5f); //TODO: set from property: maybe global property
		invoiceLine.setQuantity(this.getQuantity());
		invoiceLine.setUnitPrice(this.getPrice());
		invoiceLine.setUniteType(UniteTypeEnum.PC);
		invoiceLine.setTotalPrice(this.caculateTotalPrice(invoiceLine.getUnitPrice(), invoiceLine.getQuantity(), invoiceLine.getTaxPercent()));
		
		invoiceLines.add(invoiceLine);
		
		return invoiceLines;
	}
	
	
	
}
