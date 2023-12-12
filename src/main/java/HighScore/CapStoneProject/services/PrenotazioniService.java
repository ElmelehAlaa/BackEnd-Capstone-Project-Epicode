package HighScore.CapStoneProject.services;

import HighScore.CapStoneProject.Enum.StatoPrenotazione;
import HighScore.CapStoneProject.entities.Prenotazioni;
import HighScore.CapStoneProject.entities.ServiziSito;
import HighScore.CapStoneProject.entities.Utente;
import HighScore.CapStoneProject.exceptions.NotFoundException;
import HighScore.CapStoneProject.repositories.PrenotazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioniService {
    @Autowired
    private PrenotazioniRepository prenotazioniRepository;
    @Autowired
    private ServiziSitoService serviziSitoService;
    @Autowired
    private UtenteService utenteService;

    public Page<Prenotazioni> getPrenotazioni(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return prenotazioniRepository.findAll(pageable);
    }

    public List<Prenotazioni> findAllByUserId(long userId) {
        return prenotazioniRepository.findAllByUtenteId(userId);
    }

    public List<Prenotazioni> findAllByPrenotazioneId(long prenotazioneId) {
        return prenotazioniRepository.findAllByServizioId(prenotazioneId);
    }

    public List<Prenotazioni> findAllByPrenotazioneStato(StatoPrenotazione statoPrenotazione) {
        return prenotazioniRepository.findAllByPrenotazioneStato(statoPrenotazione);
    }

    public Prenotazioni PrenotaServizio(long servizioId, Utente utente) throws NotFoundException {
        ServiziSito serviziSitoFound = serviziSitoService.findServizioById(servizioId);
        Prenotazioni prenotazione = new Prenotazioni();
        prenotazione.setUtente(utente);
        prenotazione.setServizio(serviziSitoFound);
        prenotazione.setPrenotazioneStato(StatoPrenotazione.IN_ATTESA_DI_CONFERMA);
        return prenotazioniRepository.save(prenotazione);
    }

}
