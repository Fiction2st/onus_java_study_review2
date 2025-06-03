package com.onus.crud_project_review.services;

import com.onus.crud_project_review.dtos.EmployeeDTO;
import com.onus.crud_project_review.dtos.EmployeeResponseDTO;
import com.onus.crud_project_review.dtos.PageResponseDTO;
import com.onus.crud_project_review.entities.Employees;
import com.onus.crud_project_review.mapper.EmployeeMapper;
import com.onus.crud_project_review.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springdoc.core.service.GenericResponseService;
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
    private final GenericResponseService responseBuilder;
    private EmployeeRepository employeeRepository;
    @Override
    public EmployeeResponseDTO createEmployee(EmployeeDTO employeeDTO) {
        if(employeeRepository.existsById(employeeDTO.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        Employees employees = EmployeeMapper.mapToEmployee(employeeDTO);
        employeeRepository.save(employees);

        return EmployeeMapper.mapToEmployeeResponseDTO(employees);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(String employeeId) {
        Employees employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new RuntimeException("Employee not found"));
        return EmployeeMapper.mapToEmployeeResponseDTO(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employees> employees = employeeRepository.findAll();
        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmployeeById(String employeeId) {
        Employees employees = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new RuntimeException("Employee not found"));
        employeeRepository.delete(employees);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(String employeeId, EmployeeDTO employeeDTO) {
        // 1. 해당 employee 찾기
        Employees employees = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new RuntimeException("Employee not found"));

        // 검색된 직원의 이메일과 업데이트 요청 이메일이 일치하지 않고, DB에 중복되는 email이 있다면 에러처리
        if(!employees.getEmail().equals(employeeDTO.getEmail()) && employeeRepository.existsByEmail(employeeDTO.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        employees.setFirstName(employeeDTO.getFirstName());
        employees.setLastName(employeeDTO.getLastName());
        employees.setEmail(employeeDTO.getEmail());
        employees.setDepartment(employeeDTO.getDepartment());

        Employees updatedEmployee = employeeRepository.save(employees);

        return EmployeeMapper.mapToEmployeeResponseDTO(updatedEmployee);
    }

    @Override
    public PageResponseDTO getAllEmployeeWithPagination(int pageNo, int pageSize, String sortBy, String sortDirection, String searchKeyword) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);

        Page<Employees> employeePage;
        if(searchKeyword == null || searchKeyword.trim().isEmpty()) {
            employeePage = employeeRepository.findAll(pageable);
        } else {
            employeePage = employeeRepository.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    searchKeyword, searchKeyword, searchKeyword, pageable // searchKeyword 는 하나만 작성해도 된다.
            );
        }

        List<EmployeeResponseDTO> employeeResponseDTOS = employeePage.getContent()
                .stream()
                .map(EmployeeMapper::mapToEmployeeResponseDTO)
                .collect(Collectors.toList());


        return PageResponseDTO.builder()
                .content(employeeResponseDTOS)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(employeePage.getTotalPages())
                .totalSize(employeePage.getTotalElements())
                .hasNext(employeePage.hasNext())
                .hasPrevious(employeePage.hasPrevious())
                .build();
    }
}
