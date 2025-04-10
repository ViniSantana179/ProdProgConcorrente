package Fabrica;

public class Produtor implements Runnable {
    private Esteira esteira;
    private Fabrica fabrica;

    public Produtor(Esteira esteira, Fabrica fabrica) {
        this.esteira = esteira;
        this.fabrica = fabrica;
    }

    @Override
    public void run() {
        while (fabrica.getEstoque() > 0) {
            esteira.produzir();
            fabrica.setEstoque(fabrica.getEstoque() - 5);
        }
    }
    
}
