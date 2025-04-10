package Fabrica;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Fabrica implements Runnable {
    private int carrosProduzidos;
    private int estoque;
    private Esteira esteira;
    private Armazem armazem;
    String start = "\\u001B[31m";
    String reset = "\u001B[0m";

    public Fabrica(int estoque, int capacidadeEsteira, int capacidadeArmazem) {
        this.estoque = estoque;
        this.esteira = new Esteira(this, capacidadeEsteira);
        this.armazem = new Armazem(capacidadeArmazem);
        this.carrosProduzidos = 0;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public int getCarrosProduzidos() {
        return carrosProduzidos;
    }

    public void armazenarCarro(Carro carro) {
        try {
            carrosProduzidos++;
            armazem.inserir(carro);
            System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - FABRICA] - Carro " + carro.modelo
            + " da cor " + carro.cor + " da estacao " + carro.estacao
            + " do funcionario " + carro.funcionario + " armazenado.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Carro retirarCarro() {
        try {
            Carro carro = armazem.retirar();
            System.out.println("[FABRICA] - Carro " + carro.modelo
            + " da cor " + carro.cor + " da estacao " + carro.estacao
            + " do funcionario " + carro.funcionario + " retirado.");
            return carro;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Produtor produtor = new Produtor(esteira, this);
        Estacao estacao0 = new Estacao("Alfa", esteira, this);
        Estacao estacao1 = new Estacao("Beta", esteira, this);
        Estacao estacao2 = new Estacao("Omega", esteira, this);
        Estacao estacao3 = new Estacao("Gremio", esteira, this);

        executorService.execute(produtor);
        executorService.execute(estacao0);
        executorService.execute(estacao1);
        executorService.execute(estacao2);
        executorService.execute(estacao3);
    }
}
