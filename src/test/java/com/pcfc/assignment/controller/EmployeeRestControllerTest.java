package com.pcfc.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcfc.assignment.dto.EmployeeDTO;
import com.pcfc.assignment.exception.EmployeeNotFoundException;
import com.pcfc.assignment.exception.GlobalExceptionHandler;
import com.pcfc.assignment.service.EmployeeService;
import com.pcfc.assignment.validator.CircularManagerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployeeRestControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private CircularManagerValidator circularManagerValidator;

    @InjectMocks
    private EmployeeRestController employeeRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeRestController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void testGetAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(createMockEmployeeDTOData()));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("Deepa"));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        EmployeeDTO employee = createMockEmployeeDTOData();
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Deepa"));
    }

    @Test
    void testGetEmployeeById_NotFound() throws Exception {
        String errorMessage = "Not found";
        when(employeeService.getEmployeeById(1L)).thenThrow(new EmployeeNotFoundException(errorMessage));

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(errorMessage));
    }

    @Test
    void testCreateEmployee() throws Exception {
        EmployeeDTO employee = createMockEmployeeDTOData();
        when(employeeService.saveOrUpdateEmployee(any(EmployeeDTO.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Deepa"));

        verify(circularManagerValidator).validate(any(), any(BindingResult.class));
    }

    @Test
    void testCreateEmployee_ValidationError() throws Exception {
        EmployeeDTO employee = new EmployeeDTO();

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateEmployee() throws Exception {
        EmployeeDTO employee = createMockEmployeeDTOData();
        when(employeeService.saveOrUpdateEmployee(any(EmployeeDTO.class))).thenReturn(employee);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Deepa"));

        verify(circularManagerValidator).validate(any(), any(BindingResult.class));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employee deleted successfully"));

        verify(employeeService).deleteEmployee(1L);
    }

    @Test
    void testDeleteEmployee_NotFound() throws Exception {
        String errorMessage = "Not found";
        doThrow(new EmployeeNotFoundException(errorMessage)).when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(errorMessage));
    }

    private EmployeeDTO createMockEmployeeDTOData() {
        EmployeeDTO employee = new EmployeeDTO();
        employee.setId(1L);
        employee.setFirstName("Deepa");
        employee.setLastName("Ganesh");
        employee.setDateOfBirth(LocalDate.of(1992, 3, 20));
        employee.setDepartment("IT");
        employee.setSalary(new BigDecimal("20000"));
        employee.setCurrency("AED");
        return employee;
    }
}

