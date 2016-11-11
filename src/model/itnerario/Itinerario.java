package model.itnerario;

import model.cidade.Cidade;

/**
 * Created by rafa93br on 09/11/16.
 */
public class Itinerario {
    private Integer id;
    private String meioDeTransporte;
    private Double custo;
    private Integer duracao;
    private Cidade parteDe;
    private Cidade chegaEm;

    public Itinerario(Integer id, String meioDeTransporte, Double custo, Integer duracao) {
        this.id = id;
        this.meioDeTransporte = meioDeTransporte;
        this.custo = custo;
        this.duracao = duracao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeioDeTransporte() {
        return meioDeTransporte;
    }

    public void setMeioDeTransporte(String meioDeTransporte) {
        this.meioDeTransporte = meioDeTransporte;
    }

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }
}
