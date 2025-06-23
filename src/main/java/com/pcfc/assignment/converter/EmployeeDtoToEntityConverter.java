package com.pcfc.assignment.converter;

import com.pcfc.assignment.dto.EmployeeDTO;
import com.pcfc.assignment.entity.EmployeeEntity;
import com.pcfc.assignment.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class EmployeeDtoToEntityConverter implements Converter<EmployeeDTO, EmployeeEntity> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeEntity convert(EmployeeDTO employeeDto) {
        EmployeeEntity employeeEntity = new EmployeeEntity();

        employeeEntity.setId(employeeDto.getId());
        employeeEntity.setFirstName(employeeDto.getFirstName());
        employeeEntity.setLastName(employeeDto.getLastName());
        employeeEntity.setDateOfBirth(employeeDto.getDateOfBirth());
        employeeEntity.setDepartment(employeeDto.getDepartment());
        employeeEntity.setSalary(employeeDto.getSalary());
        employeeEntity.setCurrency(employeeDto.getCurrency());

        if (employeeDto.getManagerId() != null) {
            employeeRepository
                    .findById(employeeDto.getManagerId())
                    .ifPresent(employeeEntity::setManager);
        }

        return employeeEntity;
    }
}
