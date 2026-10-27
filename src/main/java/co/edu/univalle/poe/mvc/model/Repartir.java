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
    private int saldo;
    private  int apuesta;
    private boolean rondaActiva;

    //Constructor, deja el juego listo sin cartas hasta que el juego halla iniciado
    public Repartir(){
        mazo = new Mazo(); //Se crea el objeto mazo, aquí se ejecuta el método constructor
        // cartas = new Carta[52]; //arreglo de 52 espacios para objetos Carta.
        //siguienteCarta = 0 ;crearCartas() y mezclar();

        mano = new LinkedList<>(); //Ya va a empezar el juego, mano vacía
        manoComputador = new LinkedList<>(); //La mano del computador

        me_planto = false;
        saldo = 1000;  //Jugador empieza con mil
        apuesta = 0;  //Aun no ha apostado
        rondaActiva = false;
    }
    //Pedir cartas
    public boolean pedirCarta(){
        if (!rondaActiva || me_planto || !mazo.hayCartasDisponibles()){  //Se pregunta ¿Hay cartas disponibles y no se planto?
             return false;                                              //y si no hay ronda activa
        }
        mano.addLast(mazo.repartirCarta());
        revisarTurnoJugador(); //LLamo a revisarTurno para ver si no se pasado del 21
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

    public void decido_Plantarme(){  // Con este método el jugador termina su partida
       if (!rondaActiva || me_planto){ //Me pregunto (No hay ronda activa o me planto
           return;
       }
        me_planto = true; //De lo contrario me planté, entonces , turno para el computador y termina la ronda
        juega_el_Computador();
        terminarRonda();
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

    public boolean laApuestaesValida(int cantidad){//Sea un número entero, múltiplo de 10.
                                                    // Sea como mínimo de 10 fichas.
	                                                //No supere el saldo disponible.
        return cantidad >= 10 && cantidad % 10 == 0 && cantidad <=saldo;
    }
    public int getSaldo(){
        return saldo;
    }
    public int getApuesta(){
        return apuesta;
    }
    public boolean apostar(int cantidad){
        if (!laApuestaesValida(cantidad)){ // Si la apuesta no es validad
            return false;
        }
        apuesta = cantidad;
        saldo = saldo - apuesta;
        return true;

    }
    public void pagar_Resultado() {
        String resultado = hallarResultado(mano, manoComputador); //Llama al métdo hallarResultado ,con la mano del apostador y del computador
        if (resultado.equals("Empate")) { //Si el resultado es empate
            saldo = saldo + apuesta; //Devuelve las fichas, por eso suma
        } else if (resultado.equals("Gana el apostador")) { //Si gana el apostador
            if (esBlackjack(mano)) { //En caso de que gane con blackjack, le dan las fichas mas 2,5
                saldo = saldo + apuesta * 5 / 2;
            } else {        //De lo contrario le dan solo el doble
                saldo = saldo + apuesta * 2;
            }
        }
        apuesta = 0;  //deja la apuesta en cero. Si el método
                    // se llamara dos veces por error, la segunda vez sumaría 0,
                    // y así el saldo se actualiza una sola vez. se resetea el valor
    }
    public boolean iniciarRonda(int cantidad) { //Recibe como parametro lo que apuesta el jugador
        if (rondaActiva || !apostar(cantidad)) { //Hay algo por lo que no se puede empezar el juego, o la apuesta no es multiplo de 10 por ejemplo
            return false;  //si ya hay una ronda activa, o no se pudo hacer la apuesta, devuelve falso.
        }
        mazo = new Mazo();  //Generar un mazo nuevo
        mano.clear();  //Aseguramos que no hay nada de la ronda anterior
        manoComputador.clear(); //Aseguramos que no hay nada de la ronda anterior

        me_planto = false;  //False significa que no se ha plantado aun
        mano.addLast(mazo.repartirCarta()); //Se reparten las dos cartas
        mano.addLast(mazo.repartirCarta());
        rondaActiva = true;  //Ya está en marcha el juego
        revisarTurnoJugador();
        return true;
    }
    private void terminarRonda() { //Cerramos la ronda
        me_planto = true; //como se plantó , sbloque al apostador
        pagar_Resultado(); //Calcular el resultado de las manos
        rondaActiva = false; //ya se acabó la ronda
    }
    private void revisarTurnoJugador() {
        if (sePaso(mano)) {  //Nos preguntamos si el apostador se paso de la mano, si es asi, llama a a terminarRonda (supera 21)
            terminarRonda();
        } else if (calcularPuntaje(mano) == 21) { //Jugador llegó a 21, entonces me planto
            decido_Plantarme();
        }
    }
    public boolean isRondaActiva() {
        return rondaActiva;
    }







}
