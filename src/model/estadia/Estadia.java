package model.estadia;

import model.cidade.Cidade;
import model.hotel.Hotel;
import model.itnerario.Itinerario;
import model.roteiro.Roteiro;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by aluno on 11/11/16.
 */
public class Estadia {
    Cidade cidade;
    Hotel hotel;
    Itinerario itinerario;
    Itinerario itinerarioVolta;
    Date dataChegada;
    Date dataSaida;
    Double custo;
    Integer dias;
    Roteiro roteiro;

    public Estadia(Cidade cidade, Hotel hotel, Itinerario itinerario, Date dataChegada, Date dataSaida) {
        if (!itinerario.getChegaEm().getId().equals(cidade.getId())) {
            throw new Error("Itinerário não chega na cidade.");
        }
        if (hotel.getCidade().getId() != cidade.getId()) {
            throw new Error("Hotel "+ hotel.getId() + " não está na cidade " + cidade.getId());
        }

        if (dataSaida.getTime() - dataChegada.getTime() < 0) {
            throw new Error("Data Saída precisa ser depois de Data Chegada");
        }


        this.cidade = cidade;
        this.hotel = hotel;
        this.itinerario = itinerario;
        this.dataChegada = dataChegada;
        this.dataSaida = dataSaida;
        this.dias = this.calcularDias();
        this.custo = this.calcularCusto();
    }


    public Estadia() {
    }

    public Roteiro getRoteiro() {
        return roteiro;
    }

    private Double calcularCusto() {
        Itinerario itinerarioVolta = this.getItinerarioVolta();
        Double custoItinerario = 0d;
        Double custoItinerarioVolta = 0d;
        if (itinerarioVolta != null) {
            custoItinerarioVolta = itinerarioVolta.getCusto();
        }
        if (itinerario != null) {
            custoItinerario = itinerario.getCusto();
        }

        return custoItinerario + (this.hotel.getPrecoDiaria() * this.calcularDias()) + custoItinerarioVolta;
    }

    private Integer calcularDias() {
        Long diff = dataSaida.getTime() - dataChegada.getTime();
        Long dias = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return dias.intValue() + 1;
    }
    public void setRoteiro(Roteiro roteiro) {
        this.roteiro = roteiro;
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

    public Itinerario getItinerario() {
        return itinerario;
    }

    public Date getDataChegada() {
        return dataChegada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public Double getCusto() {
        return this.calcularCusto();
    }

    public Integer getDias() {
        return this.calcularDias();
    }

    public Itinerario getItinerarioVolta() {
        return itinerarioVolta;
    }

    public void setItinerarioVolta(Itinerario itinerarioVolta) {
        this.itinerarioVolta = itinerarioVolta;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setItinerario(Itinerario itinerario) {
        this.itinerario = itinerario;
    }

    public void setDataChegada(Date dataChegada) {
        this.dataChegada = dataChegada;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }
}
