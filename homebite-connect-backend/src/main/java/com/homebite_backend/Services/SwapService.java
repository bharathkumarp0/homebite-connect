package com.homebite_backend.Services;

import com.homebite_backend.DTO.SwapDto;
import com.homebite_backend.Repo.SwapRepository;
import com.homebite_backend.Repo.UserRepo;
import com.homebite_backend.model.Swap;
import com.homebite_backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SwapService {

    @Autowired
    private SwapRepository swapRepo;

    @Autowired
    private UserRepo userRepo;



    private final String uploadDir = "src/main/resources/static/uploads/";

    // ✅ Save Swap (direct call)
    public Swap saveSwap(Swap swap, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, imageFile.getBytes());
            swap.setImageUrl("/uploads/" + fileName);
        }
        return swapRepo.save(swap);
    }

    // ✅ Add Swap using DTO and Principal
    public void addSwap(SwapDto swapDto, Principal principal) {
        User user = userRepo.findByEmail(principal.getName());
        if (user == null) return;

        Swap swap = new Swap();
        swap.setDishName(swapDto.getDishName());
        swap.setCategory(swapDto.getCategory());
        swap.setDescription(swapDto.getDescription());
        swap.setQuantity(swapDto.getQuantity());
        swap.setPreferredSwapFor(swapDto.getPreferredSwapFor());
        swap.setLocation(swapDto.getLocation());
        swap.setUser(user);
        swap.setUserEmail(user.getEmail());

        MultipartFile image = swapDto.getImageFile();

        if (image != null && !image.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path path = Paths.get(uploadDir + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, image.getBytes());
                swap.setImageUrl("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        swapRepo.save(swap);
    }

    //  List all swaps
    public List<Swap> listOpenSwaps() {
        return swapRepo.findAll();
    }

    //  Get all swaps except current user's
    public List<Swap> getAllSwapsExceptUser(String userEmail) {
        return swapRepo.findAll()
                .stream()
                .filter(swap -> swap.getUserEmail() == null || !swap.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());
    }

    //  Get all swaps posted by current user
    public List<Swap> getSwapsByUser(String userEmail) {
        return swapRepo.findAll()
                .stream()
                .filter(swap -> swap.getUserEmail() != null && swap.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());
    }


    public List<Swap> getAllSwaps() {
        return swapRepo.findAll();
    }

    public void deleteSwapById(Long id) {
        swapRepo.deleteById(id);
    }

    public void updateSwap(Swap swap) {
        swapRepo.save(swap);
    }



    public Swap getSwapById(Long id) {
        return swapRepo.findById(id).orElse(null);
    }

    public Swap getById(Long id) {
        return swapRepo.getById(id);
    }

    @Scheduled(fixedRate = 60000)
    public void simulateSwapRequest() {
        List<Swap> swaps = swapRepo.findAll();

        for (Swap s : swaps) {
            // if no request yet, mark as hasRequest = true
            if (!s.isHasRequest()) {
                s.setHasRequest(true);
                swapRepo.save(s);
                System.out.println("Simulated swap request for: " + s.getDishName());
            }
        }
    }
}
