package com.wordpress.babuwant2do.workregistration.service;



import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wordpress.babuwant2do.workregistration.domain.Invoice;
import com.wordpress.babuwant2do.workregistration.domain.InvoiceLine;
import com.wordpress.babuwant2do.workregistration.domain.InvoiceableTask;
import com.wordpress.babuwant2do.workregistration.domain.Project;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.InvoiceStatusEnum;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.TaskStatusEnum;
import com.wordpress.babuwant2do.workregistration.repository.InvoiceRepository;
import com.wordpress.babuwant2do.workregistration.repository.InvoiceableTaskRepository;


/**
 * Service Implementation for managing Invoice.
 */
@Service
@Transactional
public class InvoiceService {

    private final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    private final InvoiceRepository invoiceRepository;
    private final InvoiceableTaskRepository invoiceableTaskRepository;
    
    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceableTaskRepository invoiceableTaskRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceableTaskRepository = invoiceableTaskRepository;
    }

    /**
     * Save a invoice.
     *
     * @param invoice the entity to save
     * @return the persisted entity
     */
    public Invoice save(Invoice invoice) {
        log.debug("Request to save Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
    }

    /**
     *  Get all the invoices.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Invoice> findAll(Pageable pageable) {
        log.debug("Request to get all Invoices");
        return invoiceRepository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public List<Invoice> findAll() {
    	log.debug("Request to get all Invoices");
    	return invoiceRepository.findAll();
    }

    /**
     *  Get one invoice by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Invoice findOne(Long id) {
        log.debug("Request to get Invoice : {}", id);
//        return invoiceRepository.getOne(id);
        return invoiceRepository.findById(id).orElse(null);
    }

    /**
     *  Delete the  invoice by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Invoice : {}", id);
        Invoice invoice = this.findOne(id);
        for (InvoiceableTask invoiceableTask : invoice.getTasks()) {
			invoiceableTask.setInvoice(null);
			invoiceableTask.setStatus(TaskStatusEnum.FINISHED);
		}
        
        invoiceRepository.deleteById(id);
    }
    
    //TODO: Refactor this method
    public Invoice createInvoice(Project project){
    	log.debug("Request to get Create Invoice By   Project : {}", project.getId());
    	List<InvoiceableTask> task = this.invoiceableTaskRepository.findByProjectIdAndStatusWithEagerRelationships(project.getId(), TaskStatusEnum.FINISHED);
    	if(task != null && !task.isEmpty()){
    		Invoice invoice = this.prepareNewInvoceProject(project);
    		for (InvoiceableTask invoiceableTask : task) {
    			this.log.info("ProjectID: {}, Task ID: {} , Task Name: {}", invoiceableTask.getProject().getId(), invoiceableTask.getId(), invoiceableTask.getName());
    			List<InvoiceLine> taskInvoiceLIne = invoiceableTask.getInvoiceLineAsList();
    			for (InvoiceLine invoiceLine : taskInvoiceLIne) {
					this.log.warn("type: {}, price: {}, totPrice: {}", invoiceLine.getUniteType(), invoiceLine.getUnitPrice(), invoiceLine.getTotalPrice());
					invoiceLine.setInvoice(invoice);
				}
    			invoice.getInvoiceLines().addAll(taskInvoiceLIne);
    			invoice.addTask(invoiceableTask);
    		}
    		return this.save(invoice);
    	}
    	return null;
    }
    
    /**
     * prepare invoice for project , not saved, only created
     * @param project
     * @return
     */
    private Invoice prepareNewInvoceProject(Project project){
    	Invoice invoice = new Invoice();
    	
		invoice.setName(project.getId()+"_"+project.getName()+"_"+ new Date().getTime());
		invoice.setStatus(InvoiceStatusEnum.CREATED);
		invoice.setAdderss(project.getClient().getAddress());
		invoice.setProject(project);
    	
		return invoice;
    }
}
