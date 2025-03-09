package com.example.CustomerTracking.Service;

import com.example.CustomerTracking.Entity.User;
import com.example.CustomerTracking.Repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder sha256PasswordEncoder;


    public User register(User user) {
        String  hashedPassword = sha256PasswordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepo.save(user);
    }

    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    public User getUserByUserId(Long uid) {
        return userRepo.findById(uid).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User updateUser(int id, User updatedUser) {
        User user =    userRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no user id : " + id)
        );
        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());
        user.setEmail(updatedUser.getName());
        String hashPassword = sha256PasswordEncoder.encode(updatedUser.getPassword());
        user.setPassword(hashPassword);
        user.setGender(updatedUser.getGender());

        return  userRepo.save(user);
    }

    public User deleteUser(Long id) {
        User user =  userRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no user id : " + id)
        );
        userRepo.delete(user);
        return user;
    }
}
