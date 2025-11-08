package com.homebite_backend.model;

import jakarta.persistence.*;

@Entity
public class Swap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dishName;
    private String category;
    private String description;
    private String location;
    private String imageUrl;
    private String quantity;
    private String preferredSwapFor;
    private String userEmail;

    private boolean hasRequest = false;
    private boolean requestAccepted = false;

    public boolean isHasRequest() {
        return hasRequest;
    }

    public void setHasRequest(boolean hasRequest) {
        this.hasRequest = hasRequest;
    }

    public boolean isRequestAccepted() {
        return requestAccepted;
    }

    public void setRequestAccepted(boolean requestAccepted) {
        this.requestAccepted = requestAccepted;
    }
















    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Swap() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getPreferredSwapFor() { return preferredSwapFor; }
    public void setPreferredSwapFor(String preferredSwapFor) { this.preferredSwapFor = preferredSwapFor; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
