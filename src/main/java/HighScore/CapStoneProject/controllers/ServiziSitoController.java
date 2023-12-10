package HighScore.CapStoneProject.controllers;


import HighScore.CapStoneProject.entities.ServiziSito;
import HighScore.CapStoneProject.exceptions.BadRequestException;
import HighScore.CapStoneProject.payload.NewServizioDTO;
import HighScore.CapStoneProject.services.ServiziSitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servizi")
public class ServiziSitoController {
    @Autowired
    private ServiziSitoService serviziSitoService;

    @GetMapping("")
    public Page<ServiziSito> getServiziSito(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "id") String orderBy) {
        return serviziSitoService.getServizi(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ServiziSito saveServizi(@RequestBody @Validated NewServizioDTO body, BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {

            return serviziSitoService.save(body);
        }
    }

    @GetMapping(value = "/{id}")
    public ServiziSito findById(@PathVariable long id) {
        return serviziSitoService.findServizioById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long id) {
        serviziSitoService.findByIdAndDelete(id);
    }
}
