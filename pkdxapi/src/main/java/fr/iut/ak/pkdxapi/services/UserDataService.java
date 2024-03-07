package fr.iut.ak.pkdxapi.services;

import fr.iut.ak.pkdxapi.errors.UserAlreadyExistException;
import fr.iut.ak.pkdxapi.models.UserDTO;
import fr.iut.ak.pkdxapi.repositories.UserRepository;
import fr.iut.ak.pkdxapi.models.UserData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserDataService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private boolean usernameExist(String login){
        return userRepository.findByLogin(login).isPresent();
    }

    public void registerUser(UserDTO userDTO) throws UserAlreadyExistException {
        if (usernameExist(userDTO.login())) {
            throw new UserAlreadyExistException("User already exists", HttpStatus.CONFLICT); // Ou HttpStatus.UNPROCESSABLE_ENTITY
        }
        
        UserData userData = new UserData(userDTO.login(), passwordEncoder.encode(userDTO.password()), false); 
        userRepository.save(userData);
    }
}