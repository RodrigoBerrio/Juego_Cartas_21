package co.edu.univalle.poe.mvc.model;
import java.util.Random;
//CREAR MAZO , MEZCLARLO , SON 52 CARTA, SE CREA ARREGLO PARA ELLO
public class Mazo {
    private Carta [] cartas;
    private  int siguienteCarta;

    //CONSTRUCTOR
    public Mazo(){
        cartas = new Carta[52]; //arreglo de 52 espacios para objetos Carta.
        siguienteCarta = 0 ;
        crearCartas();
        mezclar();
    }

    //Crear cartas, se crean dos arreglos, uno para el número o valor y otro para el palo o figura
    private void crearCartas(){
        String [] valores_de_Carta ={"As", "2", "3", "4", "5", "6","7", "8", "9", "10", "J", "Q", "K"}; //13 VALORES
        String [] palos_figura ={"C", "D", "P", "T"}; //CUATRO PALOS

        int indice = 0;
        for(String palo:palos_figura){
            for(String valor:valores_de_Carta){
                cartas[indice] = new Carta (valor, palo);
                indice++;//Instaciamos un objeto de la clase Carta (se juntan valor y palo)
            }
        }
    }

    //Método para mezclar las cartas  usamos la clase Random, instanciamos un objeto de la clase Random (ya se importó)
    //asignamos a la variable revuelto
    //Recorra con un for la longitud del arreglo cartas (ya tiene el valor y el palo)
    //
    private void mezclar(){
        Random revuelto = new Random();
        for (int i = cartas.length -1; i>0 ; i--) {  //Recorra la lista cartas desde la 51 hacia abajo hasta 1
            int j = revuelto.nextInt(i + 1);// Obten un número cualquiera entre 0 e i en una
            // posicion cualquiera (NextInt)
            //Intercambio las cartas en vez de copiarlas para que el mazo
            // conserve siempre las 52 cartas distintas; si copiara, una se perdería y otra quedaría repetida
            Carta temporal = cartas[i]; //Creamos un objeto "temporal" de la clase Carta
            cartas[i] = cartas[j];
            cartas[j] = temporal; // y J toma la carta que está en temporal
        }

    }
    public boolean hayCartasDisponibles(){
        return siguienteCarta < cartas.length; //Verifica que todavía hay cartas, preguntando si el mazo no supera el total
    }
    //Empezamos a repartir las cartas de una en una desde arriba
    //ya que siguienteCarta empieza en cero y aumenta de uno en uno e incrementa cada vez
    public Carta repartirCarta(){
        if (!hayCartasDisponibles()){
            throw new IllegalStateException("Mazo terminado");
        }
        Carta carta = cartas[siguienteCarta];
        siguienteCarta ++;
        return carta;
    }
    //Resta del total de cartas con cartas.length la siguiente carta
    public int getCartasDisponibles(){
        return cartas.length - siguienteCarta;
    }
}











