package HighScore.CapStoneProject.controllers;

import HighScore.CapStoneProject.Enum.StatoPrenotazione;
import HighScore.CapStoneProject.entities.Prenotazioni;
import HighScore.CapStoneProject.entities.Utente;
import HighScore.CapStoneProject.exceptions.BadRequestException;
import HighScore.CapStoneProject.payload.PrenotazioniDTO;
import HighScore.CapStoneProject.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
    @Autowired
    private PrenotazioniService prenotazioniService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Prenotazioni> getUsers(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String orderBy) {
        return prenotazioniService.getPrenotazioni(page, size, orderBy);
    }

    @GetMapping("/{userId}")
    public List<Prenotazioni> getUserPrenotazioniByUserId(@PathVariable long userId) {
        return prenotazioniService.findAllByUserId(userId);
    }

    @GetMapping("id/{prenotazioneId}")
    public Prenotazioni getPrenotazioniByPrenotazioneId(@PathVariable long prenotazioneId) {
        return prenotazioniService.findById(prenotazioneId);
    }

    @PostMapping("/{ServizioId}/prenota")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazioni prenotaPosto(
            @PathVariable("ServizioId") long id,
            Authentication authentication) {
        Utente user = (Utente) authentication.getPrincipal();
        return prenotazioniService.PrenotaServizio(id, user);
    }

    @PutMapping("id/{prenotazioneId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Prenotazioni updatePrenotazione(@PathVariable long prenotazioneId, @RequestBody PrenotazioniDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {

            if (!isValidEnum(body.prenotazioneStato())) {
                throw new BadRequestException("Invalid enum value for prenotazioneStato");
            }

            return prenotazioniService.findByIdAndUpdate(prenotazioneId, body);
        }
    }

    private boolean isValidEnum(StatoPrenotazione stato) {
        return Arrays.asList(StatoPrenotazione.values()).contains(stato);
    }

}
