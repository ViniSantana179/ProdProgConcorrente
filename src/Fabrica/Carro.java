package Fabrica;

import java.io.Serializable;

public class Carro implements Serializable {
    public String modelo;
    public String cor;
    public String estacao;
    public String funcionario;

    public Carro(String modelo, String cor, String estacao, String funcionario) {
        this.modelo = modelo;
        this.cor = cor;
        this.estacao = estacao;
        this.funcionario = funcionario;
    }

    @Override
    public String toString() {
        return String.format(modelo + "," + cor + "," + estacao + "," + funcionario);
    }
}
