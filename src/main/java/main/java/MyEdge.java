package main.java;


import org.jgrapht.graph.DefaultEdge;

public class MyEdge extends DefaultEdge {
    int length;
    double feromone;
    public boolean isTour = false;

    @Override
    public String toString() {
        if(!isTour)
            return "len=" + length;
        return "len=" + length;
        //return "len=" + length + "\ntour";
                //+ "\n" + "fer=" + feromone;
    }

    public static MyEdge create(int length, double feromone){
        MyEdge node =  new MyEdge();
        node.feromone = feromone;
        node.length = length;
        return node;
    }
}
