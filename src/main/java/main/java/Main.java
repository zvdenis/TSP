package main.java;

import org.jgrapht.VertexFactory;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Main {


    public void getRandimMatrix(){

    }


    public static void main(String[] args)
    {

        SimpleWeightedGraph completeGraph = new SimpleWeightedGraph<>(DefaultEdge.class);
        CompleteGraphGenerator<String, DefaultEdge> completeGenerator
                = new CompleteGraphGenerator<>(4);
        VertexFactory<String> vFactory = new VertexFactory<String>() {
            private int id = 0;
            public String createVertex() {
                return "v" + id++;
            }
        };
        completeGenerator.generateGraph(completeGraph, vFactory, null);

    }
}
