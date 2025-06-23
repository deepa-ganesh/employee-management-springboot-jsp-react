package com.pcfc.assignment.controller;

import com.pcfc.assignment.common.Department;
import com.pcfc.assignment.dto.EmployeeDTO;
import com.pcfc.assignment.exception.EmployeeNotFoundException;
import com.pcfc.assignment.service.EmployeeService;
import com.pcfc.assignment.validator.CircularManagerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CircularManagerValidator circularManagerValidator;

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee",    new EmployeeDTO());
        model.addAttribute("departments", Department.values());
        model.addAttribute("managers",    employeeService.getAllEmployees());

        return "employee-add-edit";
    }

    @PostMapping("/add")
    public String addEmployee(@Valid @ModelAttribute("employee")
                              EmployeeDTO   employee,
                              BindingResult result,
                              Model         model) {
        return saveOrUpdateEmployee(employee, result, model);
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());

        return "employee-list";
    }

    @GetMapping("/view/{id}")
    public String viewEmployee(@PathVariable Long id, Model model) throws EmployeeNotFoundException {
        EmployeeDTO employee = employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee);

        return "employee-view";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) throws EmployeeNotFoundException {
        EmployeeDTO employee = employeeService.getEmployeeById(id);

        model.addAttribute("employee",    employee);
        model.addAttribute("departments", Department.values());
        model.addAttribute("managers",    employeeService.getAllEmployees());

        return "employee-add-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @Valid @ModelAttribute("employee") EmployeeDTO employee,
                                 BindingResult result,
                                 Model         model) {
        employee.setId(id);

        return saveOrUpdateEmployee(employee, result, model);
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) throws EmployeeNotFoundException {
        employeeService.deleteEmployee(id);
        return "redirect:/employees/list";
    }

    private String saveOrUpdateEmployee(EmployeeDTO     employee,
                                BindingResult   result,
                                Model           model) {
        circularManagerValidator.validate(employee, result);

        if (result.hasErrors()) {
            model.addAttribute("departments", Department.values());
            model.addAttribute("managers",    employeeService.getAllEmployees());

            return "employee-add-edit";
        }

        employeeService.saveOrUpdateEmployee(employee);

        return "redirect:/employees/list";
    }
}
