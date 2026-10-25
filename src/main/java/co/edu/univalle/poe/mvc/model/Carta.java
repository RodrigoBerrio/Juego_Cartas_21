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

    public int getValorCarta(){
        if (valor.equals("As")){  //Compáreme el valor con As, si es igual retorne 11
            return 11;
        }
        if (valor.equals("J")|| valor.equals("K") || valor.equals("Q")){ //Las letras valen 10, excepto As vale 11
            return 10; // Si el valor es J, K o Q, asígnale el valor a 10 por eso retorno 10
        }
        return Integer.parseInt(valor); // Método de java que convierte texto (número) en número (número) no string

    }

    //TO_STRING
    //Override (Sobrescribir) significa que Todas las clases en Java heredan toString() de Object, pero ese método
    // muestra una dirección de memoria. Con @Override lo reemplazo para que la carta se muestre como valor más palo, por ejemplo AsC

    @Override
    public String toString(){
        return valor + palo;
    }

}
