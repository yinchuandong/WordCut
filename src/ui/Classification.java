package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Classification {

	ArrayList<Word> modelList = new ArrayList<Word>();
	ArrayList<Word> articleList = new ArrayList<Word>();
	HashMap<String, ArrayList<Word>> modelMapList = new HashMap<String, ArrayList<Word>>();
	
	public Classification(){
		loadClassFromFile();
	}
	
	public void classify(ArrayList<Word> articleList){
		ArrayList<Integer> vectorA = new ArrayList<Integer>();//样本集
		ArrayList<Integer> vectorB = new ArrayList<Integer>();//未分类
		for (Word word : articleList) {
			vectorB.add(word.num);
			int index = findWord(word, modelList);
			if (index != -1) {
				System.out.println(word.word);
				vectorA.add(modelList.get(index).num);
			}else{
				vectorA.add(0);
			}
		}
		double similarity = calCosineSimilarity(vectorA, vectorB);
		System.out.println(similarity);
	}
	
	public double calCosineSimilarity(ArrayList<Integer> vectorA, ArrayList<Integer> vectorB){
		int len = vectorA.size();
		if (len <2) {
			return 0;
		}
		double ab = 0;
		double aa = 0;
		double bb = 0;
		for(int i=0; i < len; i++){
			ab += vectorA.get(i) * vectorB.get(i);
			aa += vectorA.get(i) * vectorA.get(i);
			bb += vectorB.get(i) * vectorB.get(i);
		}

		double result = ((double)ab)/(Math.sqrt((double)aa) * Math.sqrt((double)bb));
		return result;
	}
	
	private int findWord(Word word, ArrayList<Word> list) {
		for (int i = 0; i < list.size(); i++) {
			Word temp = list.get(i);
			if (temp.word.equals(word.word)) {
				return i;
			}
		}
		return -1;
	}
	
	public void loadClassFromFile(){
		try {
			File file = new File("Classification/c001.txt");
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String str = null;
			while((str = reader.readLine()) != null){
				String[] temp = str.split(",");
				Word word = new Word(temp[0], Integer.parseInt(temp[1]));
				modelList.add(word);
//				System.out.println(word.word+"/"+word.num);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		Classification classification = new Classification();
		classification.loadClassFromFile();
		
	}
	
}
