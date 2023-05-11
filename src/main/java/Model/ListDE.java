package Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.List;
@Data
@Getter
@Setter
public class ListDE {
    private NodeDE head;
    private NodeDE tail;
    private int size;
    private List<Led> ledList = new ArrayList<>();

    public void validAdd (Led led) throws Exception{

        if (head == null) {

        } else {

            NodeDE current = head;

            //reviso si ya existe una bombillo con la id de la bombillo siendo agregada
            if (current.getData().getIdentification().equals(led.getIdentification()) ){
                throw new Exception("Ya existe una bombillo con la identificación" + led.getIdentification());
            }


            //recorro la lista hasta que llegue al final
            while (current.getNext() != null) {

                //reviso si ya existe una bombillo con la id de la bombillo siendo agregada
                if (current.getData().getIdentification().equals(led.getIdentification()) ){
                    throw new Exception("Ya existe una bombillo con la identificación" + led.getIdentification());
                }

                current = current.getNext();

            }
        }
    }

    public List<Led> print() throws Exception{

        //vacío la lista normal que ya tengo
        ledList.clear();

        //recorro la lista agregando las bombillas de cada nodo a la lista normal
        if (head != null){
            NodeDE temp = head;
            while (temp != null){
                ledList.add(temp.getData());
                temp = temp.getNext();
            }
        } else {
            throw new Exception("la lista está vacía");
        }

        //retorno dicha lista normal
        return ledList;
    }

    public void invert() {
        NodeDE current = head;
        NodeDE prev = null;
        NodeDE next = null;
        while (current != null) {
            next = current.getNext();
            current.setNext(prev);
            prev = current;
            current = next;
        }
        head = prev;
    }



    public void reset() throws Exception {
        if (head == null) {
            throw new Exception("La lista está vacía");
        }

        NodeDE temp = head;

        while (temp != null) {
            Led led = temp.getData();
            led.setStateLed(false);
            led.setTurnoffled(null);
            led.setTurnonled(null);
            temp = temp.getNext();
        }
    }


    public void addToStart(Led bulb) throws Exception {
        // check if bulb already exists in the list
        for (Led l : ledList) {
            if (l.getIdentification().equals(bulb.getIdentification())) {
                throw new Exception("Ya existe una luz con este identificador.");
            }
        }

        // create a new node
        NodeDE newNode = new NodeDE(bulb);

        // if the list is not empty, update head and previous node
        if (head != null) {
            newNode.setNext(head);
            head.setPrevious(newNode);
        }

        // update head
        head = newNode;
        size++;
    }

    public void add(Led bulb) throws Exception{

        //si la cabeza es nula, la nueva cabeza es la bombilla ingresada
        if (head == null) {
            head = new NodeDE(bulb);

        } else {

            //sino, defino un nuevo nodo
            NodeDE newNode = new NodeDE(bulb);
            NodeDE current = head;

            //reviso si ya existe una bombilla con la id de la bombilla siendo agregada
            if (current.getData().getIdentification().equals(bulb.getIdentification()) ){
                throw new Exception("Ya existe una bombilla con la identificación" + bulb.getIdentification());
            }

            //recorro la lista hasta que llegue al final
            while (current.getNext() != null) {

                //reviso si ya existe una bombilla con la id de la bombilla siendo agregada
                if (current.getData().getIdentification().equals(bulb.getIdentification()) ){
                    throw new Exception("Ya existe una bombilla con la identificación" + bulb.getIdentification());
                }

                current = current.getNext();

            }

            //en el final, meto la bombilla
            newNode.setPrevious(current);
            current.setNext(newNode);

        }

        //aumento el tamaño de la lista
        size++;
    }

