package model.roteiro;

import model.cidade.Cidade;
import model.estadia.Estadia;
import model.itnerario.Itinerario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by rafa93br on 08/11/16.
 */
public class Roteiro {
    Integer id;
    Cidade cidadeInicial;
    List<Estadia> estadias = new ArrayList<Estadia>();

    public Roteiro() {
    }

    public Roteiro(Cidade cidadeInicial) {
        this.cidadeInicial = cidadeInicial;
    }

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


    public void setEstadias(List<Estadia> estadias) {
        this.estadias = estadias;
    }

    public void adicionarEstadia(Estadia estadia) {

//        if (this.estadias.size() == 0) {
//            //  Validar se o itinerário da primeira estadia parte da cidade da estadia origem
//            if (!estadia.getItinerario().getParteDe().getId().equals(origem.getCidade().getId())) {
//                throw new Error ("Itinerário da primeira estadia não parte da origem");
//            }
//        } else {
//            // Validar se o itinerário da estadia n parte da cidade da estadia n - 1
//            Estadia estadiaAnterior = this.estadias.get(this.estadias.size() - 1);
//
//            if (!estadia.getItinerario().getParteDe().getId().equals(estadiaAnterior.getCidade().getId())) {
//                throw new Error("Itinerário entre duas estadias não é valido");
//            }
//        }

        if (this.estadias.size() == 0) {
            this.estadias.add(estadia);
        } else if (this.estadias.size() == 1) {
            this.estadias.add(estadia);
        } else {
            this.estadias.add(this.estadias.size() - 1, estadia);
        }

    }

    public Estadia getUltimaEstadiaIntermediaria() {
        if (this.estadias.size() >= 2) {
            return this.estadias.get(this.estadias.size() - 2);
        }
        return null;
    }

    public Estadia getDestino() {
        if (this.estadias.size() >= 2) {
            return this.estadias.get(this.estadias.size() - 1);
        }
        return null;
    }
    public Integer getId() {
        return id;
    }

    public Double getCusto() {
        return this.estadias.stream()
                .mapToDouble(Estadia::getCusto)
                .sum();
    }
    public void setId(Integer id) {
        this.id = id;
    }


}
