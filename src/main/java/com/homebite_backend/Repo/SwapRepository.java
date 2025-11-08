package com.homebite_backend.Repo;

import com.homebite_backend.model.Swap;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SwapRepository extends JpaRepository<Swap, Long> {
    List<Swap> findByUserId(Long userId);
    List<Swap> findByDishNameContainingIgnoreCase(String dishName);
    List<Swap> findByLocationContainingIgnoreCase(String location);
    List<Swap> findByCategory(String category);
    List<Swap> findByUser_Id(Long userId);
}
