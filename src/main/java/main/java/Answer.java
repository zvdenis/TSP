package main.java;

import java.util.ArrayList;

public class Answer {
    public ArrayList<Integer> bestTour;
    public double bestTourLength;

    public Answer(ArrayList<Integer> bestTour, double bestTourLength) {
        this.bestTour = bestTour;
        this.bestTourLength = bestTourLength;
    }
}
