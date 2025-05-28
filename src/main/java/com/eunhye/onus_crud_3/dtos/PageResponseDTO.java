package com.eunhye.onus_crud_3.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO {
    private int pageSize;
    private int pageNo;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
    private List<EmployeeResponseDTO> body;
}
