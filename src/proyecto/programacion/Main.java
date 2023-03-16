package proyecto.programacion;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la expresion matematica:");
        String expresion = sc.nextLine();
        if (validarExpresion(expresion)) {
            String expresionProcesada = procesar(expresion);
            // Creamos el árbol binario y lo construimos a partir de la expresión
            ArbolBinario arbol = new ArbolBinario();
            String polaca = convertirPolaca(expresionProcesada);// convierte la expresion introducida polaca o preorden
            arbol.crearArbol(polaca);// se crea el arbol a partir de la expresion
            System.out.println("-----------Arbol Grafico-----------");
            arbol.imprimirArbolVertical();
            System.out.println("------------------------------------");
            arbol.imprimirNotacionPolaca();
            // Recursividad
            double resultado = resolverExpresion(arbol.raiz);
            System.out.println("El resultado es: " + resultado);
        } else {
            System.out.println("La expresion no es valida");
        }
    }

    //Método recursivo que resuelve la expresión a partir de un nodo dado
    public static double resolverExpresion(Nodo nodo) {
        if (nodo.izquierdo == null && nodo.derecho == null) {
            return Double.parseDouble(nodo.valor);
        }
        double izquierda = resolverExpresion(nodo.izquierdo);
        double derecha = resolverExpresion(nodo.derecho);
        
        switch (nodo.valor) {
            case "+":
                return izquierda + derecha;
            case "-":
                return izquierda - derecha;
            case "*":
                return izquierda * derecha;
            case "^":
                return Math.pow(izquierda, derecha);
            case "sqrt":
                return Math.sqrt(izquierda);
        }
        return 0;
    }
    
    //Método para procesar la expresion verificando si contiene variables para posteriormente cambiarlo por un valor numerico
    public static String procesar(String expresion) {
        Scanner input = new Scanner(System.in);
        String nuevaExpresion = "";
        for (int i = 0; i < expresion.length(); i++) {
            String caracter = expresion.charAt(i) + "";
            if (!esOperador(caracter) && !esNumero(caracter) && !caracter.equals(" ") && !caracter.equals("(") && !caracter.equals(")")) {
                System.out.println("Ingresa el valor de " + caracter + ": ");
                caracter = input.nextLine();
            }
            nuevaExpresion += caracter;
        }
        return nuevaExpresion;
    }
    
     //Método para convertir la expresion a polaca o Preorden
    public static String convertirPolaca(String expresion) {
        String[] tokens = expresion.split(" ");
        Stack<String> pila = new Stack<String>();
        String polaca = "";

        for (String token : tokens) {
            if (esNumero(token) || esVariable(token)) {
                polaca += token + " ";
            }
            else if (esOperador(token)) {
                while (!pila.isEmpty() && esOperador(pila.peek()) && precedencia(token) <= precedencia(pila.peek())) {
                    polaca += pila.pop() + " ";
                }
                pila.push(token);
            }
            else if (token.equals("(")) {
                pila.push(token);
            }
            else if (token.equals(")")) {
                while (!pila.isEmpty() && !pila.peek().equals("(")) {
                    polaca += pila.pop() + " ";
                }
                if (!pila.isEmpty() && pila.peek().equals("(")) {
                    pila.pop();
                }
            }
        }

        // Sacamos de la pila todos los operadores que quedan y los agregamos a la notación polaca o Preorden
        while (!pila.isEmpty()) {
            polaca += pila.pop() + " ";
        }

        return polaca.trim();
    }
        // verificar si es un numero
    public static boolean esNumero(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean esVariable(String token) {
        return token.matches("[a-zA-Z]+");
    }
    public static boolean esOperador(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^") || token.equals("sqrt");
    } // Verificar si es un operador
    public static int precedencia(String operador) {
        if (operador.equals("^") || operador.equals("sqrt")) {
            return 3;
        } else if (operador.equals("*") || operador.equals("/")) {
            return 2;
        } else if (operador.equals("+") || operador.equals("-")) {
            return 1;
        } else {
            return 0;
        }
    }
    // Método que valida la expresion introducida por el usuario
    public static boolean validarExpresion(String expresion) {
        String[] tokens = expresion.split(" ");

        for (String token : tokens) {
            if (!esOperador(token) && !esNumero(token) && !token.equals(" ") && !token.equals("(") && !token.equals(")") && !esVariable(token)) {
                return false;
            }
        }
        return true;
    }

}
