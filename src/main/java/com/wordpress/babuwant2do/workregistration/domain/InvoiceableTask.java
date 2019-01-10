package com.wordpress.babuwant2do.workregistration.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public abstract class InvoiceableTask extends Task implements IInvoiceable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne//(cascade=CascadeType.DETACH)
    private Invoice invoice;
	
		
	 public Invoice getInvoice() {
	        return invoice;
	    }

	    public InvoiceableTask invoice(Invoice invoice) {
	        this.invoice = invoice;
	        return this;
	    }

	    public void setInvoice(Invoice invoice) {
	        this.invoice = invoice;
	    }
	    
	  //CHECK the calculation
		protected float caculateTotalPrice(Float unitPrice, Float quantity, Float taxPercent){
			Float valueIncTax = unitPrice * quantity;
			if(valueIncTax != 0 && taxPercent != 0){
				return ( valueIncTax* (100 + taxPercent) )/ 100;
			}
			return 0f;
		}
		
}
