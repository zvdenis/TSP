import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import main.java.Answer;
import main.java.MyEdge;
import main.java.TSP;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    static AsUndirectedGraph<String, DefaultEdge> simpleGraph;
    static String simpleName = "simple.png";
    static String resourcePath = "src/test/resources/";
    static String simplePath = resourcePath + simpleName;


    static AsUndirectedGraph<String, DefaultEdge> testGraph;
    static String testName = "test.png";
    static String testPath = resourcePath + simpleName;

    @BeforeAll
    public static void createSimpleGraph() throws IOException {

        File imgFile = new File(simplePath);
        imgFile.createNewFile();
        DefaultDirectedGraph directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);


        String x1 = "1";
        String x2 = "2";
        String x3 = "3";
        String x4 = "4";

        directedGraph.addVertex(x1);
        directedGraph.addVertex(x2);
        directedGraph.addVertex(x3);
        directedGraph.addVertex(x4);

        directedGraph.addEdge(x1, x2, MyEdge.create(10, 0));
        directedGraph.addEdge(x1, x3, MyEdge.create(15, 0));
        directedGraph.addEdge(x1, x4, MyEdge.create(20, 0));
        directedGraph.addEdge(x2, x3, MyEdge.create(35, 0));
        directedGraph.addEdge(x2, x4, MyEdge.create(25, 0));
        directedGraph.addEdge(x3, x4, MyEdge.create(30, 0));


        simpleGraph = new AsUndirectedGraph<String, DefaultEdge>(directedGraph);
    }


    @BeforeAll
    public static void createComplexGraph() throws IOException {

        File imgFile = new File(simplePath);
        imgFile.createNewFile();
        DefaultDirectedGraph directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);


        String x1 = "1";
        String x2 = "2";
        String x3 = "3";
        String x4 = "4";
        String x5 = "5";
        String x6 = "6";

        directedGraph.addVertex(x1);
        directedGraph.addVertex(x2);
        directedGraph.addVertex(x3);
        directedGraph.addVertex(x4);
        directedGraph.addVertex(x5);
        directedGraph.addVertex(x6);

        directedGraph.addEdge(x1, x2, MyEdge.create(68, 0));
        directedGraph.addEdge(x1, x3, MyEdge.create(73, 0));
        directedGraph.addEdge(x1, x4, MyEdge.create(24, 0));
        directedGraph.addEdge(x1, x5, MyEdge.create(70, 0));
        directedGraph.addEdge(x1, x6, MyEdge.create(9, 0));



        directedGraph.addEdge(x2, x3, MyEdge.create(16, 0));
        directedGraph.addEdge(x2, x4, MyEdge.create(44, 0));
        directedGraph.addEdge(x2, x5, MyEdge.create(11, 0));
        directedGraph.addEdge(x2, x6, MyEdge.create(92, 0));

        directedGraph.addEdge(x3, x4, MyEdge.create(86, 0));
        directedGraph.addEdge(x3, x5, MyEdge.create(13, 0));
        directedGraph.addEdge(x3, x6, MyEdge.create(18, 0));

        directedGraph.addEdge(x4, x5, MyEdge.create(52, 0));
        directedGraph.addEdge(x4, x6, MyEdge.create(70, 0));

        directedGraph.addEdge(x5, x6, MyEdge.create(58, 0));

        testGraph = new AsUndirectedGraph<String, DefaultEdge>(directedGraph);
    }

    private void drawGraph(Graph graph, String name) throws IOException {
        JGraphXAdapter<String, DefaultEdge> graphAdapter =
                new JGraphXAdapter<String, DefaultEdge>(graph);

        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image =
                mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        File imgFile = new File(resourcePath + name);
        ImageIO.write(image, "PNG", imgFile);

        assertTrue(imgFile.exists());

    }


    @Test
    public void givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist() throws IOException {
        drawGraph(simpleGraph, simpleName);
    }

    @Test
    public void simpleTest() throws IOException {
        TSP tsp = new TSP();
        Answer answer = tsp.solve(simpleGraph);
        System.out.println(answer.bestTourLength);
        System.out.println(answer.bestTour.stream().map(x -> (x + 1) + " ").collect(Collectors.joining()));

        assertEquals(80, answer.bestTourLength);
    }

    @Test
    public void simpleTest2() throws IOException {
        TSP tsp = new TSP();

        Answer answer = tsp.solve(testGraph);

        System.out.println(answer.bestTourLength);
        System.out.println(answer.bestTour.stream().map(x -> (x + 1) + " ").collect(Collectors.joining()));

        assertEquals(119, answer.bestTourLength);
        assertAnswer(answer, testGraph);

        drawGraph(testGraph, testName);
    }

    private void assertAnswer(Answer answer, Graph g){
        double len = TSP.dist(g, answer.bestTour.get(0), answer.bestTour.get(answer.bestTour.size() - 1));
        for(int i = 0; i < answer.bestTour.size() - 1; i++)
        {
            len += TSP.dist(g, answer.bestTour.get(i), answer.bestTour.get(i + 1));

            String a = "" + (answer.bestTour.get(i) + 1);
            String b = "" + (answer.bestTour.get(i + 1) + 1);
            MyEdge edge = (MyEdge) g.getEdge(a, b);
            edge.isTour = true;
        }
        assertEquals(len, answer.bestTourLength);
    }



}