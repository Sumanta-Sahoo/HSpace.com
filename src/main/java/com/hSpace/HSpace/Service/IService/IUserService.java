package com.hSpace.HSpace.Service.IService;

import com.hSpace.HSpace.DTO.LoginRequest;
import com.hSpace.HSpace.DTO.Response;
import com.hSpace.HSpace.Entity.User;

public interface IUserService {

    Response register(User user);
    Response login(LoginRequest loginRequest);
    Response getAllUsers();
    Response  getUserBookings(Long userId);
    Response deleteUser(Long userId);
    Response getUserById(Long userId);
    Response getMyInfo(String userEmail);
}
