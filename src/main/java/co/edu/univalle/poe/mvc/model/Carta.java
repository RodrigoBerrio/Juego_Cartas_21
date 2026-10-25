package co.edu.univalle.poe.mvc.model;

public class Carta {
    private String valor;
    private String palo;

    //CONSTRUCTOR
    public Carta(String valor, String palo){
        this.valor = valor;
        this.palo = palo;
    }

    // MÉTOD0 GETTER
    public String getValor(){
        return valor;
    }

    public String getPalo(){
        return palo;
    }

    //TO_STRING
    //Override (Sobrescribir) significa que Todas las clases en Java heredan toString() de Object, pero ese método
    // muestra una dirección de memoria. Con @Override lo reemplazo para que la carta se muestre como valor más palo, por ejemplo AsC

    @Override
    public String toString(){
        return valor + palo;
    }

}
