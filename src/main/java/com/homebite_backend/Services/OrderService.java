package com.homebite_backend.Services;

import com.homebite_backend.Repo.FoodRepo;
import com.homebite_backend.Repo.OrderRepository;
import com.homebite_backend.model.FoodItem;
import com.homebite_backend.model.OrderEntity;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;



    @Autowired
    private FoodRepo foodRepository;

    public OrderEntity placeOrder(OrderEntity order) {
        return orderRepo.save(order);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepo.findAll();
    }

    public boolean updateOrder(Long id, OrderEntity updatedOrder) {
        return orderRepo.findById(id).map(o -> {
            o.setFoodItemId(updatedOrder.getFoodItemId());
            o.setFoodImage(updatedOrder.getFoodImage());
            o.setFoodName(updatedOrder.getFoodName());
            o.setUnitPrice(updatedOrder.getUnitPrice());
            o.setQuantity(updatedOrder.getQuantity());
            o.setTotalPrice(updatedOrder.getTotalPrice());
            o.setCustomerName(updatedOrder.getCustomerName());
            o.setPhone(updatedOrder.getPhone());
            o.setAddress(updatedOrder.getAddress());
            o.setCity(updatedOrder.getCity());
            o.setPincode(updatedOrder.getPincode());
            o.setOrderStatus(updatedOrder.getOrderStatus());
            orderRepo.save(o);
            return true;
        }).orElse(false);
    }

    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);
    }




    public List<OrderEntity> getOrdersForUser(String userEmail) {
        return orderRepo.findAllByUserEmail(userEmail); // call via instance
    }

}
