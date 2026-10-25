package co.edu.univalle.poe.mvc.model;
import java.nio.file.FileAlreadyExistsException;
import java.util.LinkedList;

public class Repartir {

    //Atributos
    private Mazo mazo;
    private LinkedList<Carta> mano; //Se declara el atributo privado, que es una lista enlazada
    //que solo guarda objetos de clase Cartas, llamada mano
    private LinkedList<Carta> manoComputador;
    private boolean me_planto;

    //Constructor, iniciamos repartiendo las cartas
    public Repartir(){
        mazo = new Mazo(); //Se crea el objeto mazo, aquí se ejecuta el método constructor
        // cartas = new Carta[52]; //arreglo de 52 espacios para objetos Carta.
        //siguienteCarta = 0 ;crearCartas() y mezclar();

        mano = new LinkedList<>(); //Ya va a empezar el juego, mano vacía
        manoComputador new LinkedList<>(); //La mano del computador
        mano.addLast(mazo.repartirCarta());  //llamo al método repartirCarta para que reparta
        mano.addLast(mazo.repartirCarta()); // Para iniciar el juego se requieren dos cartas al jugador,
                                            //Por eso se ejecuta dos veces
        me_planto = true;
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

    public int getCartasDisponibles(){  //Método para que Controller sepa cuantas cartas disponibles hay
                                        // y a su vez Repartir consulta a Mazo
        return mazo.getCartasDisponibles();
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

    public boolean decido_Plantarme(){  // Con este método el jugador termina su partida
        me_planto = false;
        juega_el_Computador();
    }

    public LinkedList<Carta> getManoComputador() {
        return new LinkedList<>(manoComputador); //Encapsulamiento aquí , es una copia de la mano del computador
    }
    public void juega_el_Computador() {
        manoComputador.addLast(mazo.repartirCarta()); //Agrega a la lista
        manoComputador.addLast(mazo.repartirCarta()); //Agrega a la lista (Son dos cartas que pide el computador)
        while (calcularPuntaje(manoComputador) < 17) {  //Regla del juego, hágalo mientras mano sea inferior a 17
            manoComputador.addLast(mazo.repartirCarta());
        }
    }

    public int calcularPuntaje(LinkedList<Carta> cartas) {//LLega como argumento y se recibe como parametro la mano del momento
        //Declaro dos variables en cero para poder calcular
        int total = 0; //Voy sumando
        int ases = 0; //Cuenta los ases
        for(Carta c: cartas) {// recorra toda la lista de cartas primero
            total = total + c.getValorCarta();  //Vaya sumando las carta que tiene el jugador
            if (c.getValor().equals("As")){  //Si la carta es un As la cuenta como As
                ases = ases + 1;
            }
        }
        while (total > 21 && ases > 0){ // Si el total es mayor a 21 y ases mayor a ceror, reste del total 10(regla del juego)
            total = total -10;         //y resta los ases
            ases = ases - 1;
        }
        return total;
    }
    public  boolean esBlackjack(LinkedList<Carta> cartas){ //Método que detecta en la primera mano de dos si es blackjack
        return cartas.size() ==2 && calcularPuntaje(cartas)==21;
    }
    public boolean esVeintiuno(LinkedList<Carta> cartas){ //Método que detecta que sea 21 pero no blackjack
        return calcularPuntaje(cartas)==21 && !esBlackjack(cartas);
    }
    public boolean sePaso(LinkedList<Carta> cartas){ //Método que detecta si se pasó de 21
        return calcularPuntaje(cartas)> 21;
    }

    public boolean esFigura(Carta c){ //Método llamado en veinteymedio
        return c.getValor().equals("J") || c.getValor().equals("K") || c.getValor().equals("Q");
    }
    public boolean veinteyMedio(LinkedList<Carta> cartas){
        if (cartas.size()!=2){  // La mano deben ser dos carta sino termina
            return false;
        }
        Carta primera = cartas.get(0); //Se declara la variable primera y se aplica el metodo get de LinkedList para obtener la carta en la posición cero
        Carta segunda = cartas.get(1); //Se declara la variable segunda y se aplica el metodo get de LinkedList para obtener la carta en la posición uno
        boolean figuraYCuatro = esFigura(primera) && segunda.getValor().equals("4"); // es figura y cuatro?
        boolean cuatroYFigura = primera.getValor().equals("4") && esFigura(segunda);
        return figuraYCuatro || cuatroYFigura;
    }
    //El método recibe argumentos desde la clase Carta hacía parámetro con las carta barajadas
    public String hallarResultado(LinkedList<Carta> apostador, LinkedList<Carta> computador){
        if (sePaso(apostador)) {
            return "Gana el computador";
        }
        if(sePaso(computador)){
            return "Gana el apostador";
        }
        boolean blackjack_apostador = esBlackjack(apostador);
        boolean blackjack_computador = esBlackjack(computador);
        if (blackjack_apostador && blackjack_computador){//Si ambos son true, retorna empate
            return "Empate";
        }
        if(blackjack_apostador){  //Apostador obtiene blackjack
            return "Gana el apostador";
        }
        if (blackjack_computador){ //Computador obtiene blackjack
            return "Gana el computador";
        }
        //Declaramos dos variables, llamar método calcularPuntaje y comparamos para ver quien gana
        int score_apostador = calcularPuntaje(apostador);
        int score_computador = calcularPuntaje(computador);
        if(score_apostador > score_computador){
            return "Gana el apostador";
        }
        if(score_apostador < score_computador){
            return "Gana el computador";
        }
        return "Empate";
    }





}
