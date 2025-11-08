package com.homebite_backend.DTO;

import org.springframework.web.multipart.MultipartFile;

public class SwapDto {

    private String dishName;
    private String category; // Veg / Non-Veg / Dessert
    private String description;
    private String location;
    private String quantity;
    private String preferredSwapFor;
    private MultipartFile imageFile; // for image upload

    //  Default Constructor
    public SwapDto() {}

    //  Getters and Setters
    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPreferredSwapFor() {
        return preferredSwapFor;
    }

    public void setPreferredSwapFor(String preferredSwapFor) {
        this.preferredSwapFor = preferredSwapFor;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
