package com.homebite_backend.Repo;

import com.homebite_backend.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.homebite_backend.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepo extends JpaRepository<Food,Integer> {

        // Search by title or description (case insensitive)
        List<Food> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);

        // Filter by category (VEG/NON_VEG/DESSERT)
        List<Food> findByCategory(String category);

        // Filter by food type (SHARE/SWAP)
        List<Food> findByFoodType(String foodType);

        // Get foods by user
        List<Food> findByUserId(int userId);

        // Custom query to get active foods only (hide expired)
        @Query("SELECT f FROM Food f WHERE f.status = 'ACTIVE'")
        List<Food> findActiveFoods();

        // Search within a category
        @Query("SELECT f FROM Food f WHERE (LOWER(f.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(f.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND f.category = :category")
        List<Food> searchByKeywordAndCategory(@Param("keyword") String keyword, @Param("category") String category);
    }




