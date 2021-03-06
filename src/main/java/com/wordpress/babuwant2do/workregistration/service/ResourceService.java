package com.wordpress.babuwant2do.workregistration.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wordpress.babuwant2do.workregistration.domain.Resource;
import com.wordpress.babuwant2do.workregistration.repository.ResourceRepository;


/**
 * Service Implementation for managing Resource.
 */
@Service
@Transactional
public class ResourceService {

    private final Logger log = LoggerFactory.getLogger(ResourceService.class);

    private final ResourceRepository resourceRepository;
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    /**
     * Save a resource.
     *
     * @param resource the entity to save
     * @return the persisted entity
     */
    public Resource save(Resource resource) {
        log.debug("Request to save Resource : {}", resource);
        return resourceRepository.save(resource);
    }

    /**
     *  Get all the resources.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Resource> findAll(Pageable pageable) {
        log.debug("Request to get all Resources");
        return resourceRepository.findAll(pageable);
    }
    
    @Transactional(readOnly = true)
    public List<Resource> findAll() {
    	log.debug("Request to get all Resources");
    	return resourceRepository.findAll();
    }

    /**
     *  Get one resource by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Resource findOne(Long id) {
        log.debug("Request to get Resource : {}", id);
        return resourceRepository.findById(id).orElse(null);
    }

    /**
     *  Delete the  resource by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Resource : {}", id);
        resourceRepository.deleteById(id);
    }
    
    /**
     * Find all resource belongs to Task.
     * 
     * @param taskId
     * @return
     */
    public List<Resource> findByTaskId(Long taskId){
    	return this.resourceRepository.findByTaskId(taskId);
    	
    }
}
