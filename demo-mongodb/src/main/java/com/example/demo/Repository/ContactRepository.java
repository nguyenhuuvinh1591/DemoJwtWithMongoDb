package com.example.demo.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.Entity.Contact;

public interface ContactRepository extends MongoRepository<Contact, String> {  
    Contact findBy_id(ObjectId _id);  
}