package Fabrica;
public class Main {
    public static void main(String[] args) {
        Fabrica fabrica = new Fabrica(500, 5);
        fabrica.fabricarPeca();
        fabrica.consumirPecas();
    }
}
