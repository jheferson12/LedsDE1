package Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalTime;
@Data
@AllArgsConstructor
public class Led {
    private String identification;

    private boolean stateLed;

    private LocalTime laston;

    private LocalTime lastoff;


    public Led(String identification) {
        this.identification = identification;
        this.stateLed = false;
    }




    public void turnOn() {
    }

    public boolean isOn() {
        return true;
    }

    public void setTurnoffled(Object o) {
    }

    public void setTurnonled(Object o) {
    }
}
