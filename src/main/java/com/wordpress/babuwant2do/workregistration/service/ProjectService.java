package com.wordpress.babuwant2do.workregistration.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wordpress.babuwant2do.workregistration.domain.Project;
import com.wordpress.babuwant2do.workregistration.repository.ProjectRepository;


/**
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;
    
    
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Save a project.
     *
     * @param project the entity to save
     * @return the persisted entity
     */
    public Project save(Project project) {
        log.debug("Request to save Project : {}", project);
        return projectRepository.save(project);
    }

    /**
     *  Get all the projects.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Project> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        return projectRepository.findAll(pageable);
    }
    
    @Transactional(readOnly = true)
    public List<Project> findAll() {
    	log.debug("Request to get all Projects");
    	return projectRepository.findAll();
    }

    /**
     *  Get one project by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Project findOne(Long id) {
        log.debug("Request to get Project : {}", id);
//        return projectRepository.findOne(id);
        return projectRepository.findById(id).orElse(null);
        
        //TODO: undo change
//        Project project =  projectRepository.getOne(id);
//        this.getProjectByLogin(project.getOwner().getLogin());
//        
//        return project;
    }

    /**
     *  Delete the  project by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.deleteById(id);
    }
    
    //
    
    public List<Project> getProjectByLogin(String login){
    	List<Project> projects= this.projectRepository.findByOwner_Login(login);
    	this.log.warn("Project List by Owner");			
    	this.print(projects);
    	
//    	Pageable pageable = new PageRequest(pageIndex, pageSize, Direction.ASC, "user_login_id");
    	Pageable pageable = PageRequest.of(0, 2);
    	Page<Project> page = this.projectRepository.findByOwner_Login(login, pageable);
    	this.log.warn("Project Page by Owner");
    	this.print(page.getContent());
    	
    	return projects;
    }
    
    private void print(List<Project> projects){
    	for (Project project : projects) {
    		this.log.info(project.toString());			
		}
    }
    
    
}
