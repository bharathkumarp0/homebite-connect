package com.homebite_backend.Services;

import com.homebite_backend.Repo.FoodShareRepo;
import com.homebite_backend.model.FoodShare;
import com.homebite_backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodShareService {

    @Autowired
    private FoodShareRepo foodShareRepo;

    //  Save a new shared food
    public void saveFoodShare(FoodShare foodShare) {
        foodShareRepo.save(foodShare);
    }

    //  Get all shares by logged-in user
    public List<FoodShare> getAllSharesByUser(User user) {
        return foodShareRepo.findByUser(user);
    }

    //  Get a single shared food by ID
    public Optional<FoodShare> getShareById(Long id) {
        return foodShareRepo.findById(id);
    }

    //  Delete a shared food by ID
    public boolean deleteShare(Long id) {
        if (foodShareRepo.existsById(id)) {
            foodShareRepo.deleteById(id);
            return true;
        }
        return false;
    }

    //  Update a shared food
    public FoodShare updateShare(Long id, FoodShare updatedShare) {
        return foodShareRepo.findById(id)
                .map(existing -> {
                    existing.setDishName(updatedShare.getDishName());
                    existing.setDescription(updatedShare.getDescription());
                    existing.setCategory(updatedShare.getCategory());
                    existing.setLocation(updatedShare.getLocation());
                    existing.setImageUrl(updatedShare.getImageUrl());
                    return foodShareRepo.save(existing);
                }).orElse(null);
    }
}
