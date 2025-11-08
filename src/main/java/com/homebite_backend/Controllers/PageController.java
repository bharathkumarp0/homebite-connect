package com.homebite_backend.Controllers;

import com.homebite_backend.Services.FoodService;
import com.homebite_backend.model.FoodItem;
import com.homebite_backend.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    private FoodService foodService;




    @GetMapping("/Orders")
    public String ordersPage() {
        return "Orders"; // templates/Orders.html
    }



    @GetMapping("/profile")
    public String profilePage() {
        return "Profile"; // templates/Profile.html
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "singUp"; // templates/singUp.html
    }


    @GetMapping("/about")
    public String aboutpage(){

        return "About";
    }



    @GetMapping("/Payment")
    public String payment(){

        return "Payment";
    }
    @GetMapping("/Landpage")
    public  String landpage(){
        return "Landpage";
    }





    @GetMapping("/Conformationpage")
    public String confirmOrderPage() {
        return "Conformationpage"; // matches your Thymeleaf template name and route
    }


   @GetMapping("/reset")
    public  String resetpassword(){
        return "reset";
   }

   @GetMapping("/AI")
    public String AI(){
        return "AI";
   }

}
