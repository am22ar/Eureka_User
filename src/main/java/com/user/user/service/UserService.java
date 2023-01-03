package com.user.user.service;

import com.user.user.dto.LoginDto;
import com.user.user.dto.UserDto;
import com.user.user.exception.BookStoreException;
import com.user.user.model.UserModel;
import com.user.user.repository.UserRepositry;
import com.user.user.util.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements IUserService{
    List<UserModel> userModelList = new ArrayList<>();
    @Autowired
    UserRepositry userRepositry;

   @Autowired
   UserToken userToken;

    @Override
    public UserModel registerUser(UserDto userDto){
        Optional<UserModel> newUser = userRepositry.getByEmail(userDto.getEmail());
        //checks if user has already registered using this email
        if(newUser.isPresent()){
            throw new BookStoreException("User Already exists...");
        }else {
            //if email doesn't exists then new user will register
            UserModel userModel = new UserModel(userDto);
            return userRepositry.save(userModel);
        }
    }

    @Override
    public String  userLogin(LoginDto logindto) {
        Optional<UserModel> newUser = userRepositry.getByEmail(logindto.getEmail());
        if (logindto.getEmail().equals(newUser.get().getEmail()) &&
                logindto.getPassword().equals(newUser.get().getPassword())) {
            log.info("SuccessFully Logged In");
            return "SuccessFully Logged In";
        } else {
            throw new BookStoreException("User doesn't exists");
        }
    }

    @Override
    public Object userLoginCheck(LoginDto loginDto){
        Optional<UserModel> newUser = userRepositry.getByEmail(loginDto.getEmail());
        if(loginDto.getEmail().equals(newUser.get().getEmail()) &&
                loginDto.getPassword().equals(newUser.get().getPassword())){
            return newUser.get();
        }
        else {
            throw new BookStoreException("Sorry!! Either Username or password is incorrect");
        }
    }
    @Override
    public UserModel getById(Long id){
        Optional<UserModel> userModel= userRepositry.findById(id);
        if(userModel.isPresent()){
            return userModel.get();
        }else {
            throw new BookStoreException("Id not found...");
        }
    }

    @Override
    public UserModel getByToken(String token){
        Long userId = userToken.decodeToken(token);
        Optional<UserModel> userModel = userRepositry.findById(userId);
        if (userModel.isPresent()){
            return userModel.get();
        }else {
            throw new BookStoreException("Token doesn't Exists...");
        }
    }
    @Override
    public List<UserModel> getAll() {
        List<UserModel> userModelList = userRepositry.findAll();
        return userModelList;
    }
    @Override
    public UserModel getByEmail(String email){
        Optional<UserModel> userModel = userRepositry.getByEmail(email);
        if(userModel.isPresent())
        {
            return userModel.get();
        }
        else {
            throw new BookStoreException("Email is not available");
        }
    }

    @Override
    public UserModel updateByEmail(String email,UserDto userDto){
//        if (userRepository.getByEmail(email).isPresent()) {
//            UserModel newAdd = new UserModel(email,userDto);
//            newAdd.setEmail(email);
//            return userRepository.save(newAdd);
//        } else {
//            throw new BookStoreException("id not found");
//        }
        UserModel userByEmail = userRepositry.getByEmail(email).get();
        if (userByEmail == null)
            throw new BookStoreException("Can't Update: User email: '" + email + "' does'nt exists! in repo!");
        UserModel user = new UserModel(userDto);
        user.setUserId(userByEmail.getUserId());
        return userRepositry.save(user);
    }

    @Override
    public UserModel changePassword(UserDto userDto, String email) {
        Optional<UserModel> user = userRepositry.getByEmail(email);
        if(user != null){
            user.get().setPassword(userDto.getPassword());
            return userRepositry.save(user.get());
        }else {
            throw new BookStoreException("Sorry we could not find your email: "+email);
        }
    }
}
