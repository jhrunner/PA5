package PA5;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Graph {

	public ArrayList<Vertex> vert;

	public Graph(){
		vert = new ArrayList<Vertex>();

	}

	public void addVertex(Vertex v){
		for (Vertex find: vert){
			if (find.getname().equals(v.getname()))
				return;
		}
			vert.add(v);
		Collections.sort(vert);
	}
	
	public void addLink(String one, String two)
	{
		Vertex v = new Vertex(two);
		addVertex(v);
		if(getVertex(one)!=null){
			getVertex(one).addLink(two);
			//System.out.println("got here");
		}
	}
	public Vertex getVertex(String docName){

		for(Vertex v: vert)
		{
			if(v.getname().equals(docName))
				return v;
		}
		return null;

	}
	public int inDegree(String find){
		int count =0;
		for(Vertex v: vert ){
			if (!v.getname().equals(find))
				for(String s: v.hyper){
					//System.out.println(s);
					if(s.equals(find))
						count++;
				}
		}
		return count;
	}
	public void writeFile(String output){
		try{
			PrintWriter fileOut = new PrintWriter ( new File (output));
			fileOut.println("digraph program5 {");
			for(Vertex v: vert ){
				for(String s: v.hyper){
					fileOut.println("\""+v.getname()+"\" -> \""+s+"\";");
				}
			}
			fileOut.println("}");
			fileOut.close();
		}catch(Exception e){
			System.err.println(e);
		}
	}
	public static void main(String [] args){
		Graph graph = new Graph();
		graph.addVertex(new Vertex("sup"));
		graph.addVertex(new Vertex("apple"));
		graph.addVertex(new Vertex("teacher"));
		graph.addVertex(new Vertex("sup"));
		graph.addLink("apple","teacher");
		graph.addLink("sup","teacher");
		graph.addLink("apple","banana");
		//System.out.println(graph.vert);
		for (Vertex vert: graph.vert){
			System.out.println(vert.getname());
		}
		System.out.println(graph.inDegree("teacher"));
		System.out.println(graph.inDegree("sup"));
		System.out.println(graph.inDegree("apple"));
		System.out.println(graph.inDegree("banana"));
		graph.writeFile("testOutput");
		
	}
}
