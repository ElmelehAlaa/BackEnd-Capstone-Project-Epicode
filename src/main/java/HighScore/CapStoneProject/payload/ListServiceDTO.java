package HighScore.CapStoneProject.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ListServiceDTO(@NotEmpty(message = "il titolo é obbligatorio!")
                             String title,
                             @NotEmpty(message = "la descrizione è obbligatoria!")
                             String description,
                             
                             @NotEmpty(message = "la foto è obbligatoria!")
                             String imgUrl,
                             @NotNull(message = "l'id del servizio è obbligatorio!")
                             long idService


) {
}