    public void deleteByPlace(int place) throws Exception{

        //reviso que los lugares ingresados sean válidos
        if (head == null) {
            throw new Exception("la lista está vacía");
        } else if (place < 1) {
            throw new Exception("el ingresado es menor a 1");
        } else if (place > size){
            throw new Exception("el lugar ingresado es mayor al tamaño de la lista");
        }

        //defino un ayudante y un contador para saber cuantos pasos llevo
        NodeDE temp = head;
        int steps = 1;

        //recorro la lista
        while (temp != null){

            //reviso si el puesto es igual al puesto buscado
            if (steps == place){
                NodeDE prev = temp.getPrevious();
                NodeDE next = temp.getNext();

                //reviso si es la cabeza
                if (prev == null){
                    //si sí, la cabeza será la siguiente, es decir, quito la mascota de la cabeza
                    head = next;
                }else{
                    //sino, hago que el previo tenga como siguiente a el siguiente de temp, es decir, saco a temp de la lista
                    prev.setNext(next);
                }

                //reviso si es el último
                if (next != null){
                    //si next no es nulo, es decir, si no estoy en el final de la fila, hago que next tenga de previo a prev
                    next.setPrevious(prev);
                }

                //después de haberlo removido, resto del tamaño de la fila
                size--;
            }
            steps++;
            temp = temp.getNext();
        }

    }

    public int turnOnLeds(int place) {
        int size = getSize();
        int halfSize = size / 2;
        NodeDE current = head;
        boolean turnedOn = false;

        // Recorremos la lista hasta la mitad
        for (int i = 0; i < halfSize; i++) {
            current = current.getNext();
        }

        // A partir de la mitad, prendemos los bombillos hasta el final de la lista
        while (current != null) {
            if (!current.getData().isOn()) {
                current.getData().turnOn();
                turnedOn = true;
            }
            current = current.getNext();
        }

        return place;
    }


