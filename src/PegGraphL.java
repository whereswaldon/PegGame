import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * Creates a neighbor list for use with graph theory applications
 * @author christopherwaldon
 *
 */
public class PegGraphL {
	private int numEdges;
	private int numVerts;
	private ArrayList<LinkedList<PegJumpNode>> adjList;
	public PegGraphL ()
	//   creates an empty graph
	{
		numEdges = 0;
		numVerts = 0;
		setAdjList(new ArrayList<LinkedList<PegJumpNode>>());
	}

	public PegGraphL (int numVertices)
	//    creates a graph with this numVertices vertices and no edges
	//    (create n empty linked lists)
	{
		numEdges = 0;
		numVerts = numVertices;
		setAdjList(new ArrayList<LinkedList<PegJumpNode>>());
		for (int i = 0; i < numVerts; i++) {
			getAdjList().add(new LinkedList<PegJumpNode>());
		}
	}

	public void resize(int numVertices)
	//    if this is a larger size, reallocates memory for more vertices 
	//    in the graph, and copies the  old lists, but no new edges yet 
	//    between the new vertices. 
	{
		numVerts = numVertices;
		for (int i = 0; i < numVerts; i++) {
			getAdjList().add(new LinkedList<PegJumpNode>());
		}
	}

	public int order()
	//   returns the number of vertices in the graph.
	{
		return numVerts;
	}

	public int size()
	//   Returns the number of edges in the graph.
	{
		return numEdges;
	}

	public Iterator neighbors(int i)
	//   returns an iterator to the linked list of neighbors  of vertex i
	{
		if (getAdjList().get(i) == null)
			return null;
		return getAdjList().get(i).iterator();
	}

	public void addEdge (int i, int j, int between)
	//   adds an edge between i and j (and j and i)
	{
		if (getAdjList().size() < i) {
			getAdjList().add(i, new LinkedList<PegJumpNode>());
		}
		if (getAdjList().size() < j) {
			getAdjList().add(j, new LinkedList<PegJumpNode>());
		}
		getAdjList().get(i).add(new PegJumpNode(j,between));
		getAdjList().get(j).add(new PegJumpNode(i,between));
		numEdges++;
	}

	/**
	 * Prints out each index in the list as well as what it contains
	 */
	public String toString() {
		String answer = "";
		int vertex = 0;
		for (LinkedList<PegJumpNode> l : getAdjList()) {
			answer += vertex++ + ": ";
			for (PegJumpNode i : l) {
				answer += i.getJumpTo() + " jumping " + i.getJumped() + " AND ";
			}
			answer += "\n";
		}
		return answer;
	}

	public ArrayList<LinkedList<PegJumpNode>> getAdjList() {
		return adjList;
	}

	public void setAdjList(ArrayList<LinkedList<PegJumpNode>> adjList) {
		this.adjList = adjList;
	}
}