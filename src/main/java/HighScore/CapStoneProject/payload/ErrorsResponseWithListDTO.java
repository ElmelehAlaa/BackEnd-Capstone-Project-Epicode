package HighScore.CapStoneProject.payload;

import java.util.Date;
import java.util.List;

public record ErrorsResponseWithListDTO(
        Date timestamp,
        List<String> errorsList) {
}
