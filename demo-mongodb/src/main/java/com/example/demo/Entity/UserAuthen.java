package com.example.demo.Entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo.common.UserTrackerCommon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "account")
public class UserAuthen extends UserTrackerCommon {
    @Id
    private ObjectId _id;
    private String username;
    private String password;
    private String token;
}