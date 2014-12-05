package PA5;

import java.util.TreeMap;
import java.util.Vector;

// code is from CS200 recitation 14 thx 
public class Graph {

	private int numVertices;
	

	private int numEdges;
	
	private boolean directed;
	
	private Vector<TreeMap<Integer, Integer>> adjList;

	public Graph(int n)
	{
		numVertices = n;
		numEdges = 0;
		adjList = new Vector<TreeMap<Integer, Integer>>();
		for(int i = 0; i < numVertices; i++)
		{
			adjList.add(new TreeMap<Integer, Integer>());
		}
	}
	
	public int getNumVertices() 
	{
		return numVertices;
	}
	
	public int getNumEdges()
	{
		return numEdges;
	}
	
	public int getEdgeWeight(Integer v, Integer w)
	{
		return adjList.get(v).get(w);
	}
	
	public void addEdge(Integer v, Integer w, int wgt) 
	{
		// Add the edge to both v's and w's adjacency list
		adjList.get(v).put(w, wgt);
		//adjList.get(w).put(v, wgt);
		//numEdges++;
		
	}
	
	public void addEdge(Edge e)
	{
		//Extract the vertices and weight from the edge e
		Integer v = e.getV();
		Integer w = e.getW();
		int weight = e.getWeight();
		addEdge(v, w, weight);
	}

	public void removeEdge(Edge e)
	{
		// Extract the vertices from the edge e
		Integer v = e.getV();
		Integer w = e.getW();
		
		// Remove the edge from v's and w's adjacency list
		adjList.get(v).remove(w);
		adjList.get(w).remove(v);
		numEdges--;
	}
	
	public Edge FindEdge(Integer v, Integer w)
	{
		int weight = adjList.get(v).get(w);
		return new Edge(v, w, weight);
	}
	

	TreeMap<Integer, Integer> getAdjList(Integer v)
	{
		return adjList.get(v);
	}
	
	public String toString()
	{
		String out="";
		
		for(int i=0; i<numVertices; i++)
		{
			out+=i;
			for(int j=0; j<numVertices; j++)
			{
				if(getAdjList(i).containsKey(j)&&FindEdge(i,j).getV()==i)
				//if(getAdjList(i).containsKey(j))
				{
					out+=" => "+j+"["+getEdgeWeight(i,j)+"]";
				}
			}
			out+="\n";
		}
		
		return out;
	}
	 
}