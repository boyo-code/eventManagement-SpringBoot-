package com.example.EventMangement;


import model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class EventMangementApplication {

	public static void main(String[] args) {

		SpringApplication.run(EventMangementApplication.class, args);
	}
@GetMapping
	public List<User>getUser(){
		return List.of();
    }
}
