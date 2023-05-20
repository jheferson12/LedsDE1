package co.edu.umanizales.ledsde.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalTime;
@Data
@AllArgsConstructor
public class Led {
    private int identification;

    private boolean stateLed;

    private LocalTime laston;

    private LocalTime lastoff;


    public Led(int identification,boolean stateLed) {
        this.identification = identification;
        this.stateLed = stateLed;
    }




    public void turnOn() {
    }

    public boolean isOn() {
        return true;
    }


}
