package Fabrica;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        final int PORTA = 12345;

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Fabrica fabrica = new Fabrica(5, 5, 40);

        executorService.execute(fabrica);

        try (ServerSocket servidor = new ServerSocket(PORTA)) {
            System.out.println("Servidor iniciado na porta " + PORTA);

            while (true) {
                    System.out.println("Aguardando novo cliente...");
                    Socket cliente = servidor.accept();
                    System.out.println("Cliente conectado: " + cliente.getInetAddress());
    
                    // Envia uma mensagem para o cliente
                    PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);
                    Carro carro = fabrica.retirarCarro();
                    saida.println(carro.toString());
    
                    // Fecha a conexão com este cliente
                    cliente.close();
                    System.out.println("Conexão com cliente encerrada.\n");
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}