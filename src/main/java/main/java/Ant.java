package main.java;


import org.jgrapht.Graph;

import java.util.ArrayList;

public class Ant {

    ArrayList<Integer> trail = new ArrayList<>();

    public void visitCity(int city) {
        trail.add(city);
    }

    public boolean visited(int i) {
        return trail.contains(i);
    }

    public Integer currentIndex(){
        return trail.get(trail.size() - 1);
    }

    public double trailLength(Graph g){
        double length = TSP.dist(g, trail.get(trail.size() - 1), trail.get(0));
        for (int i = 0; i < trail.size() - 1; i++)
        {
            length += TSP.dist(g, trail.get(i), trail.get(i + 1));
        }

        return length;
    }

    public void clear(){
        trail = new ArrayList<>();
    }
}
