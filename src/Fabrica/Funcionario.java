package Fabrica;

import java.util.concurrent.Semaphore;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Funcionario implements Runnable {
    public String nome;
    private int numeroFuncionario;
    private Estacao estacao;
    private Esteira esteira;
    private Fabrica fabrica;
    private Semaphore ferramentaEsquerda;
    private Semaphore ferramentaDireita;
    private String[] modelosDisponiveis;

    Funcionario(int numeroFuncionario, String nome, Estacao estacao, Semaphore ferramentaEsquerda, Semaphore ferramentaDireita) {
        this.numeroFuncionario = numeroFuncionario;
        this.nome = nome;
        this.estacao = estacao;
        this.esteira = estacao.esteira;
        this.fabrica = estacao.fabrica;
        this.ferramentaEsquerda = ferramentaEsquerda;
        this.ferramentaDireita = ferramentaDireita;
        this.modelosDisponiveis = new String[] {
            "BMW X6", "BMW X5", "BMW X7", "Audi A4", "Audi A5",
             "Audi A7", "Mercedes C200", "Mercedes C300", "Mercedes C400",
             "Mercedes C500", "Mercedes C600", "Mercedes C700"
            };
    }

    public void construirCarro() {
        try {
            Thread.sleep(2000);
            String cor = String.format("#%06x", (int)(Math.random()*0xffffff));
            Carro carro = new Carro(String.valueOf(
                modelosDisponiveis[(int) (Math.random() * modelosDisponiveis.length)]), 
                cor,
                estacao.nome,
                this.nome
            );
            fabrica.armazenarCarro(carro);
            System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - FUNCIONARIO] - " + fabrica.getCarrosProduzidos() + " carros produzidos.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (fabrica.getEstoque() > 0 || esteira.capacidade > 0) {
            try {
                esteira.consumir();
    
                if (numeroFuncionario < 4){
                    ferramentaDireita.acquire();
                    ferramentaEsquerda.acquire();
                } else {
                    ferramentaEsquerda.acquire();
                    ferramentaDireita.acquire();
                }
                
                System.out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - FUNCIONARIO] - Estação: " + estacao.nome + ", Funcionário: " + nome + " trabalhando...");

                construirCarro();

                ferramentaEsquerda.release();
                ferramentaDireita.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
