package com.onus.crud_project_review.controllers;

import com.onus.crud_project_review.dtos.EmployeeDTO;
import com.onus.crud_project_review.dtos.EmployeeResponseDTO;
import com.onus.crud_project_review.dtos.PageResponseDTO;
import com.onus.crud_project_review.repositories.EmployeeRepository;
import com.onus.crud_project_review.services.EmployeeService;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(){
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @Cacheable(
            value = "employees",
            key = "T(java.util.Objects).hash(#pageNo, #pageSize, #sortBy, #sortDirection, #searchKeyword)"
    )

    @GetMapping("/all")
    public ResponseEntity<PageResponseDTO> getAllEmployeeWithPagination(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String searchKeyword
    ) {
        PageResponseDTO res = employeeService.getAllEmployeeWithPagination(pageNo, pageSize, sortBy, sortDirection, searchKeyword);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable String employeeId){
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.ok(employeeService.createEmployee(employeeDTO));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable String employeeId,
            @RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employeeDTO));
    }

    @DeleteMapping("/{employId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employId){
        employeeRepository.deleteById(employId);
        return ResponseEntity.ok().build();
    }

}
