package com.wordpress.babuwant2do.workregistration.web.rest.helper;

import org.springframework.stereotype.Component;

import com.wordpress.babuwant2do.workregistration.domain.Resource;
import com.wordpress.babuwant2do.workregistration.domain.WorkingHourResource;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.ResourceTypeEnum;
import com.wordpress.babuwant2do.workregistration.domain.enumeration.UniteTypeEnum;
import com.wordpress.babuwant2do.workregistration.web.rest.vm.ResourceVM;


@Component
public class ResourceBuilder{
	public Resource getResource(ResourceVM resourceVM){
        Resource resource = null;
        if(resourceVM.getType().equals( ResourceTypeEnum.WORKING_HOUR_RESOURCE.toString())){
            WorkingHourResource workingHrResource = new WorkingHourResource();
            workingHrResource.setExecutionDate(resourceVM.getExecutionDate());
            workingHrResource.setUnitType(UniteTypeEnum.HOUR);
            workingHrResource.setUnitPrice(15f);//TODO: must be task wise configuration, bt for now set static value
            resource = workingHrResource;
        }else{
            resource = new Resource();
            resource.setUnitType(UniteTypeEnum.PC);
            resource.setUnitPrice(resourceVM.getUnitPrice());
        }
        
        resource.setDescription(resourceVM.getDescription());
        resource.setTask(resourceVM.getTask());
        resource.setUnitQty(resourceVM.getUnitQty());
        resource.setId(resourceVM.getId());

        return resource;
    }
}
