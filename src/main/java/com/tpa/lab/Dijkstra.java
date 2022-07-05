package com.tpa.lab;

import com.tpa.lab.domain.EdgeType;
import com.tpa.lab.domain.Vertice;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;

import java.util.*;

public class Dijkstra {

    private UndirectedSparseMultigraph<String, EdgeType> graph;

    public Dijkstra(UndirectedSparseMultigraph<String, EdgeType> graph) {
        this.graph = graph;
    }

    public Map<String, Vertice> execute(String origem) {
        Map<String, Vertice> dVertices = new HashMap<>();

        this.inicializaOrigemUnica(dVertices, origem);

        Queue<String> fila = new LinkedList<>(this.graph.getVertices());
        List<String> removidos = new ArrayList<>();

        while(!fila.isEmpty()) {
            fila.stream().min((a, b) ->
                            Float.compare(dVertices.get(a).getDistance(), dVertices.get(b).getDistance()))
                    .ifPresent(minValueKey -> {
                        fila.remove(minValueKey);
                        removidos.add(minValueKey);

                        for(String adjacente : this.graph.getSuccessors(minValueKey)) {
                            this.relax(minValueKey, adjacente, this.graph.findEdge(minValueKey, adjacente).getWeight(), dVertices);
                        }
                    });
        }

        return dVertices;
    }


    private void inicializaOrigemUnica(Map<String, Vertice> dVertices, String origem) {
        for(String verticeKey : this.graph.getVertices()) {
            Vertice vertice = new Vertice();
            vertice.setDistance(Integer.MAX_VALUE);
            dVertices.put(verticeKey, vertice);
        }

        Vertice verticeOrigem = dVertices.get(origem);
        verticeOrigem.setDistance(0);
    }

    public void relax(String u,String v, int w,Map<String, Vertice> dVertices) {
        if( dVertices.get(v).getDistance() > dVertices.get(u).getDistance() + w ) {
            dVertices.get(v).setDistance(dVertices.get(u).getDistance() + w);
            dVertices.get(v).setPredecessor(u);
        }
    }
}
