package com.example.demo.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.User;

public interface UserRepository extends MongoRepository<User, Integer> {
	User findByEmail(String email);

}
