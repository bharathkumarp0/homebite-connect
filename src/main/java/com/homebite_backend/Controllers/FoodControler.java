package com.homebite_backend.Controllers;

import com.homebite_backend.Repo.FoodRepo;
import com.homebite_backend.Repo.UserRepo;
import com.homebite_backend.Services.FoodService;
import com.homebite_backend.Services.OrderService;
import com.homebite_backend.model.Food;
import com.homebite_backend.model.FoodItem;
import com.homebite_backend.model.OrderEntity;
import com.homebite_backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")

public class FoodControler {

    @Autowired
    private FoodService foodService;
    @Autowired
    private UserRepo userRepo;

    public FoodControler(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/addfood")
    public String addFood(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("foodType") String foodType,
            @RequestParam("quantity") int quantity,
            @RequestParam("userId") int userId,
            @RequestParam("image") MultipartFile image
    ) throws IOException {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Save image to folder
        String folder = "uploads/";
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path path = Path.of(folder + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        FoodItem food = new FoodItem();
        food.setName(title);
        food.setDescription(description);
        food.setCategory(category);
        food.setFoodType(foodType);
        food.setQuantity(quantity);
//        food.s(user);
        food.setImageUrl(path.toString()); // store file path

        foodService.addFood(new FoodItem());
        return "Food added successfully";
    }
    @GetMapping("/allfoods")
    @ResponseBody
    public List<FoodItem> getallfood(){


       return foodService.getAll();
    }






}
