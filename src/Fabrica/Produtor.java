package Fabrica;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Produtor implements Runnable {
    private Esteira esteira;
    private Fabrica fabrica;

    public Produtor(Esteira esteira, Fabrica fabrica) {
        this.esteira = esteira;
        this.fabrica = fabrica;
    }

    @Override
    public void run() {
        try {
            while (fabrica.getEstoque() > 0) {
                System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - PRODUTOR] - Produzindo peça...");
                Thread.sleep(2000);
                esteira.repor();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