    public void turnLedOff(int place) throws Exception{

        //reviso que los lugares ingresados sean válidos
        if (head == null) {
            throw new Exception("la lista está vacía");
        } else if (place < 1) {
            throw new Exception("el ingresado es menor a 1");
        } else if (place > size){
            throw new Exception("el lugar ingresado es mayor al tamaño de la lista");
        }

        //defino un ayudante y un contador para saber cuantos pasos llevo
        NodeDE temp = head;
        int steps = 1;

        //recorro la lista
        while (temp != null){

            //reviso si el puesto es igual al puesto buscado
            if (steps == place){
                temp.getData().setStateLed(false);
                temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));
            }
            steps++;
            temp = temp.getNext();
        }
    }

    public void getLedForwards(int place) throws Exception, Exception {

        //defino un ayudante y un contador para saber cuantos pasos llevo
        NodeDE temp = head;
        int steps = 1;

        //recorro la lista para llegar al lugar donde quiero que empiece la ola
        while (temp != null) {

            //reviso si el puesto es igual al puesto buscado
            if (steps == place) {

                //cuando llegue prendo el primer bombillo
                temp.getData().setStateLed(true);
                temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                //reviso si hay mas bombillos adelante
                if (temp.getNext() != null){

                    //empiezo otro ciclo para seguir la ola después del primer bombillo
                    while (temp.getNext() != null) {

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e){
                            throw new Exception("se ha interrumpido la ola");
                        }

                        //apago el bombillo con el que empecé
                        temp.getData().setStateLed(false);
                        temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));

                        //paso al siguiente bombillo y lo prendo
                        temp = temp.getNext();
                        temp.getData().setStateLed(true);
                        temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                    }

                }

            }

            //aumento el contador de pasos y sigo con el siguiente bombillo
            steps++;
            temp = temp.getNext();

        }
    }

    public void getLedBackwards (int place) throws Exception, Exception {

        //defino un ayudante y un contador para saber cuantos pasos llevo
        NodeDE temp = head;
        int steps = 1;

        //recorro la lista para llegar al lugar donde quiero que empiece la ola
        while (temp != null) {

            //reviso si el puesto es igual al puesto buscado
            if (steps == place) {

                //cuando llegue prendo el primer bombillo
                temp.getData().setStateLed(true);
                temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                //reviso si hay mas bombillos adelante
                if (temp.getPrevious() != null){

                    //empiezo otro ciclo para seguir la ola después del primer bombillo
                    while (temp.getPrevious() != null) {

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e){
                            throw new Exception("se ha interrumpido la ola");
                        }

                        //apago el bombillo con el que empecé
                        temp.getData().setStateLed(false);
                        temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));

                        //paso al siguiente bombillo y lo prendo
                        temp = temp.getPrevious();
                        temp.getData().setStateLed(true);
                        temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                    }

                }

            }

            //aumento el contador de pasos y sigo con el siguiente bombillo
            steps++;
            temp = temp.getNext();

        }

    }

    public void lightShow() throws Exception, Exception {

        //primero reviso que la lista no esté vacía para mandar una excepción
        if (head == null){
            throw new Exception("La lista de bombillas está vacía");
        }

        //primero reviso si la cantidad de bombillas es par o impar
        if (size % 2 == 0){

            //aquí, la lsita sería par
            int middle = (size / 2);


            //defino un ayudante y un contador para saber cuantos pasos llevo
            NodeDE temp = head;
            int steps = 1;

            //recorro la lista para llegar al lugar donde quiero que empiece la ola
            while (temp != null) {

                //reviso si el puesto es igual al puesto buscado
                if (steps == (middle + 1)){

                    //cuando llegue prendo el primer bombillo
                    temp.getData().setStateLed(true);
                    temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                    //creo un nuevo ayudante para que haga la ola hacia atras y hago que prenda el primer bombillo de ese lado
                    NodeDE temp2 = temp.getPrevious();
                    temp2.getData().setStateLed(true);
                    temp2.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                    //reviso si hay mas bombillos adelante
                    if (temp.getNext() != null){

                        //empiezo otro ciclo para seguir la ola después del primer bombillo
                        while (temp.getNext() != null) {

                            //espero 1 segundo
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e){
                                throw new Exception("se ha interrumpido la ola");
                            }

                            //apago el bombillo con el que empecé
                            temp.getData().setStateLed(false);
                            temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));

                            //apago el bombillo con el que empecé con temp2
                            temp2.getData().setStateLed(false);
                            temp2.getData().setLastoff(LocalTime.from(LocalDateTime.now()));

                            //paso al siguiente bombillo y lo prendo
                            temp = temp.getNext();
                            temp.getData().setStateLed(true);
                            temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                            //con temp 2 me devuelvo un bombillo y lo prendo
                            temp2 = temp2.getPrevious();
                            temp2.getData().setStateLed(true);
                            temp2.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                        }

                    }

                }

                //aumento el contador de pasos y sigo con el siguiente bombillo
                steps++;
                temp = temp.getNext();

            }


        } else{
            //aquí la lista sería impar
            int middle = (size / 2) + 1;


            //defino un ayudante y un contador para saber cuantos pasos llevo
            NodeDE temp = head;
            int steps = 1;

            //recorro la lista para llegar al lugar donde quiero que empiece la ola
            while (temp != null) {

                //reviso si el puesto es igual al puesto buscado
                if (steps == middle){

                    //cuando llegue prendo el primer bombillo
                    temp.getData().setStateLed(true);
                    temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                    //creo un nuevo ayudante para que haga la ola hacia atras y hago que prenda el primer bombillo de ese lado
                    NodeDE temp2 = temp;

                    //reviso si hay mas bombillos adelante
                    if (temp.getNext() != null){

                        //empiezo otro ciclo para seguir la ola después del primer bombillo
                        while (temp.getNext() != null) {

                            //espero 1 segundo
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e){
                                throw new Exception("se ha interrumpido la ola");
                            }

                            //apago el bombillo con el que empecé
                            temp.getData().setStateLed(false);
                            temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));

                            //apago el bombillo con el que empecé con temp2
                            temp2.getData().setStateLed(false);
                            temp2.getData().setLastoff(LocalTime.from(LocalDateTime.now()));

                            //paso al siguiente bombillo y lo prendo
                            temp = temp.getNext();
                            temp.getData().setStateLed(true);
                            temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                            //con temp 2 me devuelvo un bombillo y lo prendo
                            temp2 = temp2.getPrevious();
                            temp2.getData().setStateLed(true);
                            temp2.getData().setLaston(LocalTime.from(LocalDateTime.now()));

                        }

                    }

                }

                //aumento el contador de pasos y sigo con el siguiente bombillo
                steps++;
                temp = temp.getNext();

            }

        }

    }

}

