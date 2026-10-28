21 Black Jack – Duelo contra el computador

Primer miniproyecto de la materia Fundamentos de Programación Orientada a Eventos 
Integrante
Rodrigo Berrío
CÓDIGO 2417422

Es un juego de 21 (Blackjack) donde una persona juega contra el computador. El jugador empieza con 1.000 fichas, hace una apuesta y recibe dos cartas. Puede pedir más cartas o plantarse. Después juega el computador, que pide cartas mientras tenga menos de 17. Gana quien quede más cerca de 21 sin pasarse.

HERRAMIENTAS TECNOLÓGICAS:
Java 21
IntelliJ IDEA
Scene Builder (solo si se quiere ver o cambiar el diseño de la ventana)
Conexión a internet la primera vez, para que Maven descargue JavaFX
Cómo ejecutarlo
Descargar o clonar este repositorio.
Abrir la carpeta del proyecto en IntelliJ IDEA y esperar a que termine de cargar Maven.
Ir a src/main/java/co/edu/univalle/poe/mvc/Launcher.java.
Darle al botón verde ▶ para ejecutar.
Cómo se juega
Escribir una apuesta (múltiplo de 10, mínimo 10 y sin pasarse del saldo).
Presionar Iniciar ronda.
Usar Pedir carta o Plantarse.
Ver el resultado y el saldo actualizado. Se puede jugar otra ronda mientras queden fichas.
Cómo está organizado

Seguí el patrón Modelo–Vista–Controlador que vimos en clase:

model: Carta, Mazo y Repartir (las reglas del juego).
view: hello-view.fxml (la ventana, hecha con Scene Builder).
controller: RepartirController (conecta los botones con las reglas).
