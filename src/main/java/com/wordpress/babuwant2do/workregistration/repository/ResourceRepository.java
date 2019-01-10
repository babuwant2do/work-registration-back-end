package com.wordpress.babuwant2do.workregistration.repository;

import com.wordpress.babuwant2do.workregistration.domain.Resource;
import com.wordpress.babuwant2do.workregistration.domain.Task;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Resource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
	 List<Resource> findByTaskId(Long id);
	 Page<Resource> findByTaskId(Long id, Pageable pageable);
	
}
