package com.example.demo.Entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "contact")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
	@Id
	private ObjectId _id;

	private String name;
	private Integer age;
	private String email;
	private String address;
}
