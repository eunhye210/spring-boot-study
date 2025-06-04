package com.eunhye.onus_crud_3.services.impl;

import com.eunhye.onus_crud_3.dtos.employee.EmployeeDTO;
import com.eunhye.onus_crud_3.dtos.employee.EmployeeResponseDTO;
import com.eunhye.onus_crud_3.dtos.PageResponseDTO;
import com.eunhye.onus_crud_3.entities.Employee;
import com.eunhye.onus_crud_3.mapper.EmployeeMapper;
import com.eunhye.onus_crud_3.repositories.EmployeeRepository;
import com.eunhye.onus_crud_3.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PageResponseDTO getAllEmployeesWithPagination(
            int pageNo,
            int pageSize,
            String sortBy,
            String sortDirection,
            String searchKeyword
    ) {
        Sort sort =  sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        // 단순 pagination 적용
//        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        // searchKeyword 추가 적용
        Page<Employee> employeePage;
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            employeePage = employeeRepository.findAll(pageable);
        } else {
            employeePage = employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    searchKeyword,
                    searchKeyword,
                    searchKeyword,
                    pageable
            );
        }

        List<EmployeeResponseDTO> employeeResponseDTOS = employeePage.getContent()
                .stream()
                .map(EmployeeMapper::mapToEmployeeResponseDTO)
                .collect(Collectors.toList());

        return PageResponseDTO.builder()
                .body(employeeResponseDTOS)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(employeePage.getTotalElements())
                .totalPages(employeePage.getTotalPages())
                .hasNext(employeePage.hasNext())
                .hasPrevious(employeePage.hasPrevious())
                .build();
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
