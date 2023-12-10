package HighScore.CapStoneProject.controllers;

import HighScore.CapStoneProject.Enum.Role;
import HighScore.CapStoneProject.entities.Utente;
import HighScore.CapStoneProject.exceptions.BadRequestException;
import HighScore.CapStoneProject.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping("")
    public Page<Utente> getUsers(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String orderBy) {
        return utenteService.getUsers(page, size, orderBy);
    }

    @GetMapping(value = "/{id}")
    public Utente findById(@PathVariable long id) {
        return utenteService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Utente findByIdAndUpdate(@PathVariable long id, @RequestBody @Validated Utente body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return utenteService.findByIdAndUpdate(id, body);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long id) {
        utenteService.findByIdAndDelete(id);
    }

    @GetMapping("/members")
    public List<Utente> findByRole(@RequestParam Role role) {
        return utenteService.findByRole(role);
    }

    @GetMapping("/search")
    public Utente findByEmail(@RequestParam String email) {
        return utenteService.findByEmail(email);
    }

    @PatchMapping("/{id}/image")
    @PreAuthorize("hasAnyAuthority('USER' ,'PLAYER')")
    public Utente uploadImage(@RequestParam("image") MultipartFile file, @PathVariable long id) throws IOException {
        return utenteService.uploadPicture(file, id);
    }


}
