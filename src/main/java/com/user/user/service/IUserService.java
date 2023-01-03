package com.user.user.service;


import com.user.user.dto.LoginDto;
import com.user.user.dto.UserDto;
import com.user.user.model.UserModel;

import java.util.List;

public interface IUserService {
    public UserModel registerUser(UserDto userDto);
    public String  userLogin(LoginDto logindto);
    public UserModel getById(Long id);
    public UserModel getByToken(String token);
    public List<UserModel> getAll();
    public UserModel getByEmail(String email);
    public UserModel updateByEmail(String email,UserDto userDto);
    public UserModel changePassword(UserDto userDto, String email);
    public Object userLoginCheck(LoginDto loginDto);
}
