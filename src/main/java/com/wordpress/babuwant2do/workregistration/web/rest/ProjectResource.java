package com.wordpress.babuwant2do.workregistration.web.rest;

import com.wordpress.babuwant2do.workregistration.domain.Client;
import com.wordpress.babuwant2do.workregistration.domain.Project;
import com.wordpress.babuwant2do.workregistration.domain.User;
import com.wordpress.babuwant2do.workregistration.security.SecurityUtils;
import com.wordpress.babuwant2do.workregistration.service.ProjectService;
import com.wordpress.babuwant2do.workregistration.service.UserService;
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
 * REST controller for managing Project.
 */
@RestController
@RequestMapping("/api")
public class ProjectResource {

    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

    private static final String ENTITY_NAME = "project";

    private final ProjectService projectService;

    private final UserService userService;

    public ProjectResource(ProjectService projectService
    		, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }
    /**
     * save by user login
     * @param login
     * @param project
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/projects/by-user")
    public ResponseEntity<Project> createProjectForUser(@Valid @RequestBody Project project) throws URISyntaxException {
    			 
    	log.debug("REST request to save Project {} for User", project);
        if (project.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new project cannot already have an ID")).body(null);
        }
        User user = this.userService.getUserByLogin(SecurityUtils.getCurrentUserLogin());
        log.warn("for User {}", user);
        if (project.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user", "notfound", "User ID is not Valid")).body(null);
        }
        
        Client c =new Client();
        c.setId(1l);
        project.setOwner(user);
//        project.setClient(c);
        Project result = projectService.save(project);
        return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
//    @PostMapping("/projects/by-user/vm")
//    public ResponseEntity<ProjectVM> createProjectVMForUser(@Valid @RequestBody ProjectVM project) throws URISyntaxException {
//    	
//    	log.debug("REST request to save ProjecVMt {} for User", project);
//    	return ResponseEntity.created(new URI("/api/projects/" + 100))
//    			.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, "100"))
//    			.body(project);
//    }

    /**
     * POST  /projects : Create a new project.
     *
     * @param project the project to create
     * @return the ResponseEntity with status 201 (Created) and with body the new project, or with status 400 (Bad Request) if the project has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) throws URISyntaxException {
        log.debug("REST request to save Project : {}", project);
        if (project.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new project cannot already have an ID")).body(null);
        }
        Project result = projectService.save(project);
        return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
   

    /**
     * PUT  /projects : Updates an existing project.
     *
     * @param project the project to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated project,
     * or with status 400 (Bad Request) if the project is not valid,
     * or with status 500 (Internal Server Error) if the project couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/projects")
    public ResponseEntity<Project> updateProject(@Valid @RequestBody Project project) throws URISyntaxException {
        log.debug("REST request to update Project : {}", project);
        if (project.getId() == null) {
            return createProject(project);
        }
        Project result = projectService.save(project);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, project.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projects : get all the projects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projects in body
     */
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        log.debug("REST request to get a page of Projects");
        List<Project> page = projectService.findAll();
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }
    
    @GetMapping("/projects/byLoggedinUser")
    public ResponseEntity<List<Project>> getAllProjectsForLoggedinUser() {
    	log.debug("REST request to get a page of Projects");
    	List<Project> page = projectService.getProjectByLogin(SecurityUtils.getCurrentUserLogin());
    	return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    /**
     * GET  /projects/:id : get the "id" project.
     *
     * @param id the id of the project to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the project, or with status 404 (Not Found)
     */
    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        log.debug("REST request to get Project : {}", id);
        Project project = projectService.findOne(id);
        if(project != null){
        	return new ResponseEntity<Project>(project, HttpStatus.OK);        	
        }
        return new ResponseEntity<Project>(project, HttpStatus.NOT_FOUND);        	
    }

    /**
     * DELETE  /projects/:id : delete the "id" project.
     *
     * @param id the id of the project to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.debug("REST request to delete Project : {}", id);
        projectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
