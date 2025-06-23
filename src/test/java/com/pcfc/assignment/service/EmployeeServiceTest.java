package com.pcfc.assignment.service;

import com.pcfc.assignment.dto.EmployeeDTO;
import com.pcfc.assignment.entity.EmployeeEntity;
import com.pcfc.assignment.exception.EmployeeNotFoundException;
import com.pcfc.assignment.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOrUpdateEmployeeTest() {
        EmployeeDTO employeeDTO = createMockEmployeeDTOData();
        EmployeeEntity employeeEntity = createMockEmployeeEntityData();

        EmployeeEntity savedEntity = createMockEmployeeEntityData();
        savedEntity.setId(1L);

        when(conversionService.convert(employeeDTO, EmployeeEntity.class)).thenReturn(employeeEntity);
        when(employeeRepository.save(any(EmployeeEntity.class))).thenReturn(savedEntity);
        when(conversionService.convert(savedEntity, EmployeeDTO.class)).thenReturn(employeeDTO);

        EmployeeDTO result = employeeService.saveOrUpdateEmployee(employeeDTO);

        assertNotNull(result);
        verify(employeeRepository).save(any(EmployeeEntity.class));
        verify(conversionService).convert(employeeDTO, EmployeeEntity.class);
        verify(conversionService).convert(savedEntity, EmployeeDTO.class);
    }

    @Test
    void getAllEmployeesTest() {
        List<EmployeeEntity> employees = Arrays.asList(createMockEmployeeEntityData(), createMockEmployeeEntityData());

        when(employeeRepository.findAll()).thenReturn(employees);
        when(conversionService.convert(any(EmployeeEntity.class), eq(EmployeeDTO.class)))
                .thenReturn(createMockEmployeeDTOData());

        List<EmployeeDTO> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeRepository).findAll();
        verify(conversionService, times(2)).convert(any(EmployeeEntity.class), eq(EmployeeDTO.class));
    }

    @Test
    void getEmployeeByIdTest() throws EmployeeNotFoundException {
        Long id = 1L;
        EmployeeEntity employee = createMockEmployeeEntityData();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(conversionService.convert(employee, EmployeeDTO.class)).thenReturn(createMockEmployeeDTOData());

        EmployeeDTO result = employeeService.getEmployeeById(id);

        assertNotNull(result);
        verify(employeeRepository).findById(id);
        verify(conversionService).convert(employee, EmployeeDTO.class);
    }

    @Test
    void getEmployeeByIdNotFoundTest() {
        Long id = 1L;

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(id));
        verify(employeeRepository).findById(id);
    }

    @Test
    void deleteEmployeeTest() throws EmployeeNotFoundException {
        Long id = 1L;

        when(employeeRepository.existsById(id)).thenReturn(true);
        when(employeeRepository.findByManagerId(id)).thenReturn(Arrays.asList());

        employeeService.deleteEmployee(id);

        verify(employeeRepository).existsById(id);
        verify(employeeRepository).deleteById(id);
    }

    @Test
    void deleteEmployeeWithSubordinatesTest() throws EmployeeNotFoundException {
        Long id = 1L;
        List<EmployeeEntity> subordinates = Arrays.asList(createMockEmployeeEntityData(), createMockEmployeeEntityData());

        when(employeeRepository.existsById(id)).thenReturn(true);
        when(employeeRepository.findByManagerId(id)).thenReturn(subordinates);

        employeeService.deleteEmployee(id);

        verify(employeeRepository).existsById(id);
        verify(employeeRepository).findByManagerId(id);
        verify(employeeRepository).saveAll(subordinates);
        verify(employeeRepository).deleteById(id);

        subordinates.forEach(employee -> assertNull(employee.getManager()));
    }

    @Test
    void deleteEmployeeNotFoundTest() {
        Long id = 1L;

        when(employeeRepository.existsById(id)).thenReturn(false);

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(id));
        verify(employeeRepository).existsById(id);
        verify(employeeRepository, never()).deleteById(any());
    }

    private EmployeeDTO createMockEmployeeDTOData() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName("Deepa");
        dto.setLastName("Ganesh");
        dto.setDateOfBirth(LocalDate.of(1992, 3, 20));
        dto.setDepartment("IT");
        dto.setSalary(new BigDecimal("20000"));
        dto.setCurrency("AED");
        return dto;
    }

    private EmployeeEntity createMockEmployeeEntityData() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setFirstName("Deepa");
        entity.setLastName("Ganesh");
        entity.setDateOfBirth(LocalDate.of(1992, 3, 20));
        entity.setDepartment("IT");
        entity.setSalary(new BigDecimal("20000"));
        entity.setCurrency("AED");
        return entity;
    }
}
