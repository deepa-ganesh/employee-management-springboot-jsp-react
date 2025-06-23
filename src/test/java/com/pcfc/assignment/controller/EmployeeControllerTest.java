package com.pcfc.assignment.controller;

import com.pcfc.assignment.dto.EmployeeDTO;
import com.pcfc.assignment.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private View mockView;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setSingleView(mockView)
                .build();
    }

    @Test
    void testShowAddForm() throws Exception {
        mockMvc.perform(get("/employees/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("employee"))
                .andExpect(model().attributeExists("departments"))
                .andExpect(model().attributeExists("managers"))
                .andExpect(view().name("employee-add-edit"));
    }

    @Test
    void testListEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(createMockEmployeeDTOData()));

        mockMvc.perform(get("/employees/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("employees"))
                .andExpect(view().name("employee-list"));
    }

    @Test
    void testViewEmployee() throws Exception {
        EmployeeDTO employee = createMockEmployeeDTOData();
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/employees/view/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("employee"))
                .andExpect(view().name("employee-view"));
    }

    @Test
    void testEditEmployee() throws Exception {
        EmployeeDTO employee = createMockEmployeeDTOData();
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/employees/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("employee"))
                .andExpect(model().attributeExists("departments"))
                .andExpect(model().attributeExists("managers"))
                .andExpect(view().name("employee-add-edit"));
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

