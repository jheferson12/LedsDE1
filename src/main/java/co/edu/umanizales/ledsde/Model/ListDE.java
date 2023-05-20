package co.edu.umanizales.ledsde.Model;
import co.edu.umanizales.ledsde.Exception.ListDEException;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Data
public class ListDE {
    private NodeDE head;
    private NodeDE tail;
    private int size;
    private List<Led> ledList = new ArrayList<>();


    //-----------------IMPRIMIR BOMBILLO-------------
    public List<Led> print() {
        ledList.clear();
        if (head != null) {
            NodeDE temp = head;
            while (temp != null) {
                ledList.add(temp.getData());
                temp = temp.getNext();
            }
        }
        return ledList;
    }

    //-------------METODO DE RESETEAR--------------------------------
    /*
    METODO RECETEAR
    ESTE METODO SU FUNCION ES QUE ESTEN TODAS LAS LUCES COMO ESTABAN ANTERIORMENTE
    COMIENZO A PREGUNTAR SI LA CABEZA ES IGUAL A NULO UNA VECES QUE SE AGA ESO COMIENZO A VER LA EXCEPCION
    DESPUES COMIENZO A IMPLEMNTAR UN NODO TEMPORAL QUE ES UN AYUDANTE
    DESPUES COMIENZO A RECORRER LA LISTA UNA VES QUE SE CUMPLA LA CONDICION,ENTONCES QUE EL ESTADO,
    SI ESTA APAGO O PRENDIDO QUE ME
    QUE SE PONGA NULO  O AL ESTADO  FALSO PARA QUE SE RESTAURE.
     */
    public void reset() throws ListDEException {
        // Reviso que los lugares ingresados sean válidos
        if (head == null) {
            throw new ListDEException("La lista está vacía");
        }

        // Defino un ayudante
        NodeDE temp = head;

        // Recorro la lista
        while (temp != null) {
            // Voy apagando
            temp.getData().setStateLed(false);
            temp.getData().setLastoff(null);
            temp.getData().setLaston(null);

            temp = temp.getNext();
        }
    }


    //---------------------AÑADIR BOMBILLO LED--------------------
    /*
    AÑADIR LED: PARA AÑADIRLO COMENZAMOS A PREGUNTAR PRIMERO SI LA CABEZA ESTA VACIA
    UNA VEZ QUE ESTA VACIA SE COMIENZA A CREAR EL NODO CON LA CLASE Y TAMBIEN CREAMOS UN AYUDANTE
    SE LLAMA ACTUAL
    UNA VEZ QUE PENSAMOS LO ANTERIOR COMENZAMOS A PREGUNTAR CON UN LOOP O CON UNA CONDICION SI ES DIFERENTE
    DE NULO EL SIGUIENTE
    Y AHI ES DONDE LA MANO DERECHA O EL GET NEXT AGARRA AL NUEVO NODO Y EL NUEVO NODO CON SU MANO IZQUIERDA
    O EL GET PREVIOUS QUE
    COJA AL NODO AYUDANTE O BUENO AL NODO ACTUAL
    Y SI SE SE CUMPLE LA CONDICION QUE EL TAMAÑO SE SUME EN UNO UNA VEZ QUE SE CUMPLA
     */
    public void addLed(Led led) {
        if (head == null) {
            NodeDE newNode = new NodeDE(led);
            head = newNode;
        } else {
            NodeDE newNode = new NodeDE(led);
            NodeDE current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
            newNode.setPrevious(current);
        }
        size++;
    }

