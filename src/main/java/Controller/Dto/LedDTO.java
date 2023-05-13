package Controller.Dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Data

public class LedDTO {
    private String identification;
    private boolean stateLed;

    private LocalDateTime laston;

    private LocalDateTime lastoff;
}



