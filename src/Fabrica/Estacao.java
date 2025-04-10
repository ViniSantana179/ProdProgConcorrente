package Fabrica;
import java.util.concurrent.Semaphore;

public class Estacao implements Runnable {
    public String nome;
    public Esteira esteira;
    public Fabrica fabrica;
    
    public Estacao(String nome, Esteira esteira, Fabrica fabrica) {
        this.nome = nome;
        this.esteira = esteira;
        this.fabrica = fabrica;
    }
    
    @Override
    public void run() {
        Semaphore ferramenta1 = new Semaphore(1);
        Semaphore ferramenta2 = new Semaphore(1);
        Semaphore ferramenta3 = new Semaphore(1);
        Semaphore ferramenta4 = new Semaphore(1);
        Semaphore ferramenta5 = new Semaphore(1);

        Funcionario funcionario1 = new Funcionario(1, "João", this, ferramenta1, ferramenta2);
        Funcionario funcionario2 = new Funcionario(2, "Maria", this, ferramenta2, ferramenta3);
        Funcionario funcionario3 = new Funcionario(3, "Pedro", this, ferramenta3, ferramenta4);
        Funcionario funcionario4 = new Funcionario(4, "Ana", this, ferramenta4, ferramenta5);
        Funcionario funcionario5 = new Funcionario(5, "Lucas", this, ferramenta5, ferramenta1);

        Thread t1 = new Thread(funcionario1);
        Thread t2 = new Thread(funcionario2);
        Thread t3 = new Thread(funcionario3);
        Thread t4 = new Thread(funcionario4);
        Thread t5 = new Thread(funcionario5);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
