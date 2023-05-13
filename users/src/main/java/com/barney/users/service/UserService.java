package com.barney.users.service;

import com.barney.users.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDetails);
}
