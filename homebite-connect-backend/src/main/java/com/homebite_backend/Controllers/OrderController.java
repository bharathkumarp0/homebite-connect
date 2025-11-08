package com.homebite_backend.Controllers;

import com.homebite_backend.Services.OrderService;
import com.homebite_backend.model.OrderEntity;
import com.homebite_backend.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;



import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
@RestController
@RequestMapping("/orders")
public class OrderController {


 @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public OrderEntity placeOrder(@RequestBody OrderEntity order, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must be logged in");
        }
        order.setUserEmail(user.getEmail());

        return orderService.placeOrder(order);
    }


    @GetMapping("/all")
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/update/{id}")
    public boolean updateOrder(@PathVariable Long id, @RequestBody OrderEntity order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }


    @GetMapping("/userOrders")
    @ResponseBody
    public List<OrderEntity> getOrders(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return Collections.emptyList(); // or throw unauthorized error
        }
        return orderService.getOrdersForUser(user.getEmail());
    }

}
