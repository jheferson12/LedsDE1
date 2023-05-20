package co.edu.umanizales.ledsde.Controller;

import co.edu.umanizales.ledsde.Controller.DTO.ResponseDTO;
import co.edu.umanizales.ledsde.Exception.ListDEException;
import co.edu.umanizales.ledsde.Model.Led;
import co.edu.umanizales.ledsde.Service.LedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/leds")
public class ListDEController {
    @Autowired
    private LedsService ledsService;

    //-------------------LISTAR LOS BOMBILLOS LED-----------------------------
    @GetMapping
    public ResponseEntity<ResponseDTO> getLeds()  {
        return new ResponseEntity<>(new ResponseDTO(
                200,ledsService.getLedList().print(), null), HttpStatus.OK);
    }

    //-------------------AÑADIR BOMBILLO LED------------------------------
    @GetMapping(path = "/orderledstostart")
    public ResponseEntity<ResponseDTO> getOrderPetsToStart() {
        try {
            if (ledsService.getLedList() != null) {
                ledsService.getLedList().getOrderLedsToStart();
                return new ResponseEntity<>(new ResponseDTO(200,
                        "Se ha organizado la lista con las luces al comienzo con exito",
                        null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDTO(409,
                        "No se puede realizar la acción, tenga cuidado", null), HttpStatus.BAD_REQUEST);
            }
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(400,
                    "Se ha producido un error al ordenar la lista de las luces: " + e.getMessage(), null),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500,
                    "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //--------------AÑADIR AL INICIO EL BOMBILLO LED--------------------
    @PostMapping(path = "/addled/{identification}")
    public ResponseEntity<ResponseDTO> addLed(@PathVariable int identification) {
        try {
            // Verificar si el LED ya existe
            boolean isLedRegistered = ledsService.getLedList().seetheid(identification);

            if (isLedRegistered) {
                return new ResponseEntity<>(new ResponseDTO(400, "Ya hay un LED con el mismo ID", null),
                        HttpStatus.BAD_REQUEST);
            }

            // Crear el objeto Led y agregarlo a la lista
            Led newLed = new Led(identification, false);
            ledsService.getLedList().addLed(newLed);

            return new ResponseEntity<>(new ResponseDTO(200, "El LED se ha agregado correctamente", null),
                    HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ResponseDTO(500, "Error al agregar el LED: " + exception.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //----------------TENER EL BOMBILLO LED POR IDENTIFICACION--------------------------------
    /*@GetMapping(path = "/getledbyid/{identification}")
    public ResponseEntity<ResponseDTO> getLedById(@PathVariable int identification) {
        try {
            ledsService.getLedList().getLedById(identification);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Ya se ve la identificacion de los bombillos", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "No se encontro ninguna bombillo led para la identificacion", null), HttpStatus.NOT_FOUND);
    }

     */

    @GetMapping(path = "turnonleds/{identification}")
    public ResponseEntity<ResponseDTO> turnOnLed(@PathVariable int identification) {
        try {
            ledsService.getLedList().turnLedOn(identification);
            return new ResponseEntity<>(new ResponseDTO(200, "El bombillo LED se ha encendido con éxito", null), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ResponseDTO(500, "No se encontró el bombillo LED con la identificación especificada", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //--------------------VER EL BOMBILLO APAGADO-----------------------
    @GetMapping(path = "/turnledoff/{identification}")
    public ResponseEntity<ResponseDTO> turnLedOff(@PathVariable int identification) {
        try {
            ledsService.getLedList().turnLedOff(identification);
            return new ResponseEntity<>(new ResponseDTO(200, "Se apagó el bombillo LED exitosamente", null), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ResponseDTO(500, "No se pudo apagar el bombillo LED: " + exception.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/reset")
    public ResponseEntity<ResponseDTO> reset() {
        try {
            ledsService.getLedList().reset();
            return new ResponseEntity<>(new ResponseDTO(200, "Las luces han sido reseteadas correctamente", null), HttpStatus.OK);
        } catch (ListDEException exception) {
            return new ResponseEntity<>(new ResponseDTO(400, "No se puede resetear las luces: " , null), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(path = "/lightshow")
    public ResponseEntity<ResponseDTO>lightShow()throws InterruptedException{
        try {
            ledsService.getLedList().lightShow();
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Felicidades ya puede mirar el resultado :)",null),HttpStatus.OK);
        }catch (ListDEException listDEException){
            return new ResponseEntity<>(new ResponseDTO(400,
                    "no puede apagar y prender los extremos :(",null),HttpStatus.BAD_REQUEST);
        }
    }


}















