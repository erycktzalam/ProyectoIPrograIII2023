package proyecto.programacion;

public class Nodo {
    String valor;
    Nodo izquierdo;
    Nodo derecho;

    Nodo(String valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }
}