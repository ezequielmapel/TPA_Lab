package com.tpa.lab;

import com.tpa.lab.domain.Card;
import com.tpa.lab.domain.EdgeType;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) throws IOException, ParseException {
        FileReader reader = new FileReader("./src/main/resources/cards.json");
        JSONParser parser = new JSONParser();

        JSONArray jArray = (JSONArray) parser.parse(reader);

        List<Card> cards = (List<Card>) jArray.stream().map((c) -> Card.extractCard((JSONObject) c)).collect(Collectors.toList());
        StateMatrix stateMatrix = new StateMatrix(cards, cards.size());
        stateMatrix.printMatrix();


        System.out.println("##################");

        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(stateMatrix.tranformToStateMatrix(), StateMatrix.ROWS);
        adjacencyMatrix.transformToAdjacencyMatrix();
        adjacencyMatrix.printMatrix();


        GraphJung graphicsJung = new GraphJung(StateMatrix.ROWS);
        UndirectedSparseMultigraph<String, EdgeType> adjcencyGraph = graphicsJung.createGraph(adjacencyMatrix);

        //Dijkstra dijkstra = new Dijkstra(adjcencyGraph);
        //dijkstra.execute("X0");
        //graphicsJung.showGraphByMap(dijkstra.execute("X0"), "Dijkstra");


    }


}
