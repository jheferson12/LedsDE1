package co.edu.umanizales.ledsde.Service;

import co.edu.umanizales.ledsde.Model.ListDE;
import lombok.Data;

import org.springframework.stereotype.Service;
@Service
@Data
public class LedsService {
    private ListDE ledList;

    public LedsService() {
        this.ledList = new ListDE();
    }


}
