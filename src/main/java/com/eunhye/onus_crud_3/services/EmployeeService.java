package com.eunhye.onus_crud_3.services;

import com.eunhye.onus_crud_3.dtos.employee.EmployeeDTO;
import com.eunhye.onus_crud_3.dtos.employee.EmployeeResponseDTO;
import com.eunhye.onus_crud_3.dtos.PageResponseDTO;

import java.util.List;


public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeResponseDTO getEmployeeById(String employeeId);
    List<EmployeeResponseDTO> getAllEmployees();
    PageResponseDTO getAllEmployeesWithPagination(int pageNo, int pageSize, String sortBy, String sortDirection, String searchKeyword);
    void deleteEmployee(String employeeId);
    EmployeeResponseDTO updateEmployee(String employeeId, EmployeeDTO employeeDTO);
}
