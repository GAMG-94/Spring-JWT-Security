package com.spring.security.services.implement;

import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.persistence.repositories.UserRepository;
import com.spring.security.services.IAuthServices;
import com.spring.security.services.IJWTUtilityServices;
import com.spring.security.services.models.dtos.LoginDTO;
import com.spring.security.services.models.dtos.ResponseDTO;
import com.spring.security.services.models.validations.UserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements IAuthServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IJWTUtilityServices jwtUtilityServices;

    @Autowired
    private UserValidations userValidations;

    @Override
    public HashMap<String, String> login(LoginDTO login) throws Exception {
        try {
            HashMap<String, String> jwt = new HashMap<>();
            Optional<UserEntity> user = userRepository.findByEmail(login.getEmail());

            if (user.isEmpty()) {
                jwt.put("Error", "User no register");
                return jwt;
            }

            // VERIFICAR LA CONTRASEÑA
            if (verifyPassword(login.getPassword(), user.get().getPassword())) {
                jwt.put("jwt", jwtUtilityServices.generateJWT(user.get().getUser_id()));
            } else {
                jwt.put("Error", "Authenticated failed");
            }
            return jwt;

        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    @Override
    public ResponseDTO register(UserEntity user) throws Exception{
        try {
            ResponseDTO response = userValidations.validate(user);

            if (response.getNumOfErrors() > 0) {
                return response;
            }

            List<UserEntity> getAllUsers = userRepository.findAll();

            for (UserEntity repeatFiles : getAllUsers) {
                if (repeatFiles != null) {
                    response.setNumOfErrors(1);
                    response.setMessage("User Already Exists!!");
                    return response;
                }
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            response.setMessage("Created Successfully!!");

            return response;

        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedPassword);
    }
}
