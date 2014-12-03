package PA5;
import java.util.ArrayList;

//TEst comment
public class Term implements Comparable {
    //Set 0 to compare alphabetically, set 1 to compare by index
    public static int compareType = 0;
    //Word parsed from web page
    private String name;
    //Number of documents the word appears in. NOT a word count
    private int docFrequency;
    //Array of occurrences, each contain the number of times the word was in a specified document
    private LinkedOccurrence docsList = new LinkedOccurrence();

    //Constructor
    public Term(String name) {
        docFrequency = 0;
        this.name = name;
    }

    //Constructor
    public Term(String document, String name){
        docFrequency = 0;
        this.name = name;
        addNewOccurrence(document);
    }

    //Increases words document frequency by 1
    public void incFrequency() {
        docFrequency++;
    }

    //Updates docsList by one term occurring in *document*
    public void addNewOccurrence(String document) {
        //Duplicate checking
        boolean add = true;
        for(Occurrence occ: docsList.toArray()){
            if(occ.getDocName().compareTo(document) == 0){
                add = false;
                occ.incFrequency();
            }
        }

        if(add){
            docsList.insert(document);
            incFrequency();

        }
    }
    

    //Returns the word the term is associated with
    public String getName() {
        return name;
    }

    //Returns total count of the word in all documents
    public int getTotalFrequency() {
        int total = 0;
        for (Occurrence occ : docsList.toArray()) {
            total += occ.getTermFrequency();
        }
        return total;
    }

    public ArrayList<Occurrence> getDocsList() {
        return docsList.toArray();
    }

    @Override
    public int compareTo(Object o) {
        if(compareType == 0) {
            String name1 = this.name;
            String name2 = ((Term) o).name;
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
        if(compareType == 1){
            Integer count1 = this.getTotalFrequency();
            Integer count2 = ((Term) o).getTotalFrequency();
            return count1.compareTo(count2);
        }
        return 0;
    }

    //Returns frequency of term in specified document
    public int getInDocumentFrequency(String document){
        for(Occurrence occ: docsList.toArray()){
            if(occ.getDocName().compareTo(document) == 0){
                return occ.getTermFrequency();
            }
        }
        return -1;
    }

    //Returns total number of documents the term is in
    public int getDocFrequency() {
        return docFrequency;
    }
    public String toString()
    {
    	return "name: " + name;
    }
}