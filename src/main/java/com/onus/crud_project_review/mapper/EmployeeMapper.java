package com.onus.crud_project_review.mapper;

import com.onus.crud_project_review.dtos.employee.EmployeeDTO;
import com.onus.crud_project_review.dtos.employee.EmployeeResponseDTO;
import com.onus.crud_project_review.entities.Employees;

public class EmployeeMapper {
    public static Employees mapToEmployee(EmployeeDTO employeeDTO){
        Employees employees = new Employees();
        employees.setId(employeeDTO.getId());
        employees.setFirstName(employeeDTO.getFirstName());
        employees.setLastName(employeeDTO.getLastName());
        employees.setEmail(employeeDTO.getEmail());
        employees.setDepartment(employeeDTO.getDepartment());
        return employees;
    }

    public static EmployeeResponseDTO mapToEmployeeResponseDTO(Employees employees){
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId(employees.getId());
        employeeResponseDTO.setFirstName(employees.getFirstName());
        employeeResponseDTO.setLastName(employees.getLastName());
        employeeResponseDTO.setEmail(employees.getEmail());
        employeeResponseDTO.setDepartment(employees.getDepartment());
        return employeeResponseDTO;
    }
}
