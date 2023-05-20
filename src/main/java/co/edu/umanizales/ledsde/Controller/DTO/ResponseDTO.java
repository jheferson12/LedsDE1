package co.edu.umanizales.ledsde.Controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ResponseDTO {
    private int code;
    private Object data;
    private List<ErrorDTO>errorDTOS;
}
