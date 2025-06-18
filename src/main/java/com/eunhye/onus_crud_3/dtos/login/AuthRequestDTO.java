package com.eunhye.onus_crud_3.dtos.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequestDTO {
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Schema(description = "이메일 주소", example = "tkrhd0210@gmail.com")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    @Schema(description = "비밀번호", example = "01234567")
    private String password;
}
