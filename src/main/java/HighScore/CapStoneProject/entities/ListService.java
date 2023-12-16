package HighScore.CapStoneProject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@Table(name = "lista_servizi")
public class ListService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String imageUrl;
    private String description;

    @CreationTimestamp
    private String createdAt;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "servizi_id")
    private ServiziSito serviziSito;
}



