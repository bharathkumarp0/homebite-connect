package com.homebite_backend.Services;

import com.homebite_backend.Repo.UserRepo;
import com.homebite_backend.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;





    public void adduser(User user){
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }



    public List<User> getallusers() {
        return userRepo.findAll();
    }

    public User getuser(int id){
        return userRepo.findById((int) id).orElse(null);
    }
    public boolean updateUser(int id, User updatedUser) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
//            user.setPhone(updatedUser.getPhone());
            userRepo.save(user);  // Save updated user
            return true;
        }
        return false;
    }

    public boolean deleteUser(int id){
        if(userRepo.existsById(id)){
            userRepo.deleteById(id);
            return true;  // successfully deleted
        }
        return false; // user not found
    }



    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Transactional
    public boolean updateUserPassword(String email, String encodedPassword) {
        User user = userRepo.findByEmail(email);
        if(user == null) return false;
        user.setPassword(encodedPassword);
     userRepo.save(user);
        return true;
    }


}
