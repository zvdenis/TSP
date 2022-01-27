package main.java;

import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.Random;

public class TSP {
    ArrayList<Integer> bestTour = new ArrayList<>();
    double bestTourLength = 1000000000;

    private Random random = new Random();
    private double[][] trails;
    private int N;
    private Ant[] ants;
    private int antsCount;
    private Graph g;

    private double alpha = 1.0;
    private double beta = 1.0;
    private double evaporation = 0.5;
    private double Q = 500;


    public void setupAnts() {

        for (int i = 0; i < N; i++){
            ants[i] = new Ant();
        }

        for (Ant ant : ants) {

            ant.visitCity(random.nextInt(N));
        }


    }

    public void moveAnts() {
        for (int i = 0; i < ants.length; i++) {
            ants[i].clear();
            ants[i].trail.add(i);
            for (int j = 1 ; j < N; j++)
                ants[i].visitCity(selectNextCity(ants[i]));
        }
    }

    public Integer selectNextCity(Ant ant) {

        double[] probabilites = calculateProbabilities(ant);

        double r = random.nextDouble();
        double total = 0;
        for (int i = 0; i < N; i++) {
            total += probabilites[i];
            if (total >= r) {
                return i;
            }
        }

        throw new IllegalStateException();
    }

    public double[] calculateProbabilities(Ant ant) {
        double[] probabilities = new double[N];
        int i = ant.currentIndex();
        double pheromone = 0.0;
        for (int l = 0; l < N; l++) {
            if (!ant.visited(l)) {
                pheromone
                        += Math.pow(trails[i][l], alpha) * Math.pow(1.0 / dist(i, l), beta);
            }
        }
        for (int j = 0; j < N; j++) {
            if (ant.visited(j)) {
                probabilities[j] = 0.0;
            } else {
                double numerator
                        = Math.pow(trails[i][j], alpha) * Math.pow(1.0 / dist(i, j), beta);
                probabilities[j] = numerator / pheromone;
            }
        }
        return probabilities;
    }

    public void updateTrails() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                trails[i][j] *= evaporation;
            }
        }
        for (Ant a : ants) {
            double contribution = Q / a.trailLength(g);
            for (int i = 0; i < N - 1; i++) {
                trails[a.trail.get(i)][a.trail.get(i + 1)] += contribution;
            }
            trails[a.trail.get(N - 1)][a.trail.get(0)] += contribution;
        }
    }

    private void updateBest() {
        if (bestTour == null) {
            bestTour = ants[0].trail;
        }
        for (Ant a : ants) {
            if (a.trailLength(g) < bestTourLength) {
                bestTourLength = a.trailLength(g);
                bestTour = (ArrayList<Integer>) a.trail.clone();
            }
        }
    }


    public Answer solve(Graph g) {
        this.g = g;
        N = g.vertexSet().size();
        antsCount = N;
        trails = new double[N][N];
        for(int i = 0; i < N; i++)
            for(int j = 0; j < N; j++)
                trails[i][j] = 1.0 / N;

        ants = new Ant[N];
        setupAnts();


        for (int i = 0; i < 1000; i++) {
            moveAnts();
            updateTrails();
            updateBest();
        }

        return new Answer(bestTour, bestTourLength);
    }

    public static int dist(Graph g, Integer i, Integer j) {
        String a = "" + (i + 1);
        String b = "" + (j + 1);
        MyEdge edge = (MyEdge) g.getEdge(a, b);
        return edge.length;
    }

    private int dist(Integer i, Integer j) {
        return dist(g, i, j);
    }


    private void setPheromones(){
        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++)
            {
                String a = "" + (i + 1);
                String b = "" + (j + 1);
                MyEdge edge = (MyEdge) g.getEdge(a, b);
                edge.feromone = ((int)(trails[i][j] * 100)) / 100;
            }
    }

    private void setPheromone(Integer i, Integer j, double value) {
        MyEdge edge = (MyEdge) g.getEdge(i.toString(), j.toString());
        edge.feromone = value;
    }


}