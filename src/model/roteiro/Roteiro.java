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
    Estadia origem;
    Estadia destino;

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

    public ArrayList<Estadia> getTodasEstadias() {
        ArrayList<Estadia> todasEstadias = new ArrayList<Estadia>();
        todasEstadias.add(this.origem);
        todasEstadias.addAll(this.estadias);
        todasEstadias.add(this.destino);

        return todasEstadias;
    }

    public void adicionarEstadia(Estadia estadia) {

        if (this.estadias.size() == 0) {
            //  Validar se o itinerário da primeira estadia parte da cidade da estadia origem
            if (!estadia.getItinerario().getParteDe().getId().equals(origem.getCidade().getId())) {
                throw new Error ("Itinerário da primeira estadia não parte da origem");
            }
        } else {
            // Validar se o itinerário da estadia n parte da cidade da estadia n - 1
            Estadia estadiaAnterior = this.estadias.get(this.estadias.size() - 1);

            if (!estadia.getItinerario().getParteDe().getId().equals(estadiaAnterior.getCidade().getId())) {
                throw new Error("Itinerário entre duas estadias não é valido");
            }
        }

        this.estadias.add(estadia);
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

    public Estadia getOrigem() {
        return origem;
    }

    public void setOrigem(Estadia origem) {
        // Verificar se existe aeroporto na origem
        if (!origem.getCidade().isTemAeroporto()) {
            throw new Error("Não tem aeroporto na origem.");
        }
        this.origem = origem;
    }

    public Estadia getDestino() {
        return destino;
    }

    public void setDestino(Estadia destino) {
        // Verificar se o itinerário de volta chega na cidade inicial
        if (!destino.getItinerarioVolta().getChegaEm().getId().equals(cidadeInicial.getId())) {
            throw new Error("Itnerário não volta para cidade inicial");
        }
        // Verificar se destino tem aeroporto
        if (!destino.getCidade().isTemAeroporto()) {
            throw new Error("Não tem aeroporto no destino.");
        }
        this.destino = destino;
    }
}
