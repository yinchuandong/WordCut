package ui;

import java.util.Comparator;

class Word implements Comparator<Word>{
	
	public String word = "";
	public int num = 0; 
	
	public Word(){
		
	}
	
	public Word(String word){
		this.word = word;
		this.num = 1;
	}
	
	public Word(String word, int num){
		this.word = word;
		this.num = num;
	}

	@Override
	public int compare(Word o1, Word o2) {
		if (o1.num < o2.num) {
			return -1;
		}else{
			return 1;
		}
	}
}
