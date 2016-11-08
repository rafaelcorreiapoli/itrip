package model.cidade;

/**
 * Created by rafa93br on 08/11/16.
 */
public class Cidade {
    private String nome;
    private boolean temAeroporto;
    private int numeroDiasIdeal;
    private Integer id;

    public Cidade(Integer id, String nome, boolean temAeroporto, int numeroDiasIdeal){
        this.nome = nome;
        this.temAeroporto = temAeroporto;
        this.numeroDiasIdeal = numeroDiasIdeal;
        this.id = id;
    }

    public Integer getId() {
        return this.id;
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

    public boolean isTemAeroporto() {
        return temAeroporto;
    }

    public void setTemAeroporto(boolean temAeroporto) {
        this.temAeroporto = temAeroporto;
    }

    public int getNumeroDiasIdeal() {
        return numeroDiasIdeal;
    }

    public void setNumeroDiasIdeal(int numeroDiasIdeal) {
        this.numeroDiasIdeal = numeroDiasIdeal;
    }

}