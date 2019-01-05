package com.wordpress.babuwant2do.workregistration.web.rest;

import com.wordpress.babuwant2do.workregistration.domain.Project;
import com.wordpress.babuwant2do.workregistration.domain.Resource;
import com.wordpress.babuwant2do.workregistration.service.ResourceService;
import com.wordpress.babuwant2do.workregistration.web.rest.helper.ResourceBuilder;
import com.wordpress.babuwant2do.workregistration.web.rest.util.HeaderUtil;
import com.wordpress.babuwant2do.workregistration.web.rest.util.PaginationUtil;
import com.wordpress.babuwant2do.workregistration.web.rest.vm.ResourceVM;

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
 * REST controller for managing Resource.
 */
@RestController
@RequestMapping("/api")
public class ResourceResource {

    private final Logger log = LoggerFactory.getLogger(ResourceResource.class);

    private static final String ENTITY_NAME = "resource";

    private final ResourceService resourceService;
    
    private final ResourceBuilder resourceBuilder;

    public ResourceResource(ResourceService resourceService, ResourceBuilder resourceBuilder) {
        this.resourceService = resourceService;
        this.resourceBuilder = resourceBuilder;
    }

    /**
     * POST  /resources : Create a new resource.
     *
     * @param resourceVM the resource to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resource, or with status 400 (Bad Request) if the resource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resources")
    public ResponseEntity<Resource> createResource(@Valid @RequestBody ResourceVM resourceVM) throws URISyntaxException {
        log.debug("REST request to save Resource : {}", resourceVM);
        if (resourceVM.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new resource cannot already have an ID")).body(null);
        }
        Resource resource = this.resourceBuilder.getResource(resourceVM);
        Resource result = resourceService.save(resource);
        return ResponseEntity.created(new URI("/api/resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resources : Updates an existing resource.
     *
     * @param resourceVM the resource to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resource,
     * or with status 400 (Bad Request) if the resource is not valid,
     * or with status 500 (Internal Server Error) if the resource couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resources")
    public ResponseEntity<Resource> updateResource(@Valid @RequestBody ResourceVM resourceVM) throws URISyntaxException {
        log.debug("REST request to update Resource : {}", resourceVM);
        if (resourceVM.getId() == null) {
            return createResource(resourceVM);
        }
        Resource resource = this.resourceBuilder.getResource(resourceVM);
        
        Resource result = resourceService.save(resource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resourceVM.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resources : get all the resources.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of resources in body
     */
    @GetMapping("/resources")
    public ResponseEntity<List<Resource>> getAllResources() {
        log.debug("REST request to get a page of Resources");
        List<Resource> page = resourceService.findAll();
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resources");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    /**
     * GET  /resources/:id : get the "id" resource.
     *
     * @param id the id of the resource to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resource, or with status 404 (Not Found)
     */
    @GetMapping("/resources/{id}")
    public ResponseEntity<Resource> getResource(@PathVariable Long id) {
        log.debug("REST request to get Resource : {}", id);
        Resource resource = resourceService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(resource));
        if(resource != null){
        	return new ResponseEntity<Resource>(resource, HttpStatus.OK);        	
        }
        return new ResponseEntity<Resource>(resource, HttpStatus.NOT_FOUND);  
    }

    /**
     * DELETE  /resources/:id : delete the "id" resource.
     *
     * @param id the id of the resource to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resources/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        log.debug("REST request to delete Resource : {}", id);
        resourceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
