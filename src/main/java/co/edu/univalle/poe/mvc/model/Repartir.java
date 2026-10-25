package co.edu.univalle.poe.mvc.model;
import java.util.LinkedList;

public class Repartir {

    //Atributos
    private Mazo mazo;
    private LinkedList<Carta> mano; //Se declara el atributo privado, que es una lista enlazada
    //que solo guarda objetos de clase Cartas, llamada mano

    //Constructor, iniciamos repartiendo las cartas
    public Repartir(){
        mazo = new Mazo(); //Se crea el objeto mazo, aquí se ejecuta el método constructor
        // cartas = new Carta[52]; //arreglo de 52 espacios para objetos Carta.
        //siguienteCarta = 0 ;crearCartas() y mezclar();

        mano = new LinkedList<>(); //Ya va a empezar el juego, mano vacía
        mano.addLast(mazo.repartirCarta());  //llamo al método repartirCarta para que reparta
        mano.addLast(mazo.repartirCarta()); // Para iniciar el juego se requieren dos cartas al jugador,
                                            //Por eso se ejecuta dos veces
    }
    //Pedir cartas
    public boolean pedirCarta(){
        if (!mazo.hayCartasDisponibles()){  //Se pregunta ¿Hay cartas disponibles, si falso entonces se acabó
             return false;
        }
        mano.addLast(mazo.repartirCarta());
        return true;
    }
    public boolean hayCartasDisponibles(){
        return mazo.hayCartasDisponibles();
    }

    //Averigua cuantas cartas tiene el jugador, retorna entero
    public int getCantidadCartas(){
        return mano.size(); // size es parte de los métodos de linkedList  que dice cuantas cartas tiene el jugador
    }

    //getMano devuelve una copia de la lista para proteger la mano original, el concepto de encapsulamiento
    //el controlador puede consultarla y mostrarla, pero no modificarla
    public LinkedList<Carta> getMano(){  //
        return new LinkedList<>(mano);
    }

}
