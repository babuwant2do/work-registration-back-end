package com.wordpress.babuwant2do.workregistration.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.UniteTypeEnum;

@Entity
@DiscriminatorValue("INVOICABLE_RESOURCE_BASED_TASK")
public class ResourceBasedInvoicableTask extends InvoiceableTask{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "task")
    @JsonIgnore
    private List<Resource> resources = new ArrayList<>();

    public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	
	public void addResource(Resource resource){
		this.resources.add(resource);
		resource.setTask(this);
	}

	@Override
	@JsonIgnore
	public List<InvoiceLine> getInvoiceLineAsList() {
		Map<UniteTypeEnum, InvoiceLine> resourceSummary = new HashMap<>();
		InvoiceLine invoiceLine;
		for (Resource resource : resources) {
			if(resourceSummary.containsKey(resource.getUnitType())){
				invoiceLine = resourceSummary.get(resource.getUnitType());
				invoiceLine.setQuantity(invoiceLine.getQuantity() + resource.getUnitQty());
			}else{
				invoiceLine = new InvoiceLine();
				invoiceLine.setDetails(String.format("%s : %s", this.getName(), this.getDescription()));
				invoiceLine.setTaxPercent(2.5f); //TODO: set from property: maybe global property
				invoiceLine.setQuantity(resource.getUnitQty());
				invoiceLine.setUnitPrice(resource.getUnitPrice());
				invoiceLine.setUniteType(resource.getUnitType());
				//TODO: calculate price inc Vat after sum up similar kind of resource
//			invoiceLine.setTotalPrice(this.caculateTotalPrice(invoiceLine.getUnitPrice(), invoiceLine.getQuantity(), invoiceLine.getTaxPercent()));
				
				resourceSummary.put(resource.getUnitType(), invoiceLine);
			}
		}
		
		List<InvoiceLine> invoiceLineList = new ArrayList<>();
		for (InvoiceLine invoiceL : resourceSummary.values()) {
			invoiceL.setTotalPrice(this.caculateTotalPrice(invoiceL.getUnitPrice(), invoiceL.getQuantity(), invoiceL.getTaxPercent()));
			invoiceLineList.add(invoiceL);
		}
		
		return invoiceLineList;
	}
}
