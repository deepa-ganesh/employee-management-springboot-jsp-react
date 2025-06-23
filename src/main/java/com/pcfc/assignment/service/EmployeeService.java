package com.pcfc.assignment.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.pcfc.assignment.dto.EmployeeDTO;
import com.pcfc.assignment.entity.EmployeeEntity;
import com.pcfc.assignment.exception.EmployeeNotFoundException;
import com.pcfc.assignment.repository.EmployeeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ConversionService  conversionService;

    public EmployeeDTO saveOrUpdateEmployee(EmployeeDTO employeeDto) {
        EmployeeEntity employeeEntity = conversionService.convert(employeeDto, EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        log.info("Employee added successfully. New Employee ID: {}", savedEmployeeEntity.getId());
        return conversionService.convert(savedEmployeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employees = employeeRepository.findAll();

        return employees.stream()
               .map(employee -> conversionService.convert(employee, EmployeeDTO.class))
               .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) throws EmployeeNotFoundException {
        EmployeeEntity employee = employeeRepository.findById(id)
                                                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        return conversionService.convert(employee, EmployeeDTO.class);
    }

    public void deleteEmployee(Long id) throws EmployeeNotFoundException {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }

        List<EmployeeEntity> subordinates = employeeRepository.findByManagerId(id);

        if (!subordinates.isEmpty()) {
            subordinates.forEach(emp -> emp.setManager(null));
            employeeRepository.saveAll(subordinates);
        }

        employeeRepository.deleteById(id);
        log.info("Employee deleted successfully. Employee ID: {}", id);
    }
}
