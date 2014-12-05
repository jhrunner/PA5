package PA5;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Query{
    private double termFrequency, documentFrequency, totalDocs;
    //              TF              DF                  D

    private ArrayList<Term> table;
    // number of docs read in
    private int totalDocuments;

    public Query(ArrayList table){
        this.table = table;
        totalDocuments = WebPages.getDocCount();
    }

    public double getTFIDF(String termName, String document){
        Term searchTerm = null;

        for(Term term: table){
            if(term.getName().compareTo(termName.toLowerCase().trim()) == 0){
                searchTerm = term;
            }
        }
        if(searchTerm != null) {
            termFrequency = searchTerm.getInDocumentFrequency(document);
            //System.out.println("termFrequency: " + termFrequency);
            totalDocs = totalDocuments;
            //System.out.println("totalDocs: " + totalDocs);
            documentFrequency = searchTerm.getDocFrequency();
            //System.out.println("documentFrequency: " + documentFrequency);
            double IDF = Math.log(totalDocs / documentFrequency);
            return (termFrequency * IDF);
        }

        return -1;
    }
    public double getWiq(Term term){
    	double numDocs = term.getDocFrequency(); // idf
    	//System.out.println ("test line: " + 0.5*(1+Math.log(totalDocuments/numDocs)));
    	return (0.5*(1+Math.log(((double) totalDocuments/(double)numDocs))));
    }
}
