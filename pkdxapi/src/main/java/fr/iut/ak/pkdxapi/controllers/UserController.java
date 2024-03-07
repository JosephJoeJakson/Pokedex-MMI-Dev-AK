package fr.iut.ak.pkdxapi.controllers;

import fr.iut.ak.pkdxapi.models.UserDTO;
import fr.iut.ak.pkdxapi.services.UserDataService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserDataService userDataService = null;

    @PostMapping("/users/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        userDataService.registerUser(userDTO);
        return ResponseEntity.ok("Success");
    }
}


