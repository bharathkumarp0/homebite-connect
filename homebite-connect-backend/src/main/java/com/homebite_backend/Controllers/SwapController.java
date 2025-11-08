package com.homebite_backend.Controllers;

import com.homebite_backend.DTO.SwapDto;
import com.homebite_backend.Services.SwapService;
import com.homebite_backend.model.Swap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class SwapController {

    @Autowired
    private SwapService swapService;

    //  Show all swaps
    @GetMapping("/Swap")
    public String showSwapPage(Model model) {
        List<Swap> allSwaps = swapService.getAllSwaps();
        model.addAttribute("availableSwaps", allSwaps);
        model.addAttribute("mySwaps", allSwaps);
        model.addAttribute("swapDto", new SwapDto()); // for the form
        return "Swap"; // make sure file name is Swap.html
    }

    //  Handle Add Swap form submission
    @PostMapping("/addSwap")
    public String addSwap(@ModelAttribute("swapDto") SwapDto swapDto) {
        try {
            Swap swap = new Swap();
            swap.setDishName(swapDto.getDishName());
            swap.setCategory(swapDto.getCategory());
            swap.setDescription(swapDto.getDescription());
            swap.setQuantity(swapDto.getQuantity());
            swap.setPreferredSwapFor(swapDto.getPreferredSwapFor());
            swap.setLocation(swapDto.getLocation());

            MultipartFile image = swapDto.getImageFile();
            swapService.saveSwap(swap, image);

            System.out.println(" Swap added successfully: " + swapDto.getDishName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/Swap"; // redirect to show updated list
    }



    @GetMapping("/deleteSwap/{id}")
    public String deleteSwap(@PathVariable Long id) {
        swapService.deleteSwapById(id);
        return "redirect:/Swap";
    }

    //  Edit Swap (Load existing data)
    @GetMapping("/editSwap/{id}")
    public String editSwap(@PathVariable Long id, Model model) {
        Swap swap = swapService.getSwapById(id);
        model.addAttribute("swap", swap);
        return "editSwap"; // New page or popup form for editing
    }

    //  Update Swap (Submit edited form)
    @PostMapping("/updateSwap")
    public String updateSwap(@ModelAttribute Swap swap,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

        Swap existing = swapService.getById(swap.getId());

        //  Keep existing image if user didn’t upload a new one
        if (imageFile == null || imageFile.isEmpty()) {
            swap.setImageUrl(existing.getImageUrl());
        }

        //  Keep same user reference
        swap.setUser(existing.getUser());

        //  Save swap (calls your service correctly)
        swapService.saveSwap(swap, imageFile);

        return "redirect:/Swap";
    }

    @PostMapping("/acceptSwap")
    public String acceptSwap(@RequestParam Long id) throws IOException {
        Swap swap = swapService.getById(id);
        swap.setRequestAccepted(true);
        swapService.saveSwap(swap, null); // no new image
        return "redirect:/Swap";
    }







}
