package com.homebite_backend.Services;

import com.homebite_backend.Repo.FoodItemRepository;
import com.homebite_backend.model.Food;
import com.homebite_backend.model.FoodItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    @Autowired
    private FoodItemRepository foodRepo;

    public FoodItem addFood(FoodItem food) {
        return foodRepo.save(food);
    }

    public List<FoodItem> getAll() {
        return foodRepo.findAll();
    }

    public boolean updateFood(Long id, FoodItem updatedFood) {
        return foodRepo.findById(id).map(f -> {
            f.setName(updatedFood.getName());
            f.setDescription(updatedFood.getDescription());
            f.setCategory(updatedFood.getCategory());
            f.setFoodType(updatedFood.getFoodType());
            f.setQuantity(updatedFood.getQuantity());
            f.setPrice(updatedFood.getPrice());
            f.setImageUrl(updatedFood.getImageUrl());
            foodRepo.save(f);
            return true;
        }).orElse(false);
    }

    public void deleteFood(Long id) {
        foodRepo.deleteById(id);
    }

}
