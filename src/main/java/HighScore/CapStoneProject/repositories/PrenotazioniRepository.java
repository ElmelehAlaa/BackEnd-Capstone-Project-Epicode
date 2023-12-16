package HighScore.CapStoneProject.repositories;

import HighScore.CapStoneProject.Enum.StatoPrenotazione;
import HighScore.CapStoneProject.entities.Prenotazioni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazioni, Long> {
    List<Prenotazioni> findAllByUtenteId(Long userId);

    List<Prenotazioni> findAllByServizioId(Long servizioId);

    List<Prenotazioni> findAllByPrenotazioneStato(StatoPrenotazione statoPrenotazione);

    Optional<Prenotazioni> findById(long id);

}
