package PA5;

import java.util.ArrayList;
import java.util.Iterator;

public class HashIterator<Term> implements Iterator<Term> {
	HashTable table;
	ArrayList<Term> queue;
	public HashIterator(HashTable table){
		this.table = table;
		queue = new ArrayList<Term>();
		fillQueue();
	}
	@Override
	public boolean hasNext() {	
		return (!queue.isEmpty());
	}

	@Override
	public Term next() {
		if(hasNext()){
			Term term = queue.get(0);
			queue.remove(0);
			return term;
		}
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	public void fillQueue(){
		for(int i=1; i<table.size(); i++){
			//System.out.println(table.getTable().length);
			
			if(table.getTerm(i) != null && !table.getTerm(i).getName().equals("Reserved")){
				Term term = (Term) table.getTerm(i);
				queue.add(term);
			}
		}
	}

}
