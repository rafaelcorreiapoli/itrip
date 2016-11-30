package lib.montador;

import lib.dijkstra.Edge;
import lib.dijkstra.Engine;
import lib.dijkstra.Graph;
import lib.dijkstra.Vertex;
import model.cidade.Cidade;
import model.cidade.CidadeDAO;
import model.estadia.Estadia;
import model.hotel.Hotel;
import model.itnerario.Itinerario;
import model.itnerario.ItinerarioDAO;
import model.roteiro.Roteiro;

import java.util.*;

/**
 * Created by rafa93br on 29/11/16.
 */
public class Montador {

    public List<Estadia> gerarEstadias(Cidade cidadeOrigem, Cidade cidadeDestino, Date dataInicio) {
        List<Cidade> cidades = CidadeDAO.getInstance().getAll();
        List<Itinerario> itinerarios = ItinerarioDAO.getInstance().getAllMenorCusto();
        List<Vertex> vertexes = new ArrayList<Vertex>();
        List<Edge> edges = new ArrayList<Edge>();
        Vertex vertexOrigem = null;
        Vertex vertexDestino = null;

        for (Cidade cidade : cidades) {
            Vertex vertex = new Vertex(cidade.getId().toString(), cidade.getNome());

            vertex.getData().put("cidade", cidade);
            vertex.getData().put("hotel", cidade.hotelNaMediana());
            vertex.getData().put("tabelaItinerarios", new HashMap<Integer, Itinerario>());
            if (cidade.getId().equals(cidadeOrigem.getId())) {
                vertexOrigem = vertex;
            } else if (cidade.getId().equals(cidadeDestino.getId())) {
                vertexDestino = vertex;
            }

            vertexes.add(vertex);
        }

        for (Itinerario itinerario : itinerarios) {
            Cidade origem = itinerario.getParteDe();
            Cidade destino = itinerario.getChegaEm();
            Vertex vOrigem = null;
            Vertex vDestino = null;

            for (Vertex vertex : vertexes) {
                if (vertex.getId().equals(origem.getId().toString())) {
                    vOrigem = vertex;
                }

                if (vertex.getId().equals(destino.getId().toString())) {
                    vDestino = vertex;
                }

                if (vOrigem != null && vDestino != null) {
                    break;
                }
            }


            HashMap<Integer, Itinerario> tabelaItinerarios = (HashMap<Integer, Itinerario>) vDestino.getData().get("tabelaItinerarios");
            tabelaItinerarios.put(origem.getId(), itinerario);

            Double weight = itinerario.getCusto() + (destino.hotelNaMediana().getPrecoDiaria() * destino.getNumeroDiasIdeal());

            Edge edge = new Edge(vOrigem.getId() + vDestino.getId(),vOrigem, vDestino, weight);
            edges.add(edge);
        }

        Graph graph = new Graph(vertexes, edges);
        Engine engine = new Engine(graph);

        engine.execute(vertexOrigem);

        LinkedList<Vertex> path = engine.getPath(vertexDestino);

        Calendar c = Calendar.getInstance();
        c.setTime(dataInicio);


        List<Estadia> estadias = new ArrayList<Estadia>();


        int i = 0;
        for (Vertex vertex : path) {
            Cidade cidade = (Cidade) vertex.getData().get("cidade");
            Hotel hotel = (Hotel) vertex.getData().get("hotel");
            Date dataChegada = c.getTime();
            c.add(Calendar.DATE, cidade.getNumeroDiasIdeal());
            Date dataSaida = c.getTime();

            Estadia estadia = new Estadia();
            estadia.setCidade(cidade);
            estadia.setDataChegada(dataChegada);
            estadia.setDataSaida(dataSaida);
            estadia.setHotel(hotel);
            if (i > 0) {
                Cidade cidadeAnterior = (Cidade) path.get(i - 1).getData().get("cidade");
                HashMap<Cidade, Itinerario> tabelaItinerarios = (HashMap<Cidade, Itinerario>) vertex.getData().get("tabelaItinerarios");
                Itinerario itinerario = tabelaItinerarios.get(cidadeAnterior.getId());
                estadia.setItinerario(itinerario);
            }

            estadias.add(estadia);

            i++;
        }

        return estadias;
    }
}