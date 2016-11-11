package model.roteiro;

import model.cidade.Cidade;
import model.estadia.Estadia;

import java.util.List;

/**
 * Created by rafa93br on 08/11/16.
 */
public class Roteiro {
    Cidade cidadeInicial;
    List<Estadia> estadias;

    public Roteiro(Cidade cidadeInicial, List<Estadia> estadias) {
        this.cidadeInicial = cidadeInicial;
        this.estadias = estadias;
    }

    public Cidade getCidadeInicial() {
        return cidadeInicial;
    }

    public void setCidadeInicial(Cidade cidadeInicial) {
        this.cidadeInicial = cidadeInicial;
    }

    public List<Estadia> getEstadias() {
        return estadias;
    }

    public void adicionarEstadia(Estadia estadia) {
        this.estadias.add(estadia);
    }
}
