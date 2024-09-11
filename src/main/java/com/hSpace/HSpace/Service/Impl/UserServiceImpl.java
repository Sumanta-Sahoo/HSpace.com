package com.hSpace.HSpace.Service.Impl;

import com.hSpace.HSpace.DTO.LoginRequest;
import com.hSpace.HSpace.DTO.Response;
import com.hSpace.HSpace.DTO.UserDTO;
import com.hSpace.HSpace.Entity.User;
import com.hSpace.HSpace.Exception.CustomException;
import com.hSpace.HSpace.Repository.UserRepository;
import com.hSpace.HSpace.Service.IService.IUserService;
import com.hSpace.HSpace.Utils.JWTUtils;
import com.hSpace.HSpace.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Response register(User user) {
        Response response = new Response();
        try{
           if(user.getRole() == null || user.getRole().isBlank()){
               user.setRole("User");
           }
           if(userRepository.existsByEmail(user.getEmail())){
               throw new CustomException(user.getEmail() + " Already Exist !! Try Another One");
           }
           user.setPassword(passwordEncoder.encode(user.getPassword()));
           User savedUser = userRepository.save(user);
           UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);
           response.setStatusCode(200);
           response.setUser(userDTO);


        }catch(CustomException ce){
            response.setStatusCode(400);
            response.setMessage(ce.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred during registration");
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new CustomException("User not found with email : " + loginRequest.getEmail()));

            var jwtToken = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwtToken);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");
            response.setMessage("Successfull");
        }catch (CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred during login request");
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try{
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserDTOList(userList);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setUserList(userDTOList);
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred during get all users");
        }
        return response;
    }

    @Override
    public Response getUserBookings(Long userId) {
        Response response = new Response();
        try{
            User user = userRepository.findById(userId).orElseThrow(() -> new CustomException("User not found with :: " + userId));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setUser(userDTO);
        }catch (CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred during get user bookings");
        }
        return response;
    }

    @Override
    public Response deleteUser(Long userId) {
        Response response = new Response();
        try{
            //Without saving user will implement after testing
            User user = userRepository.findById(userId).orElseThrow(()-> new CustomException("User Not Found With userID :: " + userId));
            if(user!=null){
                userRepository.deleteById(userId);
                response.setStatusCode(200);
                response.setMessage("Successfull");
            }
        }catch (CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred during delete user having userId : " + userId);
        }
        return null;
    }

    @Override
    public Response getUserById(Long userId) {
        Response response = new Response();
        try{
            User user = userRepository.findById(userId).orElseThrow(()-> new CustomException("User Not Found : " + userId));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setUser(userDTO);
        }catch (CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred during get user by id" + userId);
        }
        return response;
    }

    @Override
    public Response getMyInfo(String userEmail) {
        Response response = new Response();
        try{
            User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new CustomException("User Not Found : " + userEmail));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setUser(userDTO);
        }catch (CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred during get user by email : " + userEmail);
        }
        return null;
    }
}
