package com.user.user.repository;

import com.user.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepositry extends JpaRepository<UserModel,Long> {
    @Query(value = "select * from user where email= :email",nativeQuery = true)
    public Optional<UserModel> getByEmail(String email);

}
