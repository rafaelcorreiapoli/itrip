package model.estadia;

import model.cidade.Cidade;
import model.hotel.Hotel;
import model.itnerario.Itinerario;

import java.util.Date;

/**
 * Created by aluno on 11/11/16.
 */
public class Estadia {
    Cidade cidade;
    Hotel hotel;
    Itinerario itinerario;
    Date dataChegada;
    Date dataSaida;
    Double custo;
    Integer dias;

    public Estadia(Cidade cidade, Hotel hotel, Itinerario itinerario, Date dataChegada, Date dataSaida, Double custo, Integer dias) {
        this.cidade = cidade;
        this.hotel = hotel;
        this.itinerario = itinerario;
        this.dataChegada = dataChegada;
        this.dataSaida = dataSaida;
        this.custo = custo;
        this.dias = dias;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Itinerario getItinerario() {
        return itinerario;
    }

    public void setItinerario(Itinerario itinerario) {
        this.itinerario = itinerario;
    }

    public Date getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(Date dataChegada) {
        this.dataChegada = dataChegada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double custo) {
        this.custo = custo;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }
}
