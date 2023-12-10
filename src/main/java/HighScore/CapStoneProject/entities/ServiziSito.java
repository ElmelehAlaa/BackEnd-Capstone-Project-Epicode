package HighScore.CapStoneProject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@Table(name = "servizi")
public class ServiziSito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String imgUrl;
    private String description;
    private double costo;

    @CreationTimestamp
    private String createdAt;

}
