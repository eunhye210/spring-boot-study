package com.eunhye.onus_crud_3.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
}
