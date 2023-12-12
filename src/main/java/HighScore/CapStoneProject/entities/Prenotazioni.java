package HighScore.CapStoneProject.entities;


import HighScore.CapStoneProject.Enum.StatoPrenotazione;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
public class Prenotazioni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Utente utente;


    @ManyToOne
    @JoinColumn(name = "servizioId")
    private ServiziSito servizio;

    @Enumerated(EnumType.STRING)
    private StatoPrenotazione prenotazioneStato;

    @CreationTimestamp
    private Date createdAt;
}
