package com.example.EventMangement;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EventMangementApplication {

	public static void main(String[] args) {

		SpringApplication.run(EventMangementApplication.class, args);

    }
}
