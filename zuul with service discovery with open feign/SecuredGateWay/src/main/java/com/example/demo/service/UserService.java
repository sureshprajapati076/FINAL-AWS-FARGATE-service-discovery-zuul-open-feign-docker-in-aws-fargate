package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    User findOne(String email);
    User save(User user);
   
}
