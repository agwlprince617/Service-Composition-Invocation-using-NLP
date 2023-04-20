import java.util.*;

class GraphFindAllPaths<T> implements Iterable<T> {

    public final Map<T, Map<T, Double>> graph = new HashMap<T, Map<T, Double>>();
    
    public boolean addNode(T node) {
        if (node == null) {
            throw new NullPointerException("The input node cannot be null.");
        }
        if (graph.containsKey(node)) return false;
    
        graph.put(node, new HashMap<T, Double>());
        return true;
    }
    
    public void addEdge (T source, T destination, double length) {
        if (source == null || destination == null) {
            throw new NullPointerException("Source and Destination, both should be non-null.");
        }
        if (!graph.containsKey(source) || !graph.containsKey(destination)) {
            throw new NoSuchElementException("Source and Destination, both should be part of graph");
        }
        /* A node would always be added so no point returning true or false */
        graph.get(source).put(destination, length);
    
    }
    
  
    public void removeEdge (T source, T destination) {
        if (source == null || destination == null) {
            throw new NullPointerException("Source and Destination, both should be non-null.");
        }
        if (!graph.containsKey(source) || !graph.containsKey(destination)) {
            throw new NoSuchElementException("Source and Destination, both should be part of graph");
        }
        graph.get(source).remove(destination);
    }
    

    public Map<T, Double> edgesFrom(T node) {
        if (node == null) {
            throw new NullPointerException("The node should not be null.");
        }
        Map<T, Double> edges = graph.get(node);
        if (edges == null) {
            throw new NoSuchElementException("Source node does not exist.");
        }
        return Collections.unmodifiableMap(edges);
    }
    
   
    @Override public Iterator<T> iterator() {
        //System.out.println(graph.keySet().iterator());
        return graph.keySet().iterator();
    }
    }
    
  
    public class FindAllPaths<T> {
    
    private final  GraphFindAllPaths<T> graph;
    
    
    public FindAllPaths(GraphFindAllPaths<T> graph) {
        if (graph == null) {
            throw new NullPointerException("The input graph cannot be null.");
        }
        this.graph = graph;
    }
    
    
    private void validate (T source, T destination) {
    
        if (source == null) {
            throw new NullPointerException("The source: " + source + " cannot be  null.");
        }
        if (destination == null) {
            throw new NullPointerException("The destination: " + destination + " cannot be  null.");
        }
        if (source.equals(destination)) {
            throw new IllegalArgumentException("The source and destination: " + source + " cannot be the same.");
        }
    }
     
    public List<List<T>> sort(List<List<T>> list) {
        list.sort((xs1, xs2) -> xs1.size() - xs2.size());
        return list;
    }
 
    public List<List<T>> getAllPaths(T source, T destination) {
        validate(source, destination);
    
        List<List<T>> paths = new ArrayList<List<T>>();
        recursive(source, destination, paths, new LinkedHashSet<T>());
        return paths;
    }
    
    
    private void recursive (T current, T destination, List<List<T>> paths, LinkedHashSet<T> path) {
        path.add(current);
    
        if (current == destination) {
            paths.add(new ArrayList<T>(path));
            path.remove(current);
            return;
        }
    
        final Set<T> edges  = graph.edgesFrom(current).keySet();
    
        for (T t : edges) {
            if (!path.contains(t)) {
                //System.out.println(t);
                recursive (t, destination, paths, path);
            }
        }
    
        path.remove(current);
    }    
    
    public static void main(String[] args) {

        GraphFindAllPaths<String> graphFindAllPaths = new GraphFindAllPaths<String>();
        graphFindAllPaths.addNode("FlightNumber");
        graphFindAllPaths.addNode("FlightDetails");
        graphFindAllPaths.addNode("BaggageDetails");
        graphFindAllPaths.addNode("PassengerDetails");
    
        graphFindAllPaths.addEdge("FlightNumber", "FlightDetails", 10);
        graphFindAllPaths.addEdge("FlightDetails", "BaggageDetails", 15);
        graphFindAllPaths.addEdge("FlightDetails", "PassengerDetails", 10);
        graphFindAllPaths.addEdge("PassengerDetails", "BaggageDetails", 15);
        // graphFindAllPaths.addEdge("B", "D", 10);
        // graphFindAllPaths.addEdge("C", "D", 20);
        // graphFindAllPaths.addEdge("D", "B", 10);
        // graphFindAllPaths.addEdge("D", "C", 20);
    
        // graphFindAllPaths.addEdge("B", "C", 5);
        // graphFindAllPaths.addEdge("C", "B", 5);
    
    
    
        FindAllPaths<String> findAllPaths = new FindAllPaths<String>(graphFindAllPaths);
    
        List<List<String>> paths= findAllPaths.sort(findAllPaths.getAllPaths("FlightNumber", "BaggageDetails"));
    
        for (List<String> path :paths)
        {
            System.out.println(path);
        }

    }
    
    
}