package HighScore.CapStoneProject.controllers;

import HighScore.CapStoneProject.entities.Utente;
import HighScore.CapStoneProject.exceptions.BadRequestException;
import HighScore.CapStoneProject.payload.NewUserDTO;
import HighScore.CapStoneProject.payload.UserLoginDTO;
import HighScore.CapStoneProject.payload.UserLoginSuccessDTO;
import HighScore.CapStoneProject.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "API gestione utenti")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public UserLoginSuccessDTO login(@RequestBody UserLoginDTO body) {
        return new UserLoginSuccessDTO(authService.autheticateUser(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<ObjectError> errors = new ArrayList<>();

            validation.getFieldErrors().forEach(fieldError -> {
                String fieldName = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();
                errors.add(new ObjectError(fieldName, errorMessage));
            });

            throw new BadRequestException(errors);
        } else {
            try {
                return authService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}


