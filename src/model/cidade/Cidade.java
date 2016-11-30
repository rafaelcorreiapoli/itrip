package model.cidade;

import model.hotel.Hotel;
import model.hotel.HotelDAO;
import model.itnerario.Itinerario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafa93br on 08/11/16.
 */
public class Cidade {
    private String nome;
    private boolean temAeroporto;

    public int getNumeroDiasIdeal() {
        return numeroDiasIdeal;
    }

    public void setNumeroDiasIdeal(int numeroDiasIdeal) {
        this.numeroDiasIdeal = numeroDiasIdeal;
    }

    private int numeroDiasIdeal;
    private Integer id;
    List<Itinerario> itinerarios = new ArrayList<Itinerario>();

    public Cidade(Integer id, String nome, boolean temAeroporto, int numeroDiasIdeal){
        this.nome = nome;
        this.temAeroporto = temAeroporto;
        this.numeroDiasIdeal = numeroDiasIdeal;
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }


    public List<Itinerario> getItinerarios() {
        return itinerarios;
    }

    public void setItinerarios(List<Itinerario> itinerarios) {
        this.itinerarios = itinerarios;
    }

    public Boolean temItinerarioPara(Cidade cidade) {

        for (Itinerario itinerario : this.itinerarios) {
            System.out.println(itinerario.getId());
            System.out.println(itinerario.getChegaEm().getId());
            if (itinerario.getChegaEm().getId() == cidade.getId()) {
                return true;
            };
        }

        return false;
    }

    public Boolean temItinerarioDe(Cidade cidade) {
        return cidade.temItinerarioPara(this);
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

    public Hotel hotelNaMediana() {
        List<Hotel> hoteis = HotelDAO.getInstance().getHoteisByCidade(this.getId());

        double count = (double) hoteis.size();
        Integer target = (int) Math.ceil(count / 2) - 1;
        return hoteis.get(target);
    }

}