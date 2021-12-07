package com.bezkoder.spring.data.mongodb.controller;

import com.bezkoder.spring.data.mongodb.model.User;
import com.bezkoder.spring.data.mongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;



    @CrossOrigin(origins = "http://localhost:8081")
    @RestController
    @RequestMapping("/api")
    public class UserController {

        @Autowired
        UserRepository userRepository;


        @GetMapping("/users")
        public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String name) {
            try {
                List<User> users = new ArrayList<User>();

                if (name == null)
                    userRepository.findAll().forEach(users::add);
                else
                    userRepository.findByName(name).forEach(users::add);

                if (users.isEmpty()) {
                    RestTemplate restTemplate = new RestTemplate();
                    //https://jsonplaceholder.typicode.com/users
                 //   HttpEntity entity = new HttpEntity<>();

                    String url = "https://jsonplaceholder.typicode.com/users ";


                    User user = new User();
                  //  ResponseEntity<User> responseEntity = null;

                    HttpHeaders headers = new HttpHeaders();
                    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                    headers.set("X-COM-PERSIST", "NO");
                    headers.set("X-COM-LOCATION", "USA");

                    HttpEntity<String> entity = new HttpEntity<String>(headers);

                    ResponseEntity<User[]> responseEntity = restTemplate
                            .exchange(url, HttpMethod.GET, entity, User[].class);

                    if (responseEntity != null) {
                        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {

                           // Gson gson = new Gson();
                            //User _user = userRepository.save(new User(user.getTitle(), tutorial.getDescription(), false));

                        }

                    }
                }
                return new ResponseEntity<>(users, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/users/{id}")
        public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
            Optional<User> userData = userRepository.findById(id);

            if (userData.isPresent()) {
                return new ResponseEntity<>(userData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }




    }

