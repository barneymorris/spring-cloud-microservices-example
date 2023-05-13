package com.barney.users.controllers;

import com.barney.users.dto.UserDTO;
import com.barney.users.exception.ApiError;
import com.barney.users.exception.UserAlreadyExistException;
import com.barney.users.model.CreateUserRequestModel;
import com.barney.users.model.CreateUserResponseModel;
import com.barney.users.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @Valid @RequestBody CreateUserRequestModel userDetails
    ) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDTO userDTO = modelMapper.map(userDetails, UserDTO.class);

        try {
            UserDTO createdUser = userService.createUser(userDTO);
            CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(returnValue);
        } catch (UserAlreadyExistException e) {
            ApiError apiError = new ApiError(400, e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(apiError);
        }
    }
}
