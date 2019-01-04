package com.wordpress.babuwant2do.workregistration.repository;

import com.wordpress.babuwant2do.workregistration.domain.Project;
import com.wordpress.babuwant2do.workregistration.domain.Task;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select distinct task from Task task left join fetch task.executors")
    List<Task> findAllWithEagerRelationships();

    @Query("select task from Task task left join fetch task.executors where task.id =:id")
    Task findOneWithEagerRelationships(@Param("id") Long id);

    List<Task> findByProjectId(Long id);
    Page<Task> findByProjectId(Long id, Pageable pageable);
}
