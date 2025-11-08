package com.homebite_backend.Controllers;


import com.homebite_backend.Services.FoodService;
import com.homebite_backend.model.FoodItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import com.homebite_backend.HomebiteConnectBackendApplication;
import com.homebite_backend.Services.UserService;
import com.homebite_backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class UserControler {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;


    @GetMapping("/Frontpage")
    public String showFrontPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        System.out.println("Session ID: " + session.getId() +
                " User ID: " + (loggedInUser != null ? loggedInUser.getId() : "null"));


        if (loggedInUser == null) {
            return "redirect:/login"; // redirect if not logged in
        }

        model.addAttribute("userName", loggedInUser.getName());
        model.addAttribute("userEmail", loggedInUser.getEmail());

        // Fetch food list for display
        List<FoodItem> foodList = foodService.getAll();
        System.out.println("Food list size: " + foodList.size());
        model.addAttribute("foodList", foodList);

        return "Frontpage"; // Thymeleaf template
    }


    @PostMapping("/adduser")
    public String addUser(@ModelAttribute User user) {
        // ❌ Remove encoding from controller
        // user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.adduser(user); // service will handle encoding
        return "redirect:/login";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // clear the stored session data
        System.out.println("👋 User logged out successfully!");
        return "redirect:/login";
    }



    @GetMapping("/Profile")
    public String profilePage(HttpSession session, Model model, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loggedInUser);
        return "Profile";
    }


    @GetMapping("/getuser/{id}")
    @ResponseBody
    public User getUser(@PathVariable int id) {
        return userService.getuser(id);
    }


    @PutMapping("/updateuser/{id}")
    @ResponseBody
    public String updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        boolean updated = userService.updateUser(id, updatedUser);
        return updated ? "User updated successfully" : "User not found";
    }


    @DeleteMapping("/deleteuser/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? "User deleted successfully" : "User not found";
    }

    @GetMapping("/getallusers")
    @ResponseBody
//    @ResponseBody
    public List<User> getFoods() {
        return userService.getallusers();
    }


@PostMapping("/loginUser")
public String loginUser(@RequestParam String email,
                        @RequestParam String password,
                        Model model,
                        HttpServletRequest request) {

    User user = userService.findByEmail(email);
    if (user == null) {
        model.addAttribute("error", "Invalid email or password!");
        return "login";
    }
    boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
    if (passwordMatches) {
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("loggedInUser", user);

        return "redirect:/Frontpage";
    } else {
        model.addAttribute("error", "Invalid email or password!");
        return "login";
    }





}

    @PutMapping("/updateProfile")
    @ResponseBody
    public String updateProfile(@RequestBody User updatedUser, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "User not logged in!";
        }

        loggedInUser.setName(updatedUser.getName());
        loggedInUser.setEmail(updatedUser.getEmail());
        // If you want, you can allow updating password too
        // loggedInUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        userService.adduser(loggedInUser); // save updates (same as save)
        session.setAttribute("loggedInUser", loggedInUser); // refresh session

        return "Profile updated successfully!";
    }

    @GetMapping("/userFrontpage")
    public String frontpage(HttpSession session, Model model, HttpServletResponse response) {
        // Add no-cache headers to prevent browser caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // redirect if not logged in
        }
        model.addAttribute("userName", loggedInUser.getName());
        model.addAttribute("userEmail", loggedInUser.getEmail());

        return "Frontpage"; // your thymeleaf template name
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword,
                                Model model) {
        boolean updated = userService.updateUserPassword(email, passwordEncoder.encode(newPassword));

        if (!updated) {
            model.addAttribute("error", "Email not found");
            return "resetPasswordPage"; // show reset form with error
        }

        return "redirect:/login"; // <- Send redirect to /login URL
    }


}





