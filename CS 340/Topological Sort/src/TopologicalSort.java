
/*
 * Ethan Lor
 * CS 340 Software Design 3
 * Homework 6 Topological Sort
 * 
 * November 28, 2017
 */
import java.io.*;
import java.util.*;

/*
 * Class to build an adjacency list representation of a directed graph and
 * finds the topological ordering of graph if one exists.
 */
public class TopologicalSort {
// Adjacency list representation of a directed graph.
// See the class discussion for the details of the representation.
	private class VertexNode {
		private String name;
		private VertexNode nextV;
		private EdgeNode edges;
		private int indegree;
		private Boolean added; // Variable to check if vertex node has been added to queue.

		private VertexNode(String n, VertexNode v) {
			name = n;
			nextV = v;
			edges = null;
			indegree = 0;
			added = false;
		}
	}

	private class EdgeNode {
		private VertexNode vertex1;
		private VertexNode vertex2;
		private EdgeNode nextE;

		private EdgeNode(VertexNode v1, VertexNode v2, EdgeNode e) {
			vertex1 = v1;
			vertex2 = v2;
			nextE = e;
		}
	}

	private VertexNode vertices;
	private int numVertices;
	private LinkedList<VertexNode> queue; // Queue for vertices with indegree of 0.
	private String topo = ""; // String for topological ordering/sort.

	public TopologicalSort() {
		vertices = null;
		numVertices = 0;
	}

	public void addVertex(String s) {
	// PRE: the vertex list is sorted in ascending order using the name as the key.
	// POST: a vertex with name s has been added to the vertex list and the vertex
	// list is sorted in ascending order using the name as they key.
		addVertices(s);
	}

	/*
	 * Adds vertex/vertices to vertex list in sorted ascending order using the name
	 * as the key.
	 */
	private void addVertices(String s) {
		if (vertices == null) {
			vertices = new VertexNode(s, null);
			numVertices++;
			return;
		}

		VertexNode temp = vertices;
		if (temp.name.compareTo(s) > 0) {
			vertices = new VertexNode(s, temp);
			numVertices++;
			return;
		}

		while (temp.nextV != null) {
			if (temp.nextV.name.compareTo(s) > 0) {
				temp.nextV = new VertexNode(s, temp.nextV);
				numVertices++;
				return;
			}
			temp = temp.nextV;
		}

		temp.nextV = new VertexNode(s, null);
		numVertices++;
	}

	public void addEdge(String n1, String n2) {
	// PRE: the vertices n1 and n2 have already been added.
	// POST: the new edge(n1, n2) has been added to the n1 edge list.
		addEdges(n1, n2);
	}

	/*
	 * Adds edges to an existing vertex in the vertex list. Sorted in ascending
	 * order using the second vertex name as the key.
	 */
	private void addEdges(String n1, String n2) {
		Boolean foundV1 = false; // Boolean to keep track if first vertex is found.
		Boolean foundV2 = false; // Boolean to keep track if second vertex is found.
		VertexNode v1 = null; // Variable for first vertex.
		VertexNode v2 = null; // Variable for second vertex.

		VertexNode tempVertex = vertices;
		while (tempVertex != null) {
			if (foundV1 == false && tempVertex.name.compareTo(n1) == 0) {
				v1 = tempVertex;
				foundV1 = true;
			}
			if (foundV2 == false && tempVertex.name.compareTo(n2) == 0) {
				v2 = tempVertex;
				foundV2 = true;
			}
			if (foundV1 == true && foundV2 == true) {
				break;
			}
			tempVertex = tempVertex.nextV;
		}

		if (v1.edges == null) {
			v1.edges = new EdgeNode(v1, v2, null);
			v2.indegree++;
		} else {
			if (v1.edges.vertex2.name.compareTo(n2) > 0) {
				v1.edges = new EdgeNode(v1, v2, v1.edges.nextE);
			}
			EdgeNode tempEdge = v1.edges;
			while (tempEdge.nextE != null) {
				if (tempEdge.nextE.vertex2.name.compareTo(n2) > 0) {
					tempEdge.nextE = new EdgeNode(v1, v2, tempEdge.nextE);
					v2.indegree++;
					return;
				}
				tempEdge = tempEdge.nextE;
			}
			tempEdge.nextE = new EdgeNode(v1, v2, null);
			v2.indegree++;
		}
	}

	public String topoSort() {
	// If the graph contains a cycle return "No topological ordering exists for the
	// graph.". Otherwise return a string containing the names of vertices separated
	// by blanks in topological order.
		return sort();
	}

	/*
	 * Sorts vertex list and attempts to find a topological ordering. If topological
	 * order is found, a string containing the names of vertices is returned. If no
	 * topological order is found, "No topological ordering exists for the graph."
	 * is returned.
	 */
	private String sort() {
		queue = new LinkedList<>();
		int count = 0;
		VertexNode temp = vertices;

		for (int i = 0; i < numVertices; i++) {
			if (temp.added == false && temp.indegree == 0) {
				queue.add(temp);
				temp.added = true;
			}
			temp = temp.nextV;
		}

		while (!queue.isEmpty()) {
			VertexNode poll = queue.poll();
			topo = topo + poll.name + " ";
			count++;
			EdgeNode tempEdge = poll.edges;
			while (tempEdge != null) {
				tempEdge.vertex2.indegree--;
				tempEdge = tempEdge.nextE;
			}
			VertexNode temp2 = vertices;
			for (int i = 0; i < numVertices; i++) {
				if (temp2.added == false && temp2.indegree == 0) {
					queue.add(temp2);
					temp2.added = true;
				}
				temp2 = temp2.nextV;
			}
		}

		if (count == numVertices) {
			return topo;
		} else {
			return "No topological ordering exists for the graph.";
		}
	}

	public static void main(String[] args) throws IOException {
	// See problem statement.
		TopologicalSort TS = new TopologicalSort();
		Scanner scan = new Scanner(new File(args[0]));
		String line;
		String[] verticesString;
		String[] edge;
		line = scan.nextLine();
		verticesString = line.split(" ");

		for (int i = 0; i < verticesString.length; i++) {
			TS.addVertex(verticesString[i]);
		}

		while (scan.hasNextLine()) {
			line = scan.nextLine();
			edge = line.split(" ");
			TS.addEdge(edge[0], edge[1]);
		}

		scan.close();
		System.out.println(TS.topoSort());
	}

}
