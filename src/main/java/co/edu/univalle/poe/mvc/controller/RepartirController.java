package co.edu.univalle.poe.mvc.controller;

import co.edu.univalle.poe.mvc.model.Carta;
import co.edu.univalle.poe.mvc.model.Repartir;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class RepartirController {
    @FXML
    private TextArea txtMano;
    @FXML
    private Label lblCantidadCartas;
    @FXML
    private Label lblDisponibles;
    @FXML
    private Button btnPedirCarta;

    //Atributo del modelo de nombre repartir para poder comunicarse con el modelo
    private Repartir repartir;


    @FXML //Permite que JavaFX encuentre el método
    private void initialize() {
        repartir = new Repartir(); // Instancia mazo y mano inicial
        txtMano.setEditable(false); //Para que el jugador no escriba nada en el área de texto
        actualizarVista(); //Dibuja el estado inicial en pantalla
    }

    //Actualizamos la vista
    private void actualizarVista() {
        StringBuilder texto = new StringBuilder(); // Metodo de java para juntar cadenas de texto
        for (Carta c : repartir.getMano()) { //Recorre la copia de la mano ( de la encapsulación) carta por carta
            texto.append(c).append(" "); //agrega la carta al texto, y agrega un espacio ("  ") entre carta y carta
        }
        txtMano.setText(texto.toString()); //Coloca el texto ya completo en el área de texto
        lblCantidadCartas.setText("Cartas del jugador :" + repartir.getCantidadCartas());
        lblDisponibles.setText("Cartas disponibles :" + repartir.getCartasDisponibles());
        btnPedirCarta.setDisable(!repartir.hayCartasDisponibles()); //Si ya se acabaron las cartas desactiva el boton
    }
    @FXML  //permite que el FXML encuentre este método con onAction="#onPedirCarta". El nombre debe coincidir exactamente.
    private void onPedirCarta(){
        repartir.pedirCarta(); //le pide una carta al modelo, que la agrega a la mano.
        actualizarVista();  //Coloca de nuevo la mano y los contadores con la carta nueva.
    }
}
