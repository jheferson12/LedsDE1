package Controller;

import Controller.Dto.LedDTO;
import Controller.Dto.ResponseDTO;
import Model.Led;
import Service.LedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(path="/leds")
public class ListDEController {
    @Autowired
    private LedsService ledsService;
    List<Led> leds1= new LinkedList<>();

    //-------------------LISTAR LOS BOMBILLOS LED-----------------------------
    @GetMapping(path = "/getleds")
    public ResponseEntity<ResponseDTO> getLeds() throws Exception {
        return new ResponseEntity<>(new ResponseDTO(
                200,ledsService.getLedList().print(), null), HttpStatus.OK);
    }

    //-------------------AÑADIR BOMBILLO LED------------------------------
    @PostMapping(path = "/addled")
    public ResponseEntity<ResponseDTO> addLed(@RequestBody LedDTO ledDTO)  {
        Led led1 = ledsService.getLedList().getLedById(ledDTO.getIdentification());
        if (led1 == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "El ID del Led no existe", null), HttpStatus.NOT_FOUND);
        }
        Led led = new Led(ledDTO.getIdentification(), false, null, null);
        ledsService.getLedList().addLed(led);
        return new ResponseEntity<>(new ResponseDTO(
                200, "Se ha adicionado al chandoso", null), HttpStatus.OK);


    }
    //----------------VALIDAR AL BOMBILLO LED-------------------
    @GetMapping(path = "/validadd")
    public ResponseEntity<ResponseDTO> validAdd(Led led) {
        try {
            ledsService.getLedList().validAdd(led);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Se valido el bobillo " + exception.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(500,
                "no se valido el bombillo", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //--------------AÑADIR AL INICIO EL BOMBILLO LED--------------------
    @GetMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(Led led) {
        try {
            ledsService.getLedList().addToStart(led);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ResponseDTO(200,
                    "El bombillo led esta al inicio" + exception.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "No se encuentra el bombillo mire a ver ", null), HttpStatus.NOT_FOUND);
    }

    //----------------TENER EL BOMBILLO LED POR IDENTIFICACION--------------------------------
    @GetMapping(path = "/getledbyid/{identification}")
    public ResponseEntity<ResponseDTO> getLedById(String identification) {
        try {
            ledsService.getLedList().getLedById(identification);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Ya se ve la identificacion de los bombillos", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "No se encontro ninguna bombillo led para la identificacion", null), HttpStatus.NOT_FOUND);
    }
    //-------------TENER EL BOMBILLO EN LA PARTE DE ALFRENTE-------------------
    @GetMapping(path = "/getledforwards{place}")
    public ResponseEntity<ResponseDTO>getLedForwards(int place){
        try {
            ledsService.getLedList().getLedForwards(place);
        }catch (Exception exception){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Se añadio los bombillos led hacia adelante felicidades"+exception.getMessage(),null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "No se encuentra ningun bombillo led para situarlo hacia adelante",null),HttpStatus.NOT_FOUND);
    }
    //------------------TENER EL BOMBILLO EN LA PARTE DE ATRAS-----------------------------
    @GetMapping(path = "/getledbackwards/{place}")
    public ResponseEntity<ResponseDTO>getLedBackwards(int place){
        try {
            ledsService.getLedList().getLedBackwards(place);
        }catch (Exception exception){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Se añadio los bombillos para atras felicidades"+exception.getMessage(),null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "No se encuentra ningun bombillo led para situarlo en la parte de atras",null),HttpStatus.NOT_FOUND);
    }
    //---------------VER EL BOMBILLO PRENDIDO-----------------------------
    @GetMapping(path = "turnonleds/{place}")
    public ResponseEntity<ResponseDTO>turnOnLeds(int place){
        try {
            ledsService.getLedList().turnOnLeds(place);
        }catch (Exception exception){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Ya se prendio el bombillo led felicidades",null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(500,
                "No se encontro ningun bombillo led error del servidor",null),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //--------------------VER EL BOMBILLO APAGADO-----------------------
    @GetMapping(path = "/turnledoff/{place}")
    public ResponseEntity<ResponseDTO>turnLedOff(int place){
        try {
            ledsService.getLedList().turnLedOff(place);
        }catch (Exception exception){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Se apago el bombillo led cumplio la mision"+exception.getMessage(),null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "tenemos problemas con el tema de apagar el bombillo tenga cuidado",null),HttpStatus.NOT_FOUND);
    }
    //-----------------BORRAR POR LUGAR-------------------------
    @GetMapping(path = "/deletebyplace/{place}")
    public ResponseEntity<ResponseDTO>deleteByPlace(int place){
        try {
            ledsService.getLedList().deleteByPlace(place);
        }catch (Exception exception){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "Se borro el bombillo por el lugar felicidades",null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "tiene un error este epndiente no lo puedo añadir",null),HttpStatus.OK);
    }
    //--------------PROMEDIO DE TODOS LOS BOMBILLOS AÑADIDOS----------------------------
    @GetMapping(path = "/averageonleds")
    public ResponseEntity<ResponseDTO>AverageOnLeds(){
        try {
            ledsService.getLedList().AverageOnLeds();
        }catch (Exception exception){
            return new ResponseEntity<>(new ResponseDTO(200,
                    "se promedio la cantidad de bombillos",null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(404,
                "no se promedio los bombillos",null),HttpStatus.NOT_FOUND);
    }

}















