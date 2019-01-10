package com.wordpress.babuwant2do.workregistration.repository;

import com.wordpress.babuwant2do.workregistration.domain.Invoice;
import com.wordpress.babuwant2do.workregistration.domain.Task;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Invoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	
	List<Invoice> findByProjectId(Long id);
    Page<Invoice> findByProjectId(Long id, Pageable pageable);
}
