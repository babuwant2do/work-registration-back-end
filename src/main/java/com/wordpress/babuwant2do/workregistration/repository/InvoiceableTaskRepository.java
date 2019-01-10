package com.wordpress.babuwant2do.workregistration.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wordpress.babuwant2do.workregistration.domain.InvoiceableTask;
import com.wordpress.babuwant2do.workregistration.domain.Task;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.TaskStatusEnum;

@SuppressWarnings("unused")
@Repository
public interface InvoiceableTaskRepository extends JpaRepository<InvoiceableTask, Long> {
    
//    @Query("select task from Task task left join fetch task.executors where task.id =:id")
//    Task findByWithEagerRelationships(@Param("id") Long id);
    
    @Query("select task from InvoiceableTask task where task.project.id =:projectId")
    List<InvoiceableTask> findByProjectIdWithEagerRelationships(@Param("projectId") Long projectId);

    @Query("select task from InvoiceableTask task where task.project.id =:projectId and task.status =:status")
    List<InvoiceableTask> findByProjectIdAndStatusWithEagerRelationships(@Param("projectId") Long projectId, @Param("status") TaskStatusEnum status);

    @Query("select task from InvoiceableTask task where task.project.id =:projectId and task.status =:status")
    List<InvoiceableTask> findByProjectIdAndStatus(@Param("projectId") Long projectId, @Param("status") TaskStatusEnum status);
    
    @Query("update InvoiceableTask task SET task.invoice.id=null WHERE task.invoice.id=:invoiceId")
    void removeInvoiceRefFromTask(@Param("invoiceId") Long invoiceId);
    
}
