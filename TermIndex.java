package PA5;

public interface TermIndex {
    public void add(String filename, String newWord);
    public int size();
    public void delete(String word);
    // remove boolean from this and add in BST 
    public Term get(String word, Boolean printP);
	}