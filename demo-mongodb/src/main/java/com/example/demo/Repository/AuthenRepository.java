package com.example.demo.Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.Entity.UserAuthen;

public interface AuthenRepository extends MongoRepository<UserAuthen, String>{
	List<UserAuthen> findByUsername(String username);
	
	UserAuthen findBy_id(ObjectId _id);  
}
