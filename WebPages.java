package PA5;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class WebPages {
	//Holds list of Term objects associated with each parsed word in web page
	private HashTable termIndex;
	private ArrayList<String> docList;
	//Number of webpage files
	private static int docCount;

	//Constructor
	public WebPages(int size){
		docCount = 0;
		termIndex = new HashTable(size);
		docList = new ArrayList<String>();
	}

	//Passes filename to HTMLParser to get parsed array WITH duplicates
	//Calls adds terms to termIndex
	public void addPage(String document){

		//add to termIndex the parsed words from *document*
		HTMLParser pageParser = new HTMLParser(document);
		docList.add(document);
		docCount++;

		//For each word in our parsed array...
		for(String word: pageParser.getParsedArray()){
			//System.out.println("word: " + word);
			if (termIndex.isEmpty()) {
				addNewTerm(word, document);
			}

			else{
				boolean add = false;
				boolean cont = true;
				for(Term term: termIndex){
					//System.out.println("Term: " + term);
					if(cont) {
						add = true;
						if (word.equals(term.getName())) {
							term.addNewOccurrence(document);
							cont = false;
							add = false;
						}
					}
				}
				if(add) {
					addNewTerm(word, document);
				}
			}
		}
	}

	private void addNewTerm(String name, String document){
		termIndex.add(document, name);
	}

	//Iterates through the array of termIndex and prints each word
	public void printTerms() {
		System.out.println("WORDS");
		for (Term word : termIndex) {
			System.out.println(word.getName());
		}
	}

	//Prints which pages *word* exist on
	public String[] whichPages(String word) {
		word = word.toLowerCase();
		ArrayList<String> pages = new ArrayList<String>();
		for(Term term: termIndex){
			if (term.getName().equals(word)){
				for(Occurrence occ: term.getDocsList()){
					pages.add(occ.getDocName());
				}
			}
		}
		return pages.toArray(new String[pages.size()]);
	}
	public void pruneStopWords(String word){
		//System.out.println("The Word is: " + word);
		for (Term term : termIndex){
			if (term.getName().equals(word)){
				termIndex.delete(word);
			}
		}
	}
	public void bestPages(String query){
		DecimalFormat fmt = new DecimalFormat("0.00");
		double [] common = new double [docCount];
		double [] docSpecific = new double [docCount];
		double [] simValues = new double [docCount];
		double queryWeights=0;
		double tfidf = 0;
		Query search = new Query(termIndex);
		for (Term term : termIndex){
			//System.out.println("term: " + term);
			for (String word : query.split(" ")){
				word = word.toLowerCase();
				// Determines if term is in the query 
				if (term.getName().equals(word)){
					queryWeights += Math.pow(search.getWiq(term),2);
					//System.out.println("Wiq values: " + queryWeights);
					for (Occurrence occ : term.getDocsList()){
						tfidf = search.getTFIDF(term.getName(), occ.getDocName());//wid
						common[docList.indexOf(occ.getDocName())] += (tfidf*(search.getWiq(term)));
					}
				}				
			}
			for(Occurrence occ : term.getDocsList()){
				
				double tfSquared = Math.pow((search.getTFIDF(term.getName(), occ.getDocName())), 2);
				docSpecific[docList.indexOf(occ.getDocName())] += tfSquared;
			}
			
		}
		for (int i=0; i<docList.size(); i++){
			double a = Math.sqrt(docSpecific[i]);
			double b = Math.sqrt(queryWeights);
			
			simValues[i] = common[i]/(a*b);
		}
		double max =0;
		int index =0;
		for (int i =0; i< docCount; i++){
			double a = simValues[i];
			if (a >= max){
				max = a;
				index=i;
			}
		}
		//System.out.println("common[]: " + Arrays.toString(common));
		//System.out.println("DocSpec: " + Arrays.toString(docSpecific));
		//System.out.println("simValues: " + Arrays.toString(simValues));
		//System.out.println("common at max: " + fmt.format(common[index]));
		//System.out.println("specifics at max: " + fmt.format(docSpecific[index]));
		//System.out.println("queryWeight at max: " + queryWeights);
		//printQuery(query);
		System.out.println(" in " + docList.get(index) + ": " + fmt.format(max) );
	} 
	public void printQuery(String query){
		ArrayList<String> str = new ArrayList<String>();
		for (String s: query.split(" ")){
			str.add(s.toLowerCase());
		}
		Collections.sort(str);
		String done = "";
		for (String s : str)
			done += s + " ";
		System.out.print("[" + done + "]");
		
	}
	public static int getDocCount() {
		return docCount;
	}

	public HashTable getTermIndex() {
		return termIndex;
	}
}