package com.wordpress.babuwant2do.workregistration.web.rest;

import com.wordpress.babuwant2do.workregistration.domain.InvoiceableTask;
import com.wordpress.babuwant2do.workregistration.domain.Task;
import com.wordpress.babuwant2do.workregistration.service.TaskService;
import com.wordpress.babuwant2do.workregistration.web.rest.helper.TaskBuilder;
import com.wordpress.babuwant2do.workregistration.web.rest.util.HeaderUtil;
import com.wordpress.babuwant2do.workregistration.web.rest.vm.TaskVM;

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
 * REST controller for managing Task.
 */
@RestController
@RequestMapping("/api")
public class TaskResource {

    private final Logger log = LoggerFactory.getLogger(TaskResource.class);

    private static final String ENTITY_NAME = "task";

    private final TaskService taskService;
    
    private final TaskBuilder taskBuilder;

    public TaskResource(TaskService taskService, TaskBuilder taskBuilder) {
        this.taskService = taskService;
        this.taskBuilder = taskBuilder;
    }

    /**
     * POST  /tasks : Create a new task.
     *
     * @param taskVM the task to create
     * @return the ResponseEntity with status 201 (Created) and with body the new task, or with status 400 (Bad Request) if the task has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskVM taskVM) throws URISyntaxException {
        log.debug("REST request to save Task : {}", taskVM);
        if (taskVM.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new task cannot already have an ID")).body(null);
        }
        Task task = this.taskBuilder.getTask(taskVM);
        Task result = taskService.save(task);
        return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tasks : Updates an existing task.
     *
     * @param task the task to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated task,
     * or with status 400 (Bad Request) if the task is not valid,
     * or with status 500 (Internal Server Error) if the task couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tasks")
    public ResponseEntity<Task> updateTask(@Valid @RequestBody TaskVM taskVM) throws URISyntaxException {
        log.debug("REST request to update Task : {}", taskVM);
        if (taskVM.getId() == null) {
            return createTask(taskVM);
        }
        Task task = this.taskBuilder.getTask(taskVM);
        Task result = taskService.save(task);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, task.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tasks : get all the tasks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tasks in body
     */
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        log.debug("REST request to get a page of Tasks");
        List<Task> page = taskService.findAll();
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }
    
    @GetMapping("/tasks/by-project-id/{projectId}")
    public ResponseEntity<List<Task>> getAllTasksByProjectID(@PathVariable Long projectId) {
    	log.debug("REST request to get a page of Tasks");
    	List<Task> page = taskService.findByProject(projectId);
    	return new ResponseEntity<>(page, null, HttpStatus.OK);
    }
    
    @GetMapping("/tasks/elegibale-for-invoice-by-project-id/{projectId}")
    public ResponseEntity<List<InvoiceableTask>> getElegibleTasksByProjectID(@PathVariable Long projectId) {
    	log.debug("REST request to get a page of Tasks");
    	List<InvoiceableTask> page = taskService.getElegibleInvoiceableByProject(projectId);
    	return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    /**
     * GET  /tasks/:id : get the "id" task.
     *
     * @param id the id of the task to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the task, or with status 404 (Not Found)
     */
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        log.debug("REST request to get Task : {}", id);
        Task task = taskService.findOne(id);
        if(task != null){
        	return new ResponseEntity<Task>(task, HttpStatus.OK);        	
        }
        return new ResponseEntity<Task>(task, HttpStatus.NOT_FOUND);  
    }

    /**
     * DELETE  /tasks/:id : delete the "id" task.
     *
     * @param id the id of the task to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.debug("REST request to delete Task : {}", id);
        taskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
