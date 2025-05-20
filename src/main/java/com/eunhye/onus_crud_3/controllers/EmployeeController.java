package com.eunhye.onus_crud_3.controllers;

import com.eunhye.onus_crud_3.dtos.EmployeeDTO;
import com.eunhye.onus_crud_3.dtos.EmployeeResponseDTO;
import com.eunhye.onus_crud_3.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    // Get All Employees REST API
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }


    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(
            @PathVariable String employeeId
    ) {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @RequestBody EmployeeDTO employeeDTO
    ) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeDTO));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable String employeeId
    ) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable String employeeId,
            @RequestBody EmployeeDTO employeeDTO
    ) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employeeDTO));
    }
}
