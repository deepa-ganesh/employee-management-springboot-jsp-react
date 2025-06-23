package com.pcfc.assignment.converter;

import com.pcfc.assignment.dto.EmployeeDTO;
import com.pcfc.assignment.entity.EmployeeEntity;
import org.springframework.core.convert.converter.Converter;

public class EmployeeEntityToDtoConverter implements Converter<EmployeeEntity, EmployeeDTO> {

    @Override
    public EmployeeDTO convert(EmployeeEntity employeeEntity) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId(employeeEntity.getId());
        employeeDTO.setFirstName(employeeEntity.getFirstName());
        employeeDTO.setLastName(employeeEntity.getLastName());
        employeeDTO.setDateOfBirth(employeeEntity.getDateOfBirth());
        employeeDTO.setDepartment(employeeEntity.getDepartment());
        employeeDTO.setSalary(employeeEntity.getSalary());
        employeeDTO.setCurrency(employeeEntity.getCurrency());

        EmployeeEntity manager = employeeEntity.getManager();

        if (manager != null) {
            employeeDTO.setManagerId(manager.getId());
            employeeDTO.setManagerName(manager.getFirstName() + " " + manager.getLastName());
        }

        return employeeDTO;
    }
}
