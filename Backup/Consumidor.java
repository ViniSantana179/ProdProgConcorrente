package Fabrica;

public class Consumidor implements Runnable {

    private Esteira esteira;
    private Fabrica fabrica;
    
    public Consumidor(Esteira esteira, Fabrica fabrica) {
        this.esteira = esteira;
        this.fabrica = fabrica;
    }
    
    @Override
    public void run() {
        while (fabrica.getEstoque() > 0) {
            esteira.consumir();

            fabrica.setEstoque(fabrica.getEstoque() + 5);
        }
    }
}
