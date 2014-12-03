package PA5;

import java.util.Iterator;

public class HashTable implements TermIndex, Iterable<Term> {
	Term [] table;
	//	int initialSize;
	int count;
	public HashTable(int size){
		table = new Term [size];
		count =0;

	}
	@Override
	public void add(String filename, String newWord) {
		//System.out.println("newWord: " + newWord);
		Term term = new Term(filename,newWord);
		double percent=(double) (count+1)/(table.length);			//moved this to check before we set initialcode because 
		if (percent >= 0.8){									// table length might change when taking mod
			table = resize();
		}
		int initialCode = term.getName().hashCode();
		initialCode = Math.abs(initialCode);
		initialCode = initialCode%(table.length);
		int code=initialCode;
		Boolean added = false;
		int i =1;
		while (!added){
//			if(code>=table.length)
//			{
//				table=resize();								//when we resize, we need to reset our counter and initialCode and code
//				initialCode = term.getName().hashCode();	//because the hash values have now changed for term.getWord
//				initialCode = Math.abs(initialCode);
//				initialCode = initialCode%(table.length);
//				code=initialCode;
//				i=1;
//			}
			if (table[code] == null || table[code].getName().equals("Reserved")){
				added = true;
			}
			else{
				code = (int) (initialCode + Math.pow(i, 2))%table.length;
				i++;
			}
		}
		if (added){
			//System.out.println("added: "+ term);
			table[code] = term;
			count++;
		}
	}
	public Boolean isEmpty(){
		if (count == 0){
			return true;
		}
		else 
			return false;
	}

	private Term[] resize()			//When rehashing, also have to redo the quad probing for all the term words with same hash value
	{								//in the old array
		Term[] newArray = new Term[(2*(table.length))+1];
		//		for (int i=0; i<table.length; i++){
		//			newArray[i] = table[i];
		//		}
		for(int j=0; j<table.length; j++)		// Basically copy of the add method, insert the old terms into new array with new hashValues
		{
			if(table[j] != null && !table[j].getName().equals("Reserved"))
			{
				Term term = table[j];
				int initialCode = term.getName().hashCode();
				initialCode = Math.abs(initialCode);
				initialCode = initialCode%(newArray.length); // NewArray.length to mod old values by new table size 
				int code=initialCode;
				Boolean added = false;
				int i = 1;
				while (!added)
				{
					if (newArray[code] == null || newArray[code].getName().equals("Reserved")){
						added = true;
					}
					else{
						code = (int) (initialCode + Math.pow(i, 2));
						i++;
					}
				}
				if(added)
				{
					newArray[code]=term;
				}
			}
		}
		return newArray;
	}

	@Override
	public int size() {
		return table.length;
	}

	@Override
	public void delete(String word) {
		int initialCode = Math.abs(word.hashCode());
		initialCode = initialCode%(table.length);
		int code=initialCode;
		if (table[code] == null)
			return;
		if (table[code].getName().equals(word)){
			table[code] = new Term("Reserved");
			count--;
			return;
		}
		else{

			Boolean found =false;
			int i=1;
			while(!found){
				code = (int) (initialCode + Math.pow(i, 2));
				if (table[code] == null)
					return;
				if(code>=table.length)
					return;
				if (table[code].getName().equals(word)){
					table[code] = new Term("Reserved");
					found = true;
					count--;
				}
				i++;

			}
		}

	}

	@Override
	public Term get(String word, Boolean printP) {
		int initialCode = Math.abs(word.hashCode());
		initialCode = initialCode%(table.length);
		int code=initialCode;
		if (table[code].getName().equals(word)){
			return table[code];
		}
		if (table[code] == null)
			return null;
		else{
			Boolean found =false;
			int i=1;
			while(!found){
				code = (int) (initialCode + Math.pow(i, 2));
				if(code>=table.length)
					return null;
				if (table[code].getName().equals(word)){
					return table[code];
				}
				if (table[code] == null)
					return null;
				i++;
			}
		}
		return null;
	}
	public Iterator<Term> iterator() {
		return new HashIterator<Term>(this);
	}

	public Term getTerm(int i)
	{

		return table[i];
	}
	public Term[] getTable()
	{
		return table;
	}

}
