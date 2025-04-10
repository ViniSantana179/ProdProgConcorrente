package Fabrica;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;

public class Esteira {
    public int capacidade;
    private Fabrica fabrica;
    private Semaphore empty;
    private Semaphore full;
    private Semaphore mutex;

    public Esteira(Fabrica fabrica, int capacidade) {
        this.capacidade = capacidade;
        this.fabrica = fabrica;
        this.empty = new Semaphore(capacidade);
        this.full = new Semaphore(0);
        this.mutex = new Semaphore(1);
    }

    public void repor() {
        try {
            empty.acquire();
            mutex.acquire();
            
            System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - ESTEIRA] - Repondo esteira...");
            Thread.sleep(500);

            capacidade--;
            fabrica.setEstoque(fabrica.getEstoque() - 1);

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

            System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - ESTEIRA] - Consumindo esteira...");
            Thread.sleep(500);
            
            capacidade++;

            mutex.release();
            empty.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
