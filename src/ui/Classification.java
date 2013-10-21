package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Classification {

//	ArrayList<Word> modelList = new ArrayList<Word>();
//	ArrayList<Word> articleList = new ArrayList<Word>();
	HashMap<String, ArrayList<Word>> modelMapList = new HashMap<String, ArrayList<Word>>();
	
	public Classification(){
	}
	
	/**
	 * 计算当前文章和各个分类的余弦相似性
	 * @param articleList
	 * @return
	 */
	public HashMap<String, Double> classify(ArrayList<Word> articleList){
		HashMap<String, Double> resultMap = new HashMap<String, Double>();
		Set<String> keySet = modelMapList.keySet();
		for (String key : keySet) {//遍历各个分类的特征词库
			ArrayList<Word> classList = modelMapList.get(key);
			ArrayList<Integer> vectorA = new ArrayList<Integer>();//样本集
			ArrayList<Integer> vectorB = new ArrayList<Integer>();//未分类
			for (Word word : articleList) {
				vectorB.add(word.num);
				int index = findWord(word, classList);
				if (index != -1) {
					System.out.println(word.word);
					vectorA.add(classList.get(index).num);
				}else{
					vectorA.add(0);
				}
			}
			double similarity = calCosineSimilarity(vectorA, vectorB);
			resultMap.put(key, similarity);
		}
		return resultMap;
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
	
	/**
	 * 在list中搜索word,存在则返回index;不存在则返回-1
	 * @param word
	 * @param list
	 * @return
	 */
	private int findWord(Word word, ArrayList<Word> list) {
		for (int i = 0; i < list.size(); i++) {
			Word temp = list.get(i);
			if (temp.word.equals(word.word)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 从模板目录中加载特征分类词库
	 * @param dirPath
	 */
	public void loadClassFromDir(String dirPath){
		try {
			File directory = new File(dirPath);
			File[] lists = directory.listFiles();
			for (File file : lists) {
				@SuppressWarnings("resource")
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String str = null;
				ArrayList<Word> modelList = new ArrayList<Word>();
				while((str = reader.readLine()) != null){
					String[] temp = str.split(",");
					Word word = new Word(temp[0], Integer.parseInt(temp[1]));
					modelList.add(word);
//					System.out.println(word.word+"/"+word.num);
				}
				String key = file.getName().split("\\.")[0];
				modelMapList.put(key, modelList);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		Classification classification = new Classification();
		classification.loadClassFromDir("Classification");
		
	}
	
}
