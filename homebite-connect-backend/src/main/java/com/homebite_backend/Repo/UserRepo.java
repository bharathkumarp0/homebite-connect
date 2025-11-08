package com.homebite_backend.Repo;

import com.homebite_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    User findByEmail(String email);





}
