package com.onus.crud_project_review.services;

import com.onus.crud_project_review.dtos.employee.EmployeeDTO;
import com.onus.crud_project_review.dtos.employee.EmployeeResponseDTO;
import com.onus.crud_project_review.dtos.PageResponseDTO;


import java.util.List;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeResponseDTO getEmployeeById(String employeeId);
    List<EmployeeResponseDTO> getAllEmployees();
    void deleteEmployeeById(String employeeId);
    EmployeeResponseDTO updateEmployee(String employeeId, EmployeeDTO employeeDTO);
    PageResponseDTO getAllEmployeeWithPagination(
            int pageNo, int pageSize, String sortBy,
            String sortDirection, String searchKeyword);
}
