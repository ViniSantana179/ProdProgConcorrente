package Fabrica;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Fabrica {
    private int estoque;
    private Esteira esteira;

    public Fabrica(int estoque, int capacidadeEsteira) {
        this.estoque = estoque;
        this.esteira = new Esteira(this, capacidadeEsteira);
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public void fabricar() {
        int count = 0;

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Produtor produtor = new Produtor(esteira, this);
        Consumidor consumidor = new Consumidor(esteira, this);

        executorService.execute(produtor);
        executorService.execute(consumidor);

        System.out.println("Fabricação completa. " + count + " peças fabricadas.");
    }
}
