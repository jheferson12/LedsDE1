package Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Data
@Getter
@Setter
public class ListDE {
    private NodeDE head;
    private NodeDE tail;
    private int size;
    private List<Led> ledList = new LinkedList<>();

    //-------VALIDAR PARA AÑADIR-------------------------------------------
    public void validAdd(Led led) throws Exception {
        if (head != null) {
            NodeDE current = head;
            while (current != null) {
                if (current.getData().getIdentification().equals(led.getIdentification())) {
                    throw new Exception("Ya existe una bombillo con la identificación" + led.getIdentification());
                }
                current = current.getNext();
            }
        }
    }


    //-----------------IMPRIMIR BOMBILLO-------------
    public List<Led> print() throws Exception {

        //vacío la lista normal que ya tengo
        ledList.clear();

        //recorro la lista agregando las bombillas de cada nodo a la lista normal
        if (head != null) {
            NodeDE temp = head;
            while (temp != null) {
                ledList.add(temp.getData());
                temp = temp.getNext();
            }
        } else {
            throw new Exception("la lista está vacía");
        }

        //retorno dicha lista normal
        return ledList;
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


    //---------------------AÑADIR BOMBILLO LED--------------------
    public void addLed(Led led) {
        NodeDE nodeDE = new NodeDE(led);

        if (head == null) {
            head = nodeDE;
        } else {
            NodeDE current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(nodeDE);

            nodeDE.setPrevious(current);
        }

        size++;
    }
    //------------AÑADIR BOMBILLO LED AL INICIO-------------
    public void addToStart(Led led) throws Exception {
        // check if bulb already exists in the list
        for (Led l : ledList) {
            if (l.getIdentification().equals(led.getIdentification())) {
                throw new Exception("Ya existe una luz con este identificador.");
            }
        }

        // create a new node
        NodeDE newNode = new NodeDE(led);

        // if the list is not empty, update head and previous node
        if (head != null) {
            newNode.setNext(head);
            head.setPrevious(newNode);
        }

        // update head
        head = newNode;
        size++;
    }

    //-----------------------BOMBILLO POR ID--------------------------
    public Led getLedById(String identification) {
        // Aquí debes implementar la lógica para buscar un Led por su ID
        // por ejemplo, puedes usar un ciclo for para recorrer una lista de Leds y buscar el que tenga el ID indicado:
        for (Led led : ledList) {
            if (led.getIdentification() == identification) {
                return led;
            }
        }
        // Si no se encuentra el Led con el ID indicado, se devuelve null:
        return null;
    }

    //---------------BOBILLOS LED HACIA ADELANTE-------------------------
    public void getLedForwards(int place) throws Exception {
        NodeDE temp = head;
        int steps = 1;
        while (temp != null) {
            if (steps == place) {
                temp.getData().setStateLed(true);
                temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));
                if (temp.getNext() != null) {
                    while (temp.getNext() != null) {
                        Thread.sleep(1000);
                        temp.getData().setStateLed(false);
                        temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));
                        temp = temp.getNext();
                        temp.getData().setStateLed(true);
                        temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));
                    }
                }
            }
            steps++;
            temp = temp.getNext();
        }
    }


    //-----------------BOMBILLOS HACIA ATRAS---------------------------
    public void getLedBackwards(int place) throws Exception {
        NodeDE temp = head;
        int steps = 1;
        while (temp != null) {
            if (steps == place) {
                temp.getData().setStateLed(true);
                temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));
                if (temp.getPrevious() != null) {
                    while (temp.getPrevious() != null) {
                        Thread.sleep(1000);
                        temp.getData().setStateLed(false);
                        temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));
                        temp = temp.getPrevious();
                        temp.getData().setStateLed(true);
                        temp.getData().setLaston(LocalTime.from(LocalDateTime.now()));
                    }
                }
            }
            steps++;
            temp = temp.getNext();
        }
    }

    //---------------------PRENDER BOMBILLO LED----------------------
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

    //-------------------APAGAR EL BOMBILLO--------------------------
    public void turnLedOff(int place) throws Exception {
        if (head == null || place < 1 || place > size) {
            throw new Exception("Error");
        }
        NodeDE temp = head;
        int steps = 1;
        while (temp != null) {
            if (steps == place) {
                temp.getData().setStateLed(false);
                temp.getData().setLastoff(LocalTime.from(LocalDateTime.now()));
                Thread.sleep(1000); // sleep for 1 second
            }
            steps++;
            temp = temp.getNext();
        }
    }

    //-----------------------BORRAR POR LUGAR--------------------------------
    public void deleteByPlace(int place) throws Exception {
        if (head == null || place < 1 || place > size) {
            throw new Exception("Error");
        }
        NodeDE temp = head;
        int steps = 1;
        while (temp != null) {
            if (steps == place) {
                NodeDE prev = temp.getPrevious();
                NodeDE next = temp.getNext();
                if (prev == null) {
                    head = next;
                } else {
                    prev.setNext(next);
                }
                if (next != null) {
                    next.setPrevious(prev);
                }
                size--;
                Thread.sleep(1000); // sleep for 1 second
            }
            steps++;
            temp = temp.getNext();
        }
    }


    //----------------MIRAR EL BOMBILLO---------------------
    public void AverageOnLeds() throws Exception {
        if(size == 0) return; // Si la lista está vacía, no hacemos nada

        // Inicializamos dos variables, una para el nodo más a la izquierda y otra para el nodo más a la derecha
        NodeDE leftNode = head;
        NodeDE rightNode = head;

        // Si la lista tiene un número par de elementos, avanzamos el nodo de la derecha para que quede justo en la mitad
        if(size % 2 == 0) {
            for(int i = 0; i < size/2; i++) {
                rightNode = rightNode.getNext();
            }
        } else {
            // Si la lista tiene un número impar de elementos, avanzamos el nodo de la derecha dos veces para que quede justo en la mitad
            for(int i = 0; i < (size-1)/2; i++) {
                rightNode = rightNode.getNext();
            }
            rightNode = rightNode.getNext();
        }

        // Encendemos los LEDs de los nodos de la izquierda y de la derecha y establecemos la fecha de encendido
        leftNode.getData().setStateLed(true);
        rightNode.getData().setStateLed(true);
        leftNode.getData().setLaston(LocalTime.now());
        rightNode.getData().setLaston(LocalTime.now());

        // Esperamos un segundo
        TimeUnit.SECONDS.sleep(1);

        // Apagamos los LEDs de los nodos de la izquierda y de la derecha y establecemos la fecha de apagado
        leftNode.getData().setStateLed(false);
        rightNode.getData().setStateLed(false);
        leftNode.getData().setLastoff(LocalTime.now());
        rightNode.getData().setLastoff(LocalTime.now());
    }





}

