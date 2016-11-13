package model.hotel;

import model.cidade.Cidade;

/**
 * Created by rafa93br on 08/11/16.
 */
public class Hotel {
    private Integer id;
    private String nome;
    private Double precoDiaria;
    private String endereco;
    Cidade cidade;

    public Hotel(Integer id, String nome, Double precoDiaria, String endereco) {
        super();
        this.id = id;
        this.nome = nome;
        this.precoDiaria = precoDiaria;
        this.endereco = endereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Double getPrecoDiaria() {
        return precoDiaria;
    }
    public void setPrecoDiaria(Double precoDiaria) {
        this.precoDiaria = precoDiaria;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}