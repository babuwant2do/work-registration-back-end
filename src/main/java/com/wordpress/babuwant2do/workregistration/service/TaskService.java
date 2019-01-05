package com.wordpress.babuwant2do.workregistration.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wordpress.babuwant2do.workregistration.domain.Task;
import com.wordpress.babuwant2do.workregistration.repository.InvoiceableTaskRepository;
import com.wordpress.babuwant2do.workregistration.repository.TaskRepository;


/**
 * Service Implementation for managing Task.
 */
@Service
@Transactional
public class TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    
    private final InvoiceableTaskRepository invoiceableTaskRepository;
    
    public TaskService(TaskRepository taskRepository, InvoiceableTaskRepository invoiceableTaskRepository) {
        this.taskRepository = taskRepository;
        this.invoiceableTaskRepository = invoiceableTaskRepository;
    }

    /**
     * Save a task.
     *
     * @param task the entity to save
     * @return the persisted entity
     */
    public Task save(Task task) {
        log.debug("Request to save Task : {}", task);
        return taskRepository.save(task);
    }

    /**
     *  Get all the tasks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Task> findAll(Pageable pageable) {
        log.debug("Request to get all Tasks");
        return taskRepository.findAll(pageable);
    }
    
    @Transactional(readOnly = true)
    public List<Task> findAll() {
    	log.debug("Request to get all Tasks");
    	return taskRepository.findAll();
    }

    /**
     *  Get one task by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Task findOne(Long id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findOneWithEagerRelationships(id);
//        Task task = taskRepository.findOneWithEagerRelationships(id);
//        this.findByProject(task.getProject().getId());
//        return task;
    }

    /**
     *  Delete the  task by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.deleteById(id);
    }
    
    /**
     * return invoiceable Task List 
     * @param projectId
     * @return
     */
    public List<? extends Task> getInvoiceableTaskList(Long projectId){
    	return this.invoiceableTaskRepository.findByProjectIdWithEagerRelationships(projectId);
    }
    
    public List<Task> findByProject(Long projectId){
//    	return this.taskRepository.findByProjectId(projectId);
    	List<Task> tasks=  this.taskRepository.findByProjectId(projectId);
    	this.print(tasks);
    	return tasks;
    	
    }
    
    private void print(List<Task> tasks){
    	for (Task project : tasks) {
    		this.log.info(project.toString());			
		}
    }
    
}
