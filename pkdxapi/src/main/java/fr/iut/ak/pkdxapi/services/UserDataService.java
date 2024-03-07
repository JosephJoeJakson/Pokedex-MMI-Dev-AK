package fr.iut.ak.pkdxapi.services;

import fr.iut.ak.pkdxapi.models.UserDTO;
import fr.iut.ak.pkdxapi.repositories.UserRepository;
import fr.iut.ak.pkdxapi.models.UserData;

import org.springframework.stereotype.Service;

@Service
public class UserDataService {

    private final UserRepository userRepository = null;

    public void registerUser(UserDTO userDTO) {
        // Pas admins 
        UserData userData = new UserData(userDTO.login(), userDTO.password(), false); 
        userRepository.save(userData);
    }
}
