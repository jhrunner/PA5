package PA5;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HTMLParser {
	// Holds parsed array.
	private ArrayList<String> parsedArray = new ArrayList<String>();
	private ArrayList<String> hyper = new ArrayList<String>();

	// Replaces unwanted characters and spaces. Used to create parsed ArrayList
	// from string.
	private static final String SPLIT_CODE = "<!split!>";

	// Constructor, parses the passed in html file
	public HTMLParser(String file) {
		File html = new File(file);
		parseHTML(html);
	}

	// Called in constructor, takes a file and creates a parsed ArrayList of words
	private void parseHTML(File file) {
		// Stores all contents of file in a single string
		String fullString = fileToString(file);
		// Turns the string into an array of words, split non accepted
		// characters
		ArrayList<String> parsedArray = splitToArray(fullString);
		// Removes empty strings
		parsedArray = removeEmptyEntries(parsedArray);
		// Makes all words in array lowercase
		parsedArray = arrayToLowercase(parsedArray);
		// Removes duplicate entries
		//	parsedArray = removeDuplicates(parsedArray);
		// Removes html commands/tags
		parsedArray = removeHTMLCommands(parsedArray);
		// Sorts the array alphanumerically
		//parsedArray = sortAlphanumeric(parsedArray);
		this.parsedArray = parsedArray;
	}

	// Returns parsed array
	public ArrayList<String> getParsedArray() {
		return parsedArray;
	}

	// Prints the parsed array
	public void printArray() {
		System.out.println("WORDS");
		for (String i : parsedArray)
			System.out.println(i);
		// Prints the number of unique words
		System.out.println("\nNUMBER OF WORDS: " + parsedArray.size());
	}

	// Adds a word to the parsed array.
	public void addToArray(String string) {
		// Format string
		string = formatString(string);
		// Check if string already exists in array
		if (!parsedArray.contains(string)) {

			// Binary search for position to add string
			//parsedArray.add(binarySearch(string, parsedArray), string);			TAKEN OUT!!!!!!!!!
			parsedArray.add(string);
		}
	}

	private int binarySearch(String string, ArrayList<String> array) {
		int currentPos = array.size()-1 / 2;
		int min = 0;
		int max = array.size()-1;
		// Returns the location that string will go in the array
		return binarySearchTail(array, string, min, max);
	}

	// Binary search to find where to place the given string an ArrayList
	public int binarySearchTail (ArrayList<String> word, String key,int first, int last){
		int mid = first + ((last-first)/2);

		if (word.size() == 0){
			return 0;
		}
		if (first>last){
			return mid;
		}
		if (key.equals(word.get(mid))){
			return mid;
		}
		if(word.get(mid).compareTo(key)>0){

			return binarySearchTail(word, key, first, mid-1);
		}
		if(word.get(mid).compareTo(key)<0){
			return binarySearchTail(word,key,mid+1, last);
		}
		else{
			return mid;
		}
	}

	// Removes all non letter/number characters and makes string lowercase
	private String formatString(String string) {
		char[] characterArray = string.toLowerCase().toCharArray();
		String formatted = "";
		for (char c : characterArray) {
			// Tests numbers 0-9
			if (c >= 48 && c <= 57)
				formatted += c;
			// Tests letters a-z
			if (c >= 97 && c <= 122)
				formatted += c;
			// Tests apostrophe
			if (c == 39)
				formatted += c;
		}
		return formatted.trim();
	}

	// Takes a file, creates a string of its contents
	private String fileToString(File file) {
		// Stores the contents of the file
		String fullFile = "";
		// Creates fullFile String
		try {
			Scanner in = new Scanner(file);
			while (in.hasNextLine()) {
				fullFile += in.nextLine() + "\n";
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found.");
			System.exit(0);
		}
		return fullFile.trim();
	}

	// Removes HTML takes from an ArrayList of words
	private ArrayList<String> removeHTMLCommands(ArrayList<String> array) {
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).contains("<") || array.get(i).contains(">")) {
				String str = array.get(i);
				str = str.trim().replaceAll("\\s+", "");
				if(str.length()>16){
					if (str.substring(1, 15).equals("ahref=\"http://")){
						str = str.substring(15,str.length()-2);
						//str = str.toLowerCase();
						if(!str.equals("") && !hyper.contains(str)){ 
							hyper.add(str);
						}
					}
				}
				array.remove(i);
				i--;
			}
		}
		return array;
	}
	public ArrayList<String> getHyper(){
		ArrayList<String> temp = new ArrayList<String>();
		for (String s : hyper){
			temp.add(s.toLowerCase());
			//System.out.println(s);
		}
		return temp;
	}

	// Splits a string into an ArrayList of words
	private ArrayList<String> splitToArray(String full) {
		// Takes a string and breaks it into a String[] by words, skips HTML
		// tags
		// Iterate through each character in the string
		boolean skipping = false;
		for (int i = 0; i < full.length(); i++) {
			// If the character is NOT either a letter or a number or an
			// apostrophe, set true to replace with SPLIT_CODE
			boolean characterPasses = false;
			// Tests numbers 0-9
			if (full.charAt(i) >= 48 && full.charAt(i) <= 57)
				characterPasses = true;
			// Tests letters A-Z
			if (full.charAt(i) >= 65 && full.charAt(i) <= 90)
				characterPasses = true;
			// Tests letters a-z
			if (full.charAt(i) >= 97 && full.charAt(i) <= 122)
				characterPasses = true;
			// Tests apostrophe
			if (full.charAt(i) == 39)
				characterPasses = true;
			// Tests left-angle bracket(begins skipping for HTML tag)
			if (full.charAt(i) == 60) {
				characterPasses = true;
				full = changeCharacterAtPosition(i, full, SPLIT_CODE + "<");
				i += SPLIT_CODE.length();
				skipping = true;
			}
			if (characterPasses == false && skipping == false) {
				// Swaps the character with a split code if it is not a
				// "passing" character
				full = changeCharacterAtPosition(i, full, SPLIT_CODE);

				// Advances the character check to pass over the newly created
				// split code.
				i += SPLIT_CODE.length() - 1;
			}
			// Tests left-angle bracket(ends skipping for HTML tag)
			if (full.charAt(i) == 62) {
				skipping = false;
				full = changeCharacterAtPosition(i, full, ">" + SPLIT_CODE);
				i += SPLIT_CODE.length();
			}
		}
		// Splits the array
		String[] split = full.split(SPLIT_CODE);
		return convertToArrayList(split);
	}

	// Removes empty strings in an array
	private ArrayList<String> removeEmptyEntries(ArrayList<String> array) {
		// ArrayList to hold non-empty strings
		ArrayList<String> temporaryArray = new ArrayList<String>();
		// Populates ArrayList
		for (String s : array)
			if (!s.equals(""))
				temporaryArray.add(s);
		return temporaryArray;
	}

	// Converts all entries in an ArrayList to lowercase
	private ArrayList<String> arrayToLowercase(ArrayList<String> array) {
		for (int i = 0; i < array.size(); i++) {
			array.set(i, array.get(i).toLowerCase().trim());
		}
		return array;
	}

	// Replaces a character in a string with another string
	private String changeCharacterAtPosition(int pos, String str, String rep) {
		String alteredString = str.substring(0, pos) + rep
				+ str.substring(pos + 1, str.length());
		return alteredString;
	}

	// Removes duplicates in an array of strings
	private ArrayList<String> removeDuplicates(ArrayList<String> fullArray) {
		// ArrayList to hold non-repeated words
		ArrayList<String> originals = new ArrayList<String>();
		// Populates ArrayList
		for (String s : fullArray)
			if (!originals.contains(s))
				originals.add(s);
		return originals;
	}

	// Sorts an ArrayList alphabetically
	private ArrayList<String> sortAlphanumeric(ArrayList<String> array) {
		ArrayList<String> sorted = new ArrayList<String>();
		for (String s : array) {
			sorted.add(binarySearch(s, sorted), s);
		}
		return sorted;
	}

//	public static void main(String[] args)
//	{
//		HTMLParser a = new HTMLParser("random");
//		String fullString = a.fileToString(new File("random"));
//		// Turns the string into an array of words, split non accepted
//		// characters
//		ArrayList<String> parsedArray = a.splitToArray(fullString);
//		parsedArray = a.removeEmptyEntries(parsedArray);
//		parsedArray = a.removeHTMLCommands(parsedArray);
//		for(String str: parsedArray)
//		{
//			//str.trim();
//			//str = str.replaceAll("\\s+","");
//			System.out.println(str);
//			System.out.println(a.hyper);
//
//		}
//		System.out.println(a.hyper.size());
//	}

	// Converts a String[] to an ArrayList
	private ArrayList<String> convertToArrayList(String[] array) {
		ArrayList<String> convert = new ArrayList<String>();
		for (String i : array)
			convert.add(i);
		return convert;
	}


}
