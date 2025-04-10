package Fabrica;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cliente implements Runnable {
    private List<Loja> lojas;
    private List<Carro> carros;

    public void setLojas(List<Loja> lojas) {
        this.lojas = lojas;
        this.carros = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - CLIENTE] - Cliente iniciado.");
            while (true) {
                Loja loja = lojas.get((int) (Math.random() * lojas.size()));
                System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - CLIENTE] - Comprando carro da loja " + loja.nome);
                Carro carro = loja.vender();
                System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - CLIENTE] - Carro " + carro.modelo + " vendido para cliente pela loja " + loja.nome);
                carros.add(carro);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
