package com.pcfc.assignment.validator;

import com.pcfc.assignment.dto.EmployeeDTO;
import com.pcfc.assignment.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CircularManagerValidator implements Validator {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public boolean supports(Class<?> clazz) {
        return EmployeeDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeDTO employee = (EmployeeDTO) target;
        
        if (employee.getManagerId() != null) {
            if (employee.getId() != null && employee.getId().equals(employee.getManagerId())) {
                errors.rejectValue("managerId", "circular.reference", 
                    "An employee cannot be their own manager");
                return;
            }

            try {
                EmployeeDTO manager = employeeService.getEmployeeById(employee.getManagerId());
                Long currentManagerId = manager.getManagerId();

                while (currentManagerId != null) {
                    if (employee.getId() != null && employee.getId().equals(currentManagerId)) {
                        errors.rejectValue("managerId", "circular.reference", 
                            "Cannot assign this manager as it would create a circular reference");
                        return;
                    }

                    EmployeeDTO currentManager = employeeService.getEmployeeById(currentManagerId);
                    currentManagerId = currentManager.getManagerId();
                }
            } catch (Exception e) {
                errors.rejectValue("managerId", "manager.not.found", 
                    "Selected manager does not exist");
            }
        }
    }
}

