package com.eunhye.onus_crud_3.mapper;

import com.eunhye.onus_crud_3.dtos.EmployeeResponseDTO;
import com.eunhye.onus_crud_3.entities.Employee;
import com.eunhye.onus_crud_3.dtos.EmployeeDTO;

public class EmployeeMapper {
    public static Employee mapToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartment(employeeDTO.getDepartment());
        return employee;
    }

    public static EmployeeResponseDTO mapToEmployeeResponseDTO(Employee employee) {
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId(employee.getId());
        employeeResponseDTO.setFirstName(employee.getFirstName());
        employeeResponseDTO.setLastName(employee.getLastName());
        employeeResponseDTO.setEmail(employee.getEmail());
        employeeResponseDTO.setDepartment(employee.getDepartment());
        return employeeResponseDTO;
    }
}
