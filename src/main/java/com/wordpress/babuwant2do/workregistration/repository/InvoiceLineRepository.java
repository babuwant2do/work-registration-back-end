package com.wordpress.babuwant2do.workregistration.repository;

import com.wordpress.babuwant2do.workregistration.domain.InvoiceLine;
import com.wordpress.babuwant2do.workregistration.domain.InvoiceableTask;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InvoiceLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Long> {
	List<InvoiceLine> findByInvoiceId(Long invoiceId);
}
