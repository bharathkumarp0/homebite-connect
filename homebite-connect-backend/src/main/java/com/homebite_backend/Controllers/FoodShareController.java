package com.homebite_backend.Controllers;

import com.homebite_backend.DTO.SwapDto;
import com.homebite_backend.Services.FoodShareService;
import com.homebite_backend.Services.UserService;
import com.homebite_backend.model.FoodShare;
import com.homebite_backend.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Controller
public class FoodShareController {

    @Autowired
    private FoodShareService foodShareService;

    @Autowired
    private UserService userService;

    //  Show Share Food Page
    @RequestMapping("/Swap")
    public String showShareForm(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // redirect if not logged in
        }

        model.addAttribute("foodShare", new FoodShare());
        model.addAttribute("userName", loggedInUser.getName());
        return "ShareFood";
    }

    //  Handle Food Share Form Submission
    @PostMapping("/ShareFood")
    public String shareFood(@RequestParam("dishName") String dishName,
                            @RequestParam("description") String description,
                            @RequestParam("category") String category,
                            @RequestParam("location") String location,
                            @RequestParam("image") MultipartFile file,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        try {
            //  Get logged-in user from session
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                redirectAttributes.addFlashAttribute("error", "Please login first!");
                return "redirect:/login";
            }

            //  Ensure uploads folder exists
            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir));

            //  Save file locally
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            //  Create FoodShare object
            FoodShare foodShare = new FoodShare();
            foodShare.setDishName(dishName);
            foodShare.setDescription(description);
            foodShare.setCategory(category);
            foodShare.setLocation(location);
            foodShare.setImageUrl(fileName);

            //  Link user
            foodShare.setUser(loggedInUser);

            //  Save to DB
            foodShareService.saveFoodShare(foodShare);
            redirectAttributes.addFlashAttribute("message", "Food shared successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to upload image.");
        }

        return "redirect:/ShareFood";
    }




    //  Display All Shared Foods by Logged-in User
    @GetMapping("/sharedetails")
    public String getSharedDetails(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        List<FoodShare> sharedFoods = foodShareService.getAllSharesByUser(loggedInUser);
        model.addAttribute("sharedFoods", sharedFoods);

        System.out.println(" Shared foods fetched for user: " + loggedInUser.getEmail());
        System.out.println("🔹 Count: " + sharedFoods.size());

        return "sharedetails";
    }

    // Delete share
    @GetMapping("/deleteShare/{id}")
    public String deleteShare(@PathVariable Long id) {
        foodShareService.deleteShare(id);
        return "redirect:/sharedetails";
    }

    //  Update share
    @PostMapping("/updateShare/{id}")
    public String updateShare(@PathVariable Long id, @ModelAttribute FoodShare foodShare) {
        foodShareService.updateShare(id, foodShare);
        return "redirect:/sharedetails";
    }

    @GetMapping("/ShareFood")
    public String showShareFoodPage(Model model) {
        model.addAttribute("swapDto", new SwapDto());
        return "ShareFood";
    }








}
