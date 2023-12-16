package HighScore.CapStoneProject.payload;

import HighScore.CapStoneProject.Enum.StatoPrenotazione;
import jakarta.validation.constraints.NotNull;

public record PrenotazioniDTO(
        @NotNull(message = "Stato prenotazione non valido!")

        StatoPrenotazione prenotazioneStato
) {
}
