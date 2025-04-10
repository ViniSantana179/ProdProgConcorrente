package Fabrica;

import java.util.List;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Loja {
    public String nome;
    private final List<Carro> buffer;
    private int head;
    private int tail;
    private final Semaphore mutex;
    private final Semaphore empty;
    private final Semaphore full;

    public Loja(String nome, int size) {
        this.nome = nome;
        this.buffer = new ArrayList<>();
        this.head = 0;
        this.tail = 0;
        this.mutex = new Semaphore(1);
        this.empty = new Semaphore(size);
        this.full = new Semaphore(0);
    }

    public void comprar(Carro carro) throws InterruptedException {
        empty.acquire();
        mutex.acquire();
        buffer.add(carro);
        head = (head + 1) % buffer.size();
        mutex.release();
        full.release();
    }

    public Carro vender() throws InterruptedException {
        System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - LOJA] - Vendendo carro");
        full.acquire();
        mutex.acquire();
        Carro item = buffer.get(tail);
        tail = (tail + 1) % buffer.size();
        mutex.release();
        empty.release();
        return item;
    }

    public Carro requisitarCarro() {
        try {
            final String HOST = "127.0.0.1";
            final int PORTA = 12345;

            try (Socket socket = new Socket(HOST, PORTA)) {
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String params = entrada.readLine();
                
                Carro carro = new Carro(params.split(",")[0], params.split(",")[1], params.split(",")[2], params.split(",")[3]);
                comprar(carro);
                
                return carro;
            }
        } catch (IOException  | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
