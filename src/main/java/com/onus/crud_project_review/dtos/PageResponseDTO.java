package com.onus.crud_project_review.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO {
    private int pageSize; // 한 페이지에 가져오는 데이터 수
    private int pageNo; // 페이지 수
    private long totalSize; // 총 데이터 수
    private long totalPages; // 총 페이지 수
    private boolean hasNext; // 다음페이지 존재유무
    private boolean hasPrevious; // 이전페이지 존재유무
    private List<EmployeeResponseDTO> content;
}
