package com.wordpress.babuwant2do.workregistration.web.rest.helper;


import org.springframework.stereotype.Component;

import com.wordpress.babuwant2do.workregistration.domain.ContactBasedInvoicableTask;
import com.wordpress.babuwant2do.workregistration.domain.ResourceBasedInvoicableTask;
import com.wordpress.babuwant2do.workregistration.domain.Task;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.TaskStatusEnum;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.TaskTypeEnum;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.UniteTypeEnum;
import com.wordpress.babuwant2do.workregistration.web.rest.vm.ResourceVM;
import com.wordpress.babuwant2do.workregistration.web.rest.vm.TaskVM;

@Component
public class TaskBuilder {
	
	private final ResourceBuilder resourceBuilder;
	
	public TaskBuilder(ResourceBuilder resourceBuilder){
		this.resourceBuilder = resourceBuilder;
	}
	
	public Task getTask(TaskVM taskVM){
        Task task;
        
        if(taskVM.getType() == TaskTypeEnum.INVOICABLE_CONTACT_BASED_TASK){
            ContactBasedInvoicableTask cTask = new ContactBasedInvoicableTask();
            cTask.setQuantity(2f);
            cTask.setPrice(3f);
            cTask.setUnit(UniteTypeEnum.PC);
//            TODO: adjust following line
//            cTask.setQuantity(taskVM.getQuantity());
//            cTask.setPrice(taskVM.getPrice());
//            cTask.setUnit(taskVM.getUnit());
            
            task = cTask;
        }else if( taskVM.getType() == TaskTypeEnum.INVOICABLE_RESOURCE_BASED_TASK){
            ResourceBasedInvoicableTask rTask = new ResourceBasedInvoicableTask();
            for (ResourceVM resourceVM : taskVM.getResources()) {
				rTask.addResource(this.resourceBuilder.getResource(resourceVM));
			}
            task = rTask;
        }else{
        	task = new Task();
        }
        task.setId(taskVM.getId());
        task.setName(taskVM.getName());
        task.setDescription(taskVM.getDescription());
        task.setCreateDate(taskVM.getCreateDate());
        task.setProject(taskVM.getProject());
        task.setExecutors(taskVM.getExecutors());
        
        if( taskVM.getId() != null && taskVM.getId() != 0){
        	task.setStatus(taskVM.getStatus());        	
        }else{
        	task.setStatus(TaskStatusEnum.INPROGRESS);        	
        }

        return task;
    }
}
