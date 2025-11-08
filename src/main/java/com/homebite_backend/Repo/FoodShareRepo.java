package com.homebite_backend.Repo;

import com.homebite_backend.model.FoodShare;
import com.homebite_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodShareRepo extends JpaRepository<FoodShare, Long> {
    List<FoodShare> findByUser(User user);

}
