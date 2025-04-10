package Fabrica;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Armazem {
    private final List<Carro> buffer;
    private int head;
    private int tail;
    private final Semaphore mutex;
    private final Semaphore empty;
    private final Semaphore full;

    public Armazem(int size) {
        this.buffer = new ArrayList<>();
        this.head = 0;
        this.tail = 0;
        this.mutex = new Semaphore(1);
        this.empty = new Semaphore(size);
        this.full = new Semaphore(0);
    }

    public void inserir(Carro carro) throws InterruptedException {
        empty.acquire();
        mutex.acquire();
        buffer.add(carro);
        head = (head + 1) % buffer.size();
        mutex.release();
        full.release();
    }

    public Carro retirar() throws InterruptedException {
        full.acquire();
        mutex.acquire();
        Carro item = buffer.get(tail);
        tail = (tail + 1) % buffer.size();
        mutex.release();
        empty.release();
        return item;
    }
}
