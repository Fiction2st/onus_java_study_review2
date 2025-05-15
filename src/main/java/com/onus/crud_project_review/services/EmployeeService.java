package com.onus.crud_project_review.services;

import com.onus.crud_project_review.dtos.EmployeeDTO;
import com.onus.crud_project_review.dtos.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeResponseDTO getEmployeeById(String employeeId);
    List<EmployeeResponseDTO> getAllEmployees();
    void deleteEmployeeById(String employeeId);
    EmployeeResponseDTO updateEmployee(String employeeId, EmployeeDTO employeeDTO);
}
