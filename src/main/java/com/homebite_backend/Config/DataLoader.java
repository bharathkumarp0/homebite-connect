package com.homebite_backend.Config;

import com.homebite_backend.Services.FoodService;
import com.homebite_backend.model.FoodItem;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner seed(FoodService foodService) {
        return args -> {
            if (foodService.getAll().isEmpty()) {
                foodService.addFood(new FoodItem("Biryani", "Spicy, flavorful rice with tender meat and fragrant spices.", "Main Course", "Non-Veg", 1, 180, "/images/biryani.jpg"));
                foodService.addFood(new FoodItem("Burger", "Juicy patty loaded with crisp veggies and signature sauce.", "Fast Food", "Veg", 1, 120, "/images/burger.png"));
                foodService.addFood(new FoodItem("Chicken Biryani", "Rich aroma of basmati rice and spiced chicken.", "Main Course", "Non-Veg", 1, 200, "/images/chickenbiryani.jpeg"));
                foodService.addFood(new FoodItem("Dosa", "Crispy golden dosa served with chutney and sambar.", "South Indian", "Veg", 1, 80, "/images/dosa.avif"));
                foodService.addFood(new FoodItem("Idli-Sambar", "Soft steamed idlis paired with tangy sambar.", "South Indian", "Veg", 1, 70, "/images/Idli-sambar.jpg"));
                foodService.addFood(new FoodItem("Kesari Bath", "Sweet aromatic dessert made from roasted semolina and ghee.", "Dessert", "Veg", 1, 60, "/images/kesiribath.webp"));
                foodService.addFood(new FoodItem("Paneer Butter Masala", "Creamy buttery gravy with paneer cubes.", "Main Course", "Veg", 1, 160, "/images/paneer-butter.webp"));
                foodService.addFood(new FoodItem("Pizza", "Cheesy crust loaded with garden-fresh toppings and herbs.", "Fast Food", "Veg", 1, 220, "/images/pizza.webp"));
                foodService.addFood(new FoodItem("Veg Fried Rice", "Wok-tossed rice with vibrant veggies and light soy flavors.", "Chinese", "Veg", 1, 130, "/images/veg-fried-rice.jpg"));
                foodService.addFood(new FoodItem("Gulab-Jamun", "Sugari.", "India", "Veg", 1, 110, "/images/Gulab-Jamun.webp"));
            }
        };
    }
}