    //------------AÑADIR BOMBILLO LED AL INICIO-------------
        /*
        AÑADIR AL INICIO:PARA AÑADIR AL COMIENZO NECESITAMOS CREAR UN METODO APARTE QUE SE AGA LA FUNCION
        COMO SIMEPRE SE PREGUNTA SI AL CABEZA ES DIFERENTE DE NULO,SI SE CUMPLE COMENZAMOS A AÑADIR LA LISTA
        Y DESPUES AÑADIR UN NODO TEMPORAL QUE SEA IGUAL A LA INSTANCIA DESPUES COMENZAMOS A PREGUNTAR SIEL NODO ES DIF
        DE NULO LA LA LISTA SE AÑADA AL COMIENZO LOS DATOS QUE LLEGUEN

         */
    public void getOrderLedsToStart() throws ListDEException {
        if (head != null) {
            ListDE listDE = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                listDE.addToStart(temp.getData());
                temp = temp.getNext();
            }
            head = listDE.getHead();
        } else {
            throw new ListDEException("No hay niños para completar esta operacion");
        }
    }

    /*
    AÑADIR AL COMIENZO: EN ESTA PARTE NECESITAMOS QUE SL LA CABEZA ES DIFERENTE DE NULO QUE SE CREA UN NUEVO NODO
    Y LA CABEZA,EN ESTA PARTE SE COMIENZAN A UNIRSE ENTRE SI MISMO DONDE IMPLEMENTAMOS QUE LA MANDO DERECHA DEL NUEVO NODO QUE COJA LA CABEZA
    Y QUE LA MANO IZQUIERDA DE LA CABEZA QUE COJA AL NUEVO NODO DE AHI DECIMOS QUE LA CABEZA ES IGUAL AL NUEVO NODO y ESO SIGNIFICA QUE SE UNAN
    ESO SI UNA VEZ DE QUE SEA VERDADERO LA TALLA SE AÑADIRIA EN 1.
      */
    public void addToStart(Led led) throws ListDEException {
        try {
            //CABEZA ES DIFERENTE DE NULO
            if (head != null) {
                //NUEVO NODO
                NodeDE newNode = new NodeDE(led);
                //EL NUEVO NODO CAMBIARIA CON LA CABEZA
                newNode.setNext(head);
                //LA CABEZA DEL PREVIOS CABIA A EL NUEVO NODO
                head.setPrevious(newNode);
                //CABEZA SEA EL NUEVO NODO
                head = newNode;
            } else {
                //CABEZA SEA IGUAL AL NUEVO NODO
                head = new NodeDE(led);
            }
        } catch (NullPointerException e) {
            // Aquí puedes manejar la excepción de alguna otra manera
            throw new ListDEException("Error: " + e.getMessage());
        }
        size++;
    }


    //---------------AÑADIR AL FINAL-------------------
    /*public void addToEnd(Led led) {
        NodeDE newNode = new NodeDE(led);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        size++;
    }

     */

    //-----------------------BOMBILLO POR ID--------------------------
    /*
    MIRAL ES ID:COMO LO VEMOS EN EL CONTROLER DEL AÑADIR ESTE CODIGO COMIENZA A VER QUE NO SE REPITA LA IDENTIFICACIO
    TENIDNO EN CUENTA QUE TAMBIEN SE PUEDES HACER DE OTRA MANERA PERO N MI CASO ES LA MANERA COMO MAS SEGURA
    COMENZAMOS A ANAIZAR LA IDENTIFICACION QUE TENIENDO EN CUENTA ESTO CON LOS DATOS QUE SE RECIVE EL NODO QUE SEA IGUAL AL
    PARAMETRO O BUENO EL INT Y COMO ES BOOLEANO SE RETORNA FALSO.
     */
    public boolean seetheid(int id) {
        NodeDE temp = head;
        while (temp != null) {
            if (temp.getData().getIdentification() == id) {
                return true;
            }
            temp = temp.getNext();
        }
        return false;
    }
    /*

     */

    /*public Led getLedById(int identification)throws ListDEException {
        NodeDE temp = head;
        while (temp != null) {
            if (temp.getData().getIdentification() == identification) {
                return temp.getData();
            }
            temp = temp.getNext();
        }
        throw new ListDEException("No se encontró ningún bombillo LED con la identificación");
    }

     */



    //---------------------PRENDER BOMBILLO LED----------------------
    /*
    PRENDER LOS BOMBILLOS: EN ESTA PARTE NECESITAMOS LA IDENTIFICACION PARA QUE PRENDA EL BOMBILLO MAS SEGURA
    EN ESTA PARTE COEMNZAMOS A VER UN CONDICIONAL QUE SI LA CABEZA ES NULO ENTONCES EXCEPCION DESPUES SE COMIENZA A ÑADIR
    UN TIPO DE CONDICION QUE LOS DATOS DE EL NODO TEMPORAL TIENE QUE LLEGAR COMO IDENTIFICACION PARA QUE SE LEA LA IDENTIFICACION
    DESPUES SE COMENIZA A PONER EN TRUE EL ESTADO YA QUE NORMALMENTE LO AÑADI EN EL CONTROLLER COMO FALSO
    DESPUES SE IMPORTA LA CLASE LOCALTIME Y ES DONDE SALE LA HORA EN QUE SE DEMORA Y PUES SE HACE LA ESPERA DE UN SEGUNDO

     */
    public void turnLedOn(int identification) throws Exception {
        if (head == null) {
            throw new Exception("La lista de LEDs está vacía");
        }

        NodeDE temp = head;

        while (temp != null) {
            if (temp.getData().getIdentification() == identification) {
                temp.getData().setStateLed(true);
                temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));
                Thread.sleep(1000); // sleep for 1 second
                return; // Encender el LED y salir del método
            }

            temp = temp.getNext();
        }

        throw new Exception("No se encontró el bombillo LED con la identificación especificada");
    }


    //-------------------APAGAR EL BOMBILLO--------------------------
    /*
        APAGAR EL BOMBILLO: PUES ES SIMILAR AL ANTERIOR PERO TENIENDO EN ECUENTA QUE EL ESTADO DE LED ES TRUE EN
        PRENDER AQUI ES FALSO ENTONCES LO NORMAL SE PREGUNAT SI CABEZA ES NULO,DESPUES SE AÑADE EL NODO O BUENO UN AYUDAMNTE
        Y DESPUES SE HACE UN LOOP Y DECIR UNA CONDICOON Y SI SE CUMPLA QUE ME PONGA FALSO EL ESTAODO Y SE USSA EL SLEEP O UNA
        ESPERA DE UN SEGUNDO
     */
    public void turnLedOff(int identification) throws Exception {
        if (head == null) {
            throw new Exception("La lista de LEDs está vacía");
        }

        NodeDE temp = head;

        while (temp != null) {
            if (temp.getData().getIdentification() == identification) {
                temp.getData().setStateLed(false);
                temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));
                Thread.sleep(1000); // sleep for 1 second
                return; // Apagar el LED y salir del método
            }

            temp = temp.getNext();
        }

        throw new Exception("No se encontró el bombillo LED con la identificación especificada");
    }
    /*
    ALGORITMO DE LIGHTSHOW EN MI CODIGO COMIENZO A IMPLEMENTAR CLASES ADICIONALES COMO LOCALTIME Y EL SLEEP
    EL FUNCIONAMIENTO DEL METODO ES QUE PRIMERO ME PREGUNTE QUE SI CABEZA NO TIENE DATOS, SI LO CUMPLE QUE ME LANCE
    LA EXCEPCION,COMIENZO A CREAR VARIABLES TIPO ENTERO PARA QUE ME AÑADA LOS PASOS Y TAMBIEN QUE ME CALCULE EL INDICE DEL ELEMENTO.

    AHORA NECESITO UN LOOP DENTRO DE UNA CONDICION PARA QUE LOS PASOS Y LA OTRA VARIABLE SE CUMPLA ,UNA VEZ QUE SE
    CUMPLA LA CONDICION SE COMIENZA A IMPLEMENTAR EL ESTADO DEL BOMBILLO COMO BOOLEANO Y DESPUES QUE CUANDO SE PREDA
    ME SALGA LA HORA QUE SE DEMORO EN PRENDER LAS LUCES.

    EN ESTA PARTE SE NECESITA CONDICIONES QUE NO SE PUEDE AVECES CUMPLIR CON LA CONDICION
    ENTONCES IMPLEMENTAMOS UN ELSE IF PARA QUE LOS PASOS SEA IGUAL A LA MITAD O MIDDLE. UNA VEZ QUE SE AÑADE ESO
    ENTONCES QUE SOLAMENTE ME AÑADA LA HORA EN QUE SE PRENDIO EL BOMILLO YA QUE EN ESTA PARTE SE PRENDE EN  LOS EXTEMOS.

    YA CUANDO QUEREMOS APAGAR EL BOMBILLO SE TIENE QUE VER LA FECHA DEL BOMBILLO TANTO PRENDIDO COMO APAGADO ESO SI TENER EN CUENTA
    QUE USANDO EL SLEEP TIENE QUE TENER DIFERENCIAS CON EL TIEMPO PRENDIO O APAGADO Y DESPUES ES DONDE LOS PASOS SE COMIENZAN A SUMAR EN 1
    SI ESO SE CUMPLE Y TENIENDO EN CUENTA EL NODO INICIAL LLAMADO TEMPORAL QUE SEA IGUAL A EL SIGUIENTE DEL NODO TEMPORAL ES DECIR (LA MANO DERECHA) SEA IGUAL AL NODO
     */
    //METODO QUE PRENDE LOS EXTERIORES Y EN LA MITAD SE PREDAN
    public void lightShow() throws ListDEException, InterruptedException {
        if (head == null) {
            throw new ListDEException("La lista de bombillas está vacía");
        }

        int middle = (size + 1) / 2;
        NodeDE temp = head;
        int steps = 1;

        while (temp != null) {
            if (steps == 1 || steps == size) {
                temp.getData().setStateLed(true);
                temp.getData().setLaston(LocalTime.now());
            } else if (steps == middle) {
                TimeUnit.SECONDS.sleep(1);
                temp.getData().setStateLed(true);
                temp.getData().setLaston(LocalTime.now());
                temp.getData().setStateLed(true);

                // Aquí se apaga el bombillo en la mitad
                temp.getData().setStateLed(false);
                temp.getData().setLastoff(LocalTime.now());
                temp.getData().setLaston(LocalTime.now());
            } else {
                temp.getData().setStateLed(false);
                temp.getData().setLastoff(LocalTime.now());
                temp.getData().setLaston(LocalTime.now());
            }


            steps++;
            temp = temp.getNext();

        }
    }


}



















