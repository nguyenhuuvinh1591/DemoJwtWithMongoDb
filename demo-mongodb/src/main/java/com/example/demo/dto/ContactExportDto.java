package com.example.demo.dto;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContactExportDto {
    private int stt;
    private String id;

    private String name;
    private Integer age;
    private String email;
    private String address;
}
