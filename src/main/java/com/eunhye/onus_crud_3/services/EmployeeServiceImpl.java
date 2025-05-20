package com.eunhye.onus_crud_3.services;

import com.eunhye.onus_crud_3.dtos.EmployeeDTO;
import com.eunhye.onus_crud_3.dtos.EmployeeResponseDTO;
import com.eunhye.onus_crud_3.entities.Employee;
import com.eunhye.onus_crud_3.mapper.EmployeeMapper;
import com.eunhye.onus_crud_3.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        // convert DTO to entity
        Employee employee = EmployeeMapper.mapToEmployee(employeeDTO);

        // save entity to database
        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeResponseDTO(savedEmployee);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return EmployeeMapper.mapToEmployeeResponseDTO(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees
                .stream()
                .map(EmployeeMapper::mapToEmployeeResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmployee(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(String employeeId, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!employee.getEmail().equals(employeeDTO.getEmail()) &&
                employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // update entity
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartment(employeeDTO.getDepartment());

        Employee upatedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeResponseDTO(upatedEmployee);
    }
}
