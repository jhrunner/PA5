package PA5;
public class Occurrence {
    //Document name where Term object occurs
    private String docName;
    //Number of times Term object occurs in *docName*. Word count
    private int termFrequency;
    //Next occurrence in linked list
    public Occurrence next;

    //Constructor
    public Occurrence(String name){
        this.docName = name;
        this.termFrequency = 1;
    }

    //Increase word frequency by 1
    public void incFrequency(){
        this.termFrequency++;
    }

    //Get name of document the word occurred in
    public String getDocName() {
        return docName;
    }

    //Get number of times the word appear
    public int getTermFrequency() {
        return termFrequency;
    }

    public void printOcc(){
        System.out.println("Occurrence: " + docName + " " + termFrequency);
    }
}
