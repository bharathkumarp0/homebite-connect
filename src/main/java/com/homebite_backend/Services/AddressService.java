package com.homebite_backend.Services;

import com.homebite_backend.Repo.AddressRepo;
import com.homebite_backend.model.Address;
import com.homebite_backend.model.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AddressRepo addressRepo;


    public void addaddress(Address address){

        addressRepo.save(address);
    }

    public List<Address> getallAddress(){

        return addressRepo.findAll();
    }


public  void deleteaddress(int id){

        addressRepo.deleteAllById(Collections.singleton(id));
}

    public boolean updateaddres(int id, Address updatedAddress) {
            Optional<com.homebite_backend.model.Address> optionalAddress = addressRepo.findById(id);
            if (optionalAddress.isPresent()) {
                Address address = optionalAddress.get();
                address.setAddressLine(updatedAddress.getAddressLine());
                address.setCity(updatedAddress.getCity());
                address.setState(updatedAddress.getState());
                address.setPincode(updatedAddress.getPincode());
                address.setCreatedAt(updatedAddress.getCreatedAt());
                address.setUser(updatedAddress.getUser());


                addressRepo.save(address);  // Save updated user
                return true;
            }
            return false;
        }
    }

