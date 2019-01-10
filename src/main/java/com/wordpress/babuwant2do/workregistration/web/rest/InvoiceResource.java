package com.wordpress.babuwant2do.workregistration.web.rest;

import com.wordpress.babuwant2do.workregistration.domain.Invoice;
import com.wordpress.babuwant2do.workregistration.domain.InvoiceLine;
import com.wordpress.babuwant2do.workregistration.domain.InvoiceableTask;
import com.wordpress.babuwant2do.workregistration.domain.Project;
import com.wordpress.babuwant2do.workregistration.service.InvoiceLineService;
import com.wordpress.babuwant2do.workregistration.service.InvoiceService;
import com.wordpress.babuwant2do.workregistration.service.ProjectService;
import com.wordpress.babuwant2do.workregistration.service.TaskService;
import com.wordpress.babuwant2do.workregistration.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;

/**
 * REST controller for managing Invoice.
 */
@RestController
@RequestMapping("/api")
public class InvoiceResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceResource.class);

    private static final String ENTITY_NAME = "invoice";

    private final InvoiceService invoiceService;
    
    private final InvoiceLineService invoiceLineService;
    
    private final ProjectService projectService;
    
    private final TaskService taskService;

    public InvoiceResource(InvoiceService invoiceService, ProjectService projectService
    		, TaskService taskService, InvoiceLineService invoiceLineService) {
        this.invoiceService = invoiceService;
        this.projectService = projectService;
        this.taskService = taskService;
        this.invoiceLineService = invoiceLineService;
    }

    /**
     * POST  /invoices : Create a new invoice.
     *
     * @param invoice the invoice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invoice, or with status 400 (Bad Request) if the invoice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invoices")
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice) throws URISyntaxException {
        log.debug("REST request to save Invoice : {}", invoice);
        if (invoice.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new invoice cannot already have an ID")).body(null);
        }
        Invoice result = invoiceService.save(invoice);
        return ResponseEntity.created(new URI("/api/invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    

    /**
     * PUT  /invoices : Updates an existing invoice.
     *
     * @param invoice the invoice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoice,
     * or with status 400 (Bad Request) if the invoice is not valid,
     * or with status 500 (Internal Server Error) if the invoice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoices")
    public ResponseEntity<Invoice> updateInvoice(@Valid @RequestBody Invoice invoice) throws URISyntaxException {
        log.debug("REST request to update Invoice : {}", invoice);
        if (invoice.getId() == null) {
            return createInvoice(invoice);
        }
        Invoice result = invoiceService.save(invoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoices : get all the invoices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of invoices in body
     */
    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        log.debug("REST request to get a page of Invoices");
        List<Invoice> page = invoiceService.findAll();
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    /**
     * GET  /invoices/:id : get the "id" invoice.
     *
     * @param id the id of the invoice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoice, or with status 404 (Not Found)
     */
    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable Long id) {
        log.debug("REST request to get Invoice : {}", id);
        Invoice invoice = invoiceService.findOne(id);
        if(invoice != null){
        	return new ResponseEntity<Invoice>(invoice, HttpStatus.OK);        	
        }
        return new ResponseEntity<Invoice>(invoice, HttpStatus.NOT_FOUND);        	
        
    }
    
    @GetMapping("/invoices/{id}/lines")
    public ResponseEntity<List<InvoiceLine>> getAllInvoiceLines(@PathVariable Long id) {
        log.debug("REST request to get a page of InvoiceLines");
        List<InvoiceLine> page = invoiceLineService.findAllBiInvoiceId(id);
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }
    /**
     * DELETE  /invoices/:id : delete the "id" invoice.
     *
     * @param id the id of the invoice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        log.debug("REST request to delete Invoice : {}", id);
        invoiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/invoices/by-project-id/{projectId}")
    public ResponseEntity<List<Invoice>> getAllInvoicesByProjectId(@PathVariable Long projectId) {
        log.debug("REST request to get a page of Invoices");
        List<Invoice> page = invoiceService.findByProject(projectId);
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }
    
    
    @GetMapping("/invoices/create-by-project-id/{projectId}")
    public ResponseEntity<Invoice> createInvoiceByProjectId(@PathVariable Long projectId) {
        log.debug("REST request to get Invoice : {}", projectId);
        Invoice invoice = null;
        Project project = this.projectService.findOne(projectId);
    	if(project != null){
    		try {
				invoice = invoiceService.createInvoice(project);
				if(invoice != null){
					return new ResponseEntity<Invoice>(invoice, HttpStatus.OK);        	
				}
			} catch (Exception e) {
				return ResponseEntity.badRequest().header("errMsg", e.getMessage()).body(null);
			}
    		
    	}
        return new ResponseEntity<Invoice>(invoice, HttpStatus.BAD_REQUEST);        	
        
    }
    
    @PostMapping("/invoices/with-tasks-by-project-id/{projectId}")
    public ResponseEntity<Invoice> createInvoiceWithTasks(@PathVariable Long projectId, @RequestBody List<Long> selectedTaskIds) throws URISyntaxException {
    	log.debug("REST request to Create Invoice for project {} with Task IDs: {}", projectId, selectedTaskIds);
    	
    	Project project = this.projectService.findOne(projectId);
    	if(project != null){
    		List<InvoiceableTask> task = this.taskService.findAllById(selectedTaskIds);
    		if(task != null){
    			try {
					Invoice result = this.invoiceService.createInvoice(project, task, false);
					if(result != null){
						return ResponseEntity.created(new URI("/api/invoices/" + result.getId()))
								.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
								.body(result);						
					}
					
				} catch (Exception e) {
					return ResponseEntity.badRequest().header("errMsg", e.getMessage()).body(null);
				}
    		}
    	}
    	return ResponseEntity.badRequest().body(null);
    	
    }
}
