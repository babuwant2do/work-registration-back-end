package com.wordpress.babuwant2do.workregistration.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wordpress.babuwant2do.workregistration.domain.InvoiceLine;
import com.wordpress.babuwant2do.workregistration.repository.InvoiceLineRepository;


/**
 * Service Implementation for managing InvoiceLine.
 */
@Service
@Transactional
public class InvoiceLineService {

    private final Logger log = LoggerFactory.getLogger(InvoiceLineService.class);

    private final InvoiceLineRepository invoiceLineRepository;
    public InvoiceLineService(InvoiceLineRepository invoiceLineRepository) {
        this.invoiceLineRepository = invoiceLineRepository;
    }

    /**
     * Save a invoiceLine.
     *
     * @param invoiceLine the entity to save
     * @return the persisted entity
     */
    public InvoiceLine save(InvoiceLine invoiceLine) {
        log.debug("Request to save InvoiceLine : {}", invoiceLine);
        return invoiceLineRepository.save(invoiceLine);
    }

    /**
     *  Get all the invoiceLines.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InvoiceLine> findAll(Pageable pageable) {
        log.debug("Request to get all InvoiceLines");
        return invoiceLineRepository.findAll(pageable);
    }
    
    @Transactional(readOnly = true)
    public List<InvoiceLine> findAllBiInvoiceId(Long invoiceId) {
    	log.debug("Request to get all InvoiceLines");
    	return invoiceLineRepository.findByInvoiceId(invoiceId);
    }
    
    @Transactional(readOnly = true)
    public List<InvoiceLine> findAll() {
    	log.debug("Request to get all InvoiceLines");
    	return invoiceLineRepository.findAll();
    }

    /**
     *  Get one invoiceLine by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public InvoiceLine findOne(Long id) {
        log.debug("Request to get InvoiceLine : {}", id);
//        return invoiceLineRepository.getOne(id);
        return invoiceLineRepository.findById(id).orElse(null);
    }

    /**
     *  Delete the  invoiceLine by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InvoiceLine : {}", id);
        invoiceLineRepository.deleteById(id);
    }
}
