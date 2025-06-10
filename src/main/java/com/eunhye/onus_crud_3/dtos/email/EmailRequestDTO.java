package com.eunhye.onus_crud_3.dtos.email;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDTO {

    @NotEmpty(message = "Email should not be empty")
    private String email;
}
