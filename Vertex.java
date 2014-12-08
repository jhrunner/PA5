package PA5;

import java.util.ArrayList;

public class Vertex implements Comparable{
	
	private String name;
	public ArrayList<String> hyper;
	public Vertex(String s){
		hyper = new ArrayList<String>();
		name = s;
	}
	public String getname(){
		return name;
	}
	public void addLink(String s){
		for(String find:hyper)
		{
			if (find.equals(s))
				return;
		}
		hyper.add(s);
	}
	@Override
	public int compareTo(Object o) {
		 String name1 = name;
         String name2 = ((Vertex) o).getname();
         int size = name1.length();
         if (name1.length() > name2.length())
             size = name2.length();
             for (int i = 0; i < size; i++) {
                 char word1 = name1.charAt(i);
                 char word2 = name2.charAt(i);
                 if (word1 < word2) {
                     return -1;
                 }
                 if (word1 > word2) {
                     return 1;
                 }

             }
         if (name1.length() < name2.length())
             return -1;
         else
             return 1;
	}
//	public static void main(String[] args){
//		Vertex v = new Vertex("sup");
//		System.out.println(v.compareTo("supper"));
//	}
}
