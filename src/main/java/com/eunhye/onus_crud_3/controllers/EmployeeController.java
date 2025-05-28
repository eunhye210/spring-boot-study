package com.eunhye.onus_crud_3.controllers;

import com.eunhye.onus_crud_3.dtos.EmployeeDTO;
import com.eunhye.onus_crud_3.dtos.EmployeeResponseDTO;
import com.eunhye.onus_crud_3.dtos.PageResponseDTO;
import com.eunhye.onus_crud_3.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @Cacheable(
            value = "employees",
            key = "T(java.util.Objects).hash(#pageNo, #pageSize, #sortBy, #sortDirection, #searchKeyword)"
    )
    @GetMapping("/pagination")
    public ResponseEntity<PageResponseDTO> getAllEmployeesWithPagination(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String searchKeyword
    ) {
        PageResponseDTO res = employeeService.getAllEmployeesWithPagination(
                pageNo,
                pageSize,
                sortBy,
                sortDirection,
                searchKeyword
        );
        return ResponseEntity.ok(res);
    }


    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(
            @PathVariable String employeeId
    ) {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @CacheEvict(value = "employees", allEntries = true)
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @RequestBody EmployeeDTO employeeDTO
    ) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeDTO));
    }

    @CacheEvict(value = "employees", allEntries = true)
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable String employeeId
    ) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }

    @CacheEvict(value = "employees", allEntries = true)
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable String employeeId,
            @RequestBody EmployeeDTO employeeDTO
    ) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employeeDTO));
    }
}
