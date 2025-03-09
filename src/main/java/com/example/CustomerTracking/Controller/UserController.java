package com.example.CustomerTracking.Controller;

import com.example.CustomerTracking.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.CustomerTracking.Service.UserService;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/user/register")
    @ResponseBody
    public User register(@RequestBody User user){
        return userService.register(user);
    }

    @GetMapping("/user")
    @ResponseBody
    public List<User> allUser() {
        return userService.getAllUser();
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public User getById(@PathVariable("id") Long id) {
        return userService.getUserByUserId(id);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable("id") int id, @RequestBody User user) {
        return userService.updateUser(id, user);

    }
    @DeleteMapping("/user/{id}")
    public User deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }
}
