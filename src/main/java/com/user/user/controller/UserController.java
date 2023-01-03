package com.user.user.controller;

import com.user.user.dto.LoginDto;
import com.user.user.dto.ResponseDto;
import com.user.user.dto.UserDto;
import com.user.user.exception.BookStoreException;
import com.user.user.model.EmailModel;
import com.user.user.model.UserModel;
import com.user.user.service.IEmailService;
import com.user.user.service.IUserService;
import com.user.user.util.UserToken;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookstore")
public class UserController {

    @Autowired
    IUserService userService;
    @Autowired
    IEmailService iEmailService;
    @Autowired
    UserToken userToken;

    @PostMapping("/registerUser")
    public ResponseEntity<ResponseDto> registerUser(@Valid @RequestBody UserDto userDto){
        UserModel newUser = userService.registerUser(userDto);
        String token = userToken.createToken(newUser.getUserId());
        EmailModel emailModel = new EmailModel(newUser.getEmail(),"Account Sign-up successfully "," Hello "+ newUser.getFirstName()
                + "\n Your Account has been created on 'Book Store'"+"\nSave this Token for future use: "+token);
        iEmailService.sendEmail(emailModel);
        ResponseDto responseDto = new ResponseDto("User Registered Successfully",newUser,token);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/userLogin")
    public ResponseEntity<ResponseDto> userLogin(@RequestBody LoginDto loginDto){
        String userModel = userService.userLogin(loginDto);
        ResponseDto responseDto = new ResponseDto("User Logged in Successful...",userService.userLogin(loginDto),null);
        return new ResponseEntity<>(responseDto,HttpStatus.ACCEPTED);
    }

    @GetMapping("/getRecordById/{userId}")
    public ResponseEntity<ResponseDto> getById(@PathVariable(value = "userId") Long userId) throws BookStoreException {
        ResponseDto responseDto = new ResponseDto("User Data of Given ID:"+userId,userService.getById(userId),null);
        return new  ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @GetMapping("/getRecordByToken")
    public ResponseEntity<ResponseDto> getByToken(@RequestParam String token){
        UserModel userModel = userService.getByToken(token);
        ResponseDto responseDto = new ResponseDto("User Data of Given token:",userModel,null);
        return new  ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @GetMapping("/getAllRecords")
    public ResponseEntity<ResponseDto> getAll(){
        List<UserModel> userModelList = userService.getAll();
        ResponseDto responseDto = new ResponseDto("All the Records: ",userModelList,null);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @GetMapping("/getRecordByEmail")
    public ResponseEntity<ResponseDto> getByEmail(@RequestParam String email){
        UserModel userModel = userService.getByEmail(email);
        ResponseDto responseDto = new ResponseDto("Data of Given Email: ",userModel,null);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PutMapping("/updateByEmail")
    public ResponseEntity<ResponseDto> updateById(@RequestParam String email,@Valid @RequestBody UserDto userDto){
        UserModel userModel = userService.updateByEmail(email,userDto);
        EmailModel emailModel = new EmailModel(userModel.getEmail(),"Data Updated Using Email.",userModel.getEmail());
        iEmailService.sendEmail(emailModel);
        ResponseDto responseDto = new ResponseDto("Updated data of Given Email: ",userModel,null);
        return new ResponseEntity<>(responseDto,HttpStatus.ACCEPTED);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody UserDto userDto, @RequestParam String email){
        UserModel userModel = userService.changePassword(userDto,email);
        EmailModel emailModel = new EmailModel(userModel.getEmail(),"Password Updated..",
                "Hi "+userModel.getFirstName()+" Your Password for 'Book Store' has been changed successfully..");
        iEmailService.sendEmail(emailModel);
        ResponseDto responseDto = new ResponseDto("Password Changed ...",userModel,null);
        return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
    }

}
