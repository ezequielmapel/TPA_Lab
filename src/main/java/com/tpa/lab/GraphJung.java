package com.tpa.lab;

import com.tpa.lab.domain.EdgeType;
import com.tpa.lab.domain.Vertice;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.util.Map;


public class GraphJung {
    private int dimensions;
    private UndirectedSparseMultigraph<String, EdgeType> graph = new UndirectedSparseMultigraph<>();

    public GraphJung(int dimensions) {
        this.dimensions = dimensions;
    }

    public UndirectedSparseMultigraph<String, EdgeType> createGraph(AdjacencyMatrix adjacencyMatrix) {
        String[] vertices = new String[this.dimensions];

        for(int i = 0; i < this.dimensions; i++) {
            vertices[i] = "X"+i;
            this.graph.addVertex("X"+i);
        }

        for(int row = 0; row < this.dimensions; row++) {
            for(int column = 0; column < this.dimensions; column++) {
                int weight = adjacencyMatrix.getInRowAndColumn(row, column);
                if(weight != 0) {
                    EdgeType edgeType = new EdgeType(weight, String.valueOf(this.graph.getEdgeCount()));
                    this.graph.addEdge(edgeType, vertices[row], vertices[column]);
                }
            }
        }

        this.showGraph("Grafo");

        return this.graph;
    }

    public void showGraph(String nomedoGrafo) {
        // SimpleGraphView2 sgv = new SimpleGraphView2(); // This builds the graph
        // Layout<V, E>, VisualizationComponent<V,E>
        Layout<Integer, String> layout = new CircleLayout(this.graph);
        layout.setSize(new Dimension(300,300));
        //BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);
        BasicVisualizationServer vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(350,350));
        // Setup up a new vertex to paint transformer...
        Transformer<String,Paint> vertexPaint = new Transformer<>() {
            public Paint transform(String i) {
                return Color.YELLOW;
            }
        };
        Transformer<EdgeType,String> edgePaint = new Transformer<>() {
            public String transform(EdgeType i) {
                return  String.valueOf(i.getWeight());

            }
        };


        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        //vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());

        vv.getRenderContext().setEdgeLabelTransformer(edgePaint);
        //vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        JFrame frame = new JFrame(nomedoGrafo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);

    }


    public void showGraphByMap(Map<String, Vertice> dVertices, String graphName) {
        UndirectedSparseMultigraph<String, EdgeType> gMC = new UndirectedSparseMultigraph<>();
        for (String u : this.graph.getVertices()) {
            gMC.addVertex(u);
        }

        for (String u : this.graph.getVertices()) {
            String v = dVertices.get(u).getPredecessor();
            if (v != null) {

                EdgeType e = new EdgeType(this.graph.findEdge(v, u).getWeight(), String.valueOf(gMC.getEdgeCount()));
                gMC.addEdge(e, v, u);
            }
        }

        this.showGraph(graphName);
    }
}
