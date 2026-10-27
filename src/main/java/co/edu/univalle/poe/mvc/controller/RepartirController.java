package co.edu.univalle.poe.mvc.controller;

import co.edu.univalle.poe.mvc.model.Carta;
import co.edu.univalle.poe.mvc.model.Repartir;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.util.LinkedList;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class RepartirController {
    @FXML private TextField txtApuesta;
    @FXML private Label lblSaldo;
    @FXML private Label lblApuesta;

    @FXML private TextArea txtManoJugador;
    @FXML private Label lblPuntajeJugador;

    @FXML private TextArea txtManoComputador;
    @FXML private Label lblPuntajeComputador;

    @FXML private Label lblMensaje;

    @FXML private Button btnIniciarRonda;
    @FXML private Button btnPedirCarta;
    @FXML private Button btnPlantarse;

    //Atributo del modelo de nombre repartir para poder comunicarse con el modelo
    private Repartir repartir;


    @FXML
    private void initialize() {  //Como su nombre lo indica deja todo planteado para iniciar el juego
        repartir = new Repartir();  //Instanciar para arrancar el juego (la variable repartir se usa para obtener cosas de la clase Repartir
        txtManoJugador.setEditable(false); //Bloque que se escriba en el área de texto
        txtManoComputador.setEditable(false); //Bloquea que se escriba en el área de exto
        lblMensaje.setText("Escriba su apuesta y presiona para empezar juego"); //
        actualizarVista(); //Resetea todo en limpio para empezar juego
    }
    private String textoMano(LinkedList<Carta> cartas) { //recibe una mano del apostador
        StringBuilder texto = new StringBuilder(); //crea un caja vacía para ir colocando el texto, aqui se usa toString para convertirlo legible
        for (Carta c : cartas) { //Recorre con una variable c la estructura cartas
            texto.append(c).append("  "); //Agrege a la variable texto lo que le va dando c en cada pasada, el otro append
                                            //es para dar espacio entre caracteres
        }
        return texto.toString(); //convierte esa caja en texto legible, viene declarardo en la clase Carta.java
    }

    private void actualizarVista() {
        txtManoJugador.setText(textoMano(repartir.getMano()));
        txtManoComputador.setText(textoMano(repartir.getManoComputador()));
        lblPuntajeJugador.setText("Puntaje: " + repartir.calcularPuntaje(repartir.getMano()));
        lblPuntajeComputador.setText("Puntaje: " + repartir.calcularPuntaje(repartir.getManoComputador()));
        lblSaldo.setText("Saldo: " + repartir.getSaldo());
        lblApuesta.setText("Apuesta: " + repartir.getApuesta());

        boolean activa = repartir.isRondaActiva();
        btnIniciarRonda.setDisable(activa);
        txtApuesta.setDisable(activa);
        btnPedirCarta.setDisable(!activa);
        btnPlantarse.setDisable(!activa);
    }
    private void mostrarResultadoSiTermino() { //Muestra el resultado
        if (!repartir.isRondaActiva()) { //Se pregunta: Si no hay ronda activa a la clase Repartir

            //Invoca al metodo hallarResultado desde la clase Repartir, obtiene la mano del apostador y del computador
            String resultado = repartir.hallarResultado(repartir.getMano(), repartir.getManoComputador());
            //Invoca a getSaldo desde Repartir
            lblMensaje.setText(resultado + ". Saldo actual: " + repartir.getSaldo());
        }
    }
    @FXML //Permite quue el boton "Pedir carta" encuentre este método
    private void onPedirCarta() {
        repartir.pedirCarta(); //Se pide una carta al modelo
        actualizarVista(); //Se actualiza la vista
        mostrarResultadoSiTermino(); //Muestra resultados
    }
    @FXML //permite que el botón “Plantarse” encuentre este método onPLantarse
    private void onPlantarse() {
        repartir.decido_Plantarme();
        actualizarVista();
        mostrarResultadoSiTermino();
    }
    @FXML
    private void onIniciarRonda() {
        int cantidad;
        try {
            cantidad = Integer.parseInt(txtApuesta.getText().trim());
        } catch (NumberFormatException e) {
            lblMensaje.setText("La apuesta debe ser un número entero");
            return;
        }
        if (!repartir.iniciarRonda(cantidad)) {
            lblMensaje.setText("Apuesta no válida: entera, múltiplo de 10, mínima de 10 y sin superar el saldo");
            return;
        }
        lblMensaje.setText("Tu turno: pide carta o plántate");
        actualizarVista();
        if (repartir.isRondaActiva() && repartir.veinteyMedio(repartir.getMano())) {
            lblMensaje.setText("¡Veinte y medio! Puedes pedir carta o plantarte");
        }
        mostrarResultadoSiTermino();
    }

    @FXML
    private void onVerReglas() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText("Reglas del 21 Black Jack");
        alerta.setContentText("Gana quien se acerque más a 21 sin pasarse.\n"
                + "J, Q y K valen 10. El As vale 1 u 11.\n"
                + "Apuesta: entera, múltiplo de 10, mínimo 10.\n"
                + "El computador pide mientras tenga menos de 17.");
        alerta.showAndWait();
    }

    @FXML
    private void onCerrarJuego() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que quieres cerrar el juego?");
        if (alerta.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Platform.exit();
        }
    }




}
