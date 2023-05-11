package Service;

import Model.ListDE;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;
@Service
@Data

public class LedsService {
    private ListDE ledList;

    public LedsService() {
        ledList = new ListDE();

    }
}
