package Fabrica;
import java.util.concurrent.Semaphore;

public class Esteira implements Runnable {
    private int capacidade;
    private Semaphore empty;
    private Semaphore full;
    private Semaphore mutex;

    public Esteira(Fabrica fabrica, int capacidade) {
        this.capacidade = capacidade;
        this.empty = new Semaphore(capacidade);
        this.full = new Semaphore(0);
        this.mutex = new Semaphore(1);
    }

    public void produzir() {
        try {
            empty.acquire();
            mutex.acquire();
            
            capacidade--;
            System.out.println("[ESTEIRA] - Produzindo ");

            mutex.release();
            full.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
 
    public void consumir() {
        try {
            full.acquire();
            mutex.acquire();

            System.out.println("Consumindo");
            capacidade++;

            mutex.release();
            empty.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
