package com.pcfc.assignment.controller;

import com.pcfc.assignment.dto.EmployeeDTO;
import com.pcfc.assignment.exception.EmployeeNotFoundException;
import com.pcfc.assignment.service.EmployeeService;
import com.pcfc.assignment.validator.CircularManagerValidator;
import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CircularManagerValidator circularManagerValidator;

    @GetMapping
    public List<EmployeeDTO> getAll() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeDTO dto, Errors errors) {
        circularManagerValidator.validate(dto, errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }

        return ResponseEntity.ok(employeeService.saveOrUpdateEmployee(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) throws EmployeeNotFoundException {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDTO dto, Errors errors) {
        dto.setId(id);
        circularManagerValidator.validate(dto, errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }

        return ResponseEntity.ok(employeeService.saveOrUpdateEmployee(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable Long id) throws EmployeeNotFoundException {
        employeeService.deleteEmployee(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Employee deleted successfully");

        return ResponseEntity.ok(response);
    }
}