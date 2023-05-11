package Controller;

import Controller.Dto.LedDTO;
import Controller.Dto.ResponseDTO;
import Model.Led;
import Service.LedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/lisdecontroller")
public class ListDEController {
    @Autowired
    private LedsService ledsService;

    @GetMapping("/bulbs")
    public ResponseEntity<ResponseDTO> getBulbs() {

        // Inicializar una lista de bombillas
        List<Led> bulbs = new ArrayList<>();

        try {
            // Obtener la lista de bombillas usando el servicio DLListService y llamar al método "print"
            bulbs = ledsService.getLedList().print();

        } catch (Exception e) {
            // Si se produce una excepción, devolver una respuesta con código 204 y el mensaje de la excepción
            return new ResponseEntity<>(new ResponseDTO(
                    204, e.getMessage(),
                    null), HttpStatus.OK);
        }

        // Si no se produce ninguna excepción, devolver una respuesta con código 200 y la lista de bombillas
        return new ResponseEntity<>(new ResponseDTO(
                200, bulbs, null), HttpStatus.OK);
    }


    @PostMapping(path = "/addled")
    public ResponseEntity<ResponseDTO> addLed(@RequestBody LedDTO ledDTO) throws Exception {

        try {
            ledsService.getLedList().add(
                    new Led(ledDTO.getIdentification(),false,null,null));

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado el petacón",
                null), HttpStatus.OK);

    }

    @GetMapping(path = "reset")
    public ResponseEntity<ResponseDTO> reset() {

        try {

            ledsService.getLedList().reset();

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    204,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "bombillos reseteados", null), HttpStatus.OK);
    }

    @PostMapping(path = "/add")


    @GetMapping(path = "addtostart/{id}")
    public ResponseEntity<ResponseDTO> addToStart(@PathVariable String id) {

        try {

            ledsService.getLedList().addToStart(new Led(id));

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    204,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "la bombilla fue añadadida", null), HttpStatus.OK);
    }


    @GetMapping(path = "turnbulbon/{place}")
    public ResponseEntity<ResponseDTO> turnOnLeds(@PathVariable int place) {

        try {

            ledsService.getLedList().turnOnLeds(place);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    204,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "la bombilla fue encendida", null), HttpStatus.OK);
    }

    @GetMapping(path = "turnbulboff/{place}")
    public ResponseEntity<ResponseDTO> turnLedOff(@PathVariable int place) {

        try {


            ledsService.getLedList().turnLedOff(place);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    204,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "la bombilla fue encendida", null), HttpStatus.OK);
    }

    @GetMapping(path = "deletebulb/{place}")
    public ResponseEntity<ResponseDTO> deleteBulb(@PathVariable int place) {

        try {

            ledsService.getLedList().deleteByPlace(place);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    204,e.getMessage(),
                    null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "la bombilla fue añadadida", null), HttpStatus.OK);
    }

    @GetMapping(path = "waveforwards/{place}")
    public ResponseEntity<ResponseDTO> waveForwards(@PathVariable int place) {

        if (place > ledsService.getLedList().getSize()){
            return new ResponseEntity<>(new ResponseDTO(
                    200, "el lugar es mayor al tamaño de la fila", null), HttpStatus.OK);
        } else if (place <  0){
            return new ResponseEntity<>(new ResponseDTO(
                    200, "el lugar es menor a 0 de la fila", null), HttpStatus.OK);
        }

        try {
            ledsService.getLedList().getLedBackwards(place);
        } catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(
                    200, e.getMessage(), null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200, "se hizo una ola", null), HttpStatus.OK);
    }

    @GetMapping(path = "wavebackwards/{place}")
    public ResponseEntity<ResponseDTO> waveBackwards(@PathVariable int place) {

        if (place > ledsService.getLedList().getSize()){
            return new ResponseEntity<>(new ResponseDTO(
                    200, "el lugar es mayor al tamaño de la fila", null), HttpStatus.OK);
        } else if (place <  0){
            return new ResponseEntity<>(new ResponseDTO(
                    200, "el lugar es menor a 0 de la fila", null), HttpStatus.OK);
        }

        try {
            ledsService.getLedList().getLedForwards(place);
        } catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(
                    200, e.getMessage(), null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200, "se hizo una ola", null), HttpStatus.OK);
    }

    @GetMapping(path = "lightshow")
    public ResponseEntity<ResponseDTO> lightshow() {

        try {
            ledsService.getLedList().lightShow();
        } catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(
                    200, e.getMessage(), null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200, "se hizo una ola", null), HttpStatus.OK);
    }

}




