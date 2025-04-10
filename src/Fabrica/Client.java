package Fabrica;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        Loja loja1 = new Loja("Caoa", 40);
        Loja loja2 = new Loja("Servopa", 40);
        Loja loja3 = new Loja("Barigui", 40);

        List<Loja> lojas = new ArrayList<>();
        lojas.add(loja1);
        lojas.add(loja2);
        lojas.add(loja3);

        for (int i = 0; i < 20; i++) {
            Cliente cliente = new Cliente();
            cliente.setLojas(lojas);
            new Thread(cliente).start();
        }

        while (true) {
            Loja loja = lojas.get((int) (Math.random() * lojas.size()));
            System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - CLIENT] - Requisitando carro para loja " + loja.nome);
            Carro carro = loja.requisitarCarro();
            System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - CLIENT] - Carro " + carro.modelo + " recebedido pela loja " + loja.nome);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
