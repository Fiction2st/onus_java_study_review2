package com.onus.crud_project_review.services;

import com.onus.crud_project_review.dtos.user.UserDTO;
import com.onus.crud_project_review.dtos.user.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(UserDTO userDTO);
}
