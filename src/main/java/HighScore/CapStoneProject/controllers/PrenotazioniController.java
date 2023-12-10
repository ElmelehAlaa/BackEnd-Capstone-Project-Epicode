package HighScore.CapStoneProject.controllers;

import HighScore.CapStoneProject.entities.Prenotazioni;
import HighScore.CapStoneProject.entities.Utente;
import HighScore.CapStoneProject.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/prenotazioni/{userId}")
    public List<Prenotazioni> getUserPrenotazioniByUserId(@PathVariable long userId) {
        return prenotazioniService.findAllByUserId(userId);
    }

    @GetMapping("/prenotazioni/{prenotazioneId}")
    public List<Prenotazioni> getPrenotazioniByPrenotazioneId(@PathVariable long prenotazioneId) {
        return prenotazioniService.findAllByPrenotazioneId(prenotazioneId);
    }

    @PostMapping("/{ServizioId}/prenota")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void prenotaPosto(
            @PathVariable long id,
            Authentication authentication) {
        Utente user = (Utente) authentication.getPrincipal();
        prenotazioniService.PrenotaServizio(id, user);
    }
}
