package com.user.user.model;

import com.user.user.dto.UserDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "User")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate dob;
    private String address;

    public UserModel(UserDto userDto) {
        this.firstName = userDto.firstName;
        this.lastName = userDto.lastName;
        this.email = userDto.email;
        this.password = userDto.password;
        this.dob = userDto.dob;
        this.address = userDto.address;
    }
    public UserModel(String email, UserDto userDto) {
        this.email = email;
        this.firstName = userDto.firstName;
        this.lastName = userDto.lastName;
        this.password = userDto.password;
        this.dob = userDto.dob;
        this.address = userDto.address;
    }

}
