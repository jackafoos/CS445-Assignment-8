import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Graph {

	private Map<String,List<Edge>> graphData = new HashMap<String,List<Edge>>();
	private boolean isDirected;
	private boolean isWeighted;

	public Graph(boolean isDirected, boolean isWeighted) {
	  this.isDirected = isDirected;
	  this.isWeighted = isWeighted;
	}

	// this method will add edges to the graph based on a string.
	// this string is in the form "from,to"
	// each pair of adjacent nodes should be separated by a semicolon.
	public void addEdges(String edgeList) {
	  for (String edgePair : edgeList.split(";")) {
	    String[] edges = edgePair.split(",");
	    if (edges.length == 2) {
	      addEdge(edges[0], edges[1]);
	    }
	  }
	}

	// add an edge from one node to another; if this is a weighted graph this will throw an exception
	public void addEdge(String from, String to) {
	  if (isWeighted) {
	    throw new UnsupportedOperationException("Graph is weighted.");
	  }
	  addEdge(from, to, 1);
	}

	// add an edge from one node to another;
	// this method will handle reversing the edge for undirected graphs
	public void addEdge(String from, String to, int weight) {
	  addOneWayConnection(from, to, weight);
		if (!isDirected) {
		  addOneWayConnection(to, from, weight);
		}
	}

	private void addOneWayConnection(String origin, String destination, int weight) {
		List<Edge> connections = graphData.get(origin);
		if (connections == null) {
			connections = new LinkedList<Edge>();
			graphData.put(origin, connections);
		}
		connections.add(new Edge(destination, weight));
	}

	// determine if you can travel directly from "from" to "to"
	public boolean isAdjacent(String from, String to) {
	  List<Edge> edges = graphData.get(from);
	  if (edges != null) {
	    for (Edge edge : edges) {
	      if (edge.adjacentNode.equals(to)) {
	        return true;
	      }
	    }
	  }
	  return false;
	}

	// return the weight between "from" and "to"
	// if nodes are not adjacent, return null
	public Integer getWeight(String from, String to) {
	  List<Edge> edges = graphData.get(from);
	  if (edges != null) {
	    for (Edge edge : edges) {
	      if (edge.adjacentNode.equals(to)) {
	        return new Integer(edge.weight);
	      }
	    }
	  }
	  return null;
	}

	// represent the weight and an adjacent node
	private class Edge {
	  private int weight = 1;
	  private String adjacentNode;

	  public Edge(String adjacentNode, int weight) {
	    this.adjacentNode = adjacentNode;
	  }
	}

	public static void main(String[] args) {
	  String nodes = "A,B;B,C;C,D;D,E;A,K;K,E;A,F;F,G;G,H;H,I;I,J;J,E";
	  Graph graph = new Graph(false, false);
	  graph.addEdges(nodes);
	  System.out.println(graph.getShortestPath("A", "E")); // should print [A, K, E]
	}

	public List<String> getShortestPath(String from, String to) {
		List<String> shortPath = new List<String>();
		Queue<String> q = new Queue<String>();
		Map<String,String> visited = new Map<String>();

		q.add(from)
		visited.put(from, null);
		while(true){
			String top = q.remove();
			for (Edge edge : graphData.get(top)){
				if(!visited.containsKey(edge.adjacentNode)){
					visited.put(edge.adjacentNode, top);
					System.out.println("" + edge.adjacentNode + " => " + visited.get(adjacentNode));
					q.add(edge.adjacentNode);
				}
			}
			if(top.equals(to))
				break;
		}

		while (top != null){
			shortPath.add(0, top);
			top = visited.get(top);
		}
		return shortPath;
	}
}
