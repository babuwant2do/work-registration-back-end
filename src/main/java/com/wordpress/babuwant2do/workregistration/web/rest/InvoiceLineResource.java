package com.wordpress.babuwant2do.workregistration.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.wordpress.babuwant2do.workregistration.domain.InvoiceLine;
import com.wordpress.babuwant2do.workregistration.service.InvoiceLineService;
import com.wordpress.babuwant2do.workregistration.web.rest.util.HeaderUtil;
import com.wordpress.babuwant2do.workregistration.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InvoiceLine.
 */
@RestController
@RequestMapping("/api")
public class InvoiceLineResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceLineResource.class);

    private static final String ENTITY_NAME = "invoiceLine";

    private final InvoiceLineService invoiceLineService;

    public InvoiceLineResource(InvoiceLineService invoiceLineService) {
        this.invoiceLineService = invoiceLineService;
    }

    /**
     * POST  /invoice-lines : Create a new invoiceLine.
     *
     * @param invoiceLine the invoiceLine to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invoiceLine, or with status 400 (Bad Request) if the invoiceLine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invoice-lines")
    @Timed
    public ResponseEntity<InvoiceLine> createInvoiceLine(@Valid @RequestBody InvoiceLine invoiceLine) throws URISyntaxException {
        log.debug("REST request to save InvoiceLine : {}", invoiceLine);
        if (invoiceLine.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new invoiceLine cannot already have an ID")).body(null);
        }
        InvoiceLine result = invoiceLineService.save(invoiceLine);
        return ResponseEntity.created(new URI("/api/invoice-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invoice-lines : Updates an existing invoiceLine.
     *
     * @param invoiceLine the invoiceLine to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoiceLine,
     * or with status 400 (Bad Request) if the invoiceLine is not valid,
     * or with status 500 (Internal Server Error) if the invoiceLine couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoice-lines")
    @Timed
    public ResponseEntity<InvoiceLine> updateInvoiceLine(@Valid @RequestBody InvoiceLine invoiceLine) throws URISyntaxException {
        log.debug("REST request to update InvoiceLine : {}", invoiceLine);
        if (invoiceLine.getId() == null) {
            return createInvoiceLine(invoiceLine);
        }
        InvoiceLine result = invoiceLineService.save(invoiceLine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoiceLine.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoice-lines : get all the invoiceLines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of invoiceLines in body
     */
    @GetMapping("/invoice-lines")
    @Timed
    public ResponseEntity<List<InvoiceLine>> getAllInvoiceLines(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of InvoiceLines");
        Page<InvoiceLine> page = invoiceLineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/invoice-lines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /invoice-lines/:id : get the "id" invoiceLine.
     *
     * @param id the id of the invoiceLine to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoiceLine, or with status 404 (Not Found)
     */
    @GetMapping("/invoice-lines/{id}")
    @Timed
    public ResponseEntity<InvoiceLine> getInvoiceLine(@PathVariable Long id) {
        log.debug("REST request to get InvoiceLine : {}", id);
        InvoiceLine invoiceLine = invoiceLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(invoiceLine));
    }

    /**
     * DELETE  /invoice-lines/:id : delete the "id" invoiceLine.
     *
     * @param id the id of the invoiceLine to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoice-lines/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvoiceLine(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceLine : {}", id);
        invoiceLineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
