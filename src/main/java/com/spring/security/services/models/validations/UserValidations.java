package com.spring.security.services.models.validations;

import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.services.models.dtos.ResponseDTO;

public class UserValidations {

    public ResponseDTO validate (UserEntity user) {
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setNumOfErrors(0);

        if (user.getFirstName() == null ||
            user.getFirstName().length() < 3 ||
            user.getFirstName().length() > 16
        ) {
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("The first name field cannot be empty and cannot be less than 3 characters or more than 16 characters.");
        }

        if (user.getLastName() == null ||
            user.getLastName().length() < 2 ||
            user.getLastName().length() > 36
        ) {
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("The last name field cannot be empty and cannot be less than 3 characters or more than 36 characters.");
        }

        if (user.getEmail() == null ||
            !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("The email is invalid");
        }

        if (user.getPassword() == null ||
            !user.getPassword().matches("^(?=.*\\d)(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")) {
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors() + 1);
            responseDTO.setMessage("The password must have between 8 and 16 characters, at least 1 number, one upper case, one lower case and special characters.");
        }

        return responseDTO;
    }
}
