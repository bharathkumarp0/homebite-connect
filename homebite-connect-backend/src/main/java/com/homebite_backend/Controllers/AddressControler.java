package com.homebite_backend.Controllers;

import com.homebite_backend.Services.AddressService;
import com.homebite_backend.model.Address;
import com.homebite_backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressControler {
    @Autowired
    private AddressService addressService;
    @PostMapping("/AddAddress")
    @ResponseBody
    public String AddAddress(@RequestBody Address address){
        addressService.addaddress(address);
        return "Address Added Sucessfully";
    }
     @GetMapping("/getalladdres")
    public List<Address> getallAddress(){

        return addressService.getallAddress();
    }
    @PutMapping("/updateAddresr/{id}")
    @ResponseBody
    public String updateUser(@PathVariable int id, @RequestBody Address updatedAddress) {
        boolean updated = addressService.updateaddres(id, updatedAddress);
        if (updated) {
            return "Address updated successfully";
        } else {
            return "Address not found";
        }
    }



    @DeleteMapping("/deleteAddress/{id}")
    @ResponseBody
    public String deleteaddress(@PathVariable int id){
        addressService.deleteaddress(id);
        return " Address deleted successfully";
    }

}
