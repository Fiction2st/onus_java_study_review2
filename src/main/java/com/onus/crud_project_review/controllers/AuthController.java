package com.onus.crud_project_review.controllers;

import com.onus.crud_project_review.dtos.ApiResponseDTO;
import com.onus.crud_project_review.dtos.user.UserDTO;
import com.onus.crud_project_review.dtos.user.UserResponseDTO;
import com.onus.crud_project_review.services.EmailService;
import com.onus.crud_project_review.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> signUp(
            @Valid
            @RequestBody UserDTO userDTO) {
        UserResponseDTO savedUser = userService.createUser(userDTO);

        emailService.sendEmail(savedUser.getEmail(), savedUser.getUserName());

        ApiResponseDTO<UserResponseDTO> response = ApiResponseDTO.<UserResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("회원가입성공")
                .data(savedUser)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
