package com.bezkoder.spring.data.mongodb.repository;

import com.bezkoder.spring.data.mongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface UserRepository extends MongoRepository<User, String> {
  List<User> findByName(String name);
  List<User> findByEmail(String email);
}
