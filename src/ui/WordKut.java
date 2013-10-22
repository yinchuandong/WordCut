package ui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.omg.PortableInterceptor.DISCARDING;

import ICTCLAS.I3S.AC.ICTCLAS50;

public class WordKut {
	
	public ArrayList<Word> wordsList = new ArrayList<Word>();
	
	public WordKut(){
		
	}
	
	/**
	 * 判断当前单词是否已经在wordsList里面，若找到，返回index;没找到，返回-1
	 * @param word
	 * @return
	 */
	public int findWord(String word){
		int len = wordsList.size();
		for (int i = 0; i < len; i++) {
			Word temp = wordsList.get(i);
			if (temp.word.equals(word)) {
				return i;
			}
		}
		return -1;
	}
	
	public void addWord(String word){
		int index = findWord(word);
		if (index != -1) {
			wordsList.get(index).num++;//更新词语的数目
		}else{
			wordsList.add(new Word(word));
		}
	}
	
	public String readFile(String fileName){
		String result = "";
		try {
			File file = new File(fileName);
			FileInputStream inputStream = new FileInputStream(file);
			byte[] buff = new byte[1024*4];
			int len = inputStream.read(buff);
			while(len != -1){
				result += new String(buff, 0, len, "utf-8");
				len = inputStream.read(buff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	

	public void cutWord(String content){
		try {
			ICTCLAS50 ictclas50 = new ICTCLAS50();
			String argu = ".";
			if (ictclas50.ICTCLAS_Init(argu.getBytes("utf-8"))==false) {
				System.out.println("init false");
			}else{
				System.out.println("init true");
			}
			
//			byte[] nativeBytes = ictclas50.ICTCLAS_ParagraphProcess(content.getBytes("utf-8"), 0, 1);
//			String nativeStr = new String(nativeBytes);
//			System.out.println("未加入用户词典"+nativeStr);
			
			String userdict = "userdict.txt";
			int nCount = ictclas50.ICTCLAS_ImportUserDictFile(userdict.getBytes(), 2);
//			System.out.println("用户词典数目"+nCount);
			
			byte[] nativeBytes1 = ictclas50.ICTCLAS_ParagraphProcess(content.getBytes("utf-8"), 0, 1);
			String nativeStr1 = new String(nativeBytes1);
//			System.out.println("加入用户词典: " + nativeStr1);
			Pattern pattern = Pattern.compile("( ([^ ])*?)(/n(\\w)*) ");
			Matcher matcher = pattern.matcher(nativeStr1);	
			
			while (matcher.find()) {
				addWord(matcher.group(1).trim());
//				System.out.println(matcher.group());
			}
			Collections.sort(wordsList, new Word());
			Collections.reverse(wordsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void display(){
		for (Word temp : wordsList) {
			System.out.println(temp.word + "," + temp.num);
		}
	}
	
	public static void main(String[] args){
		WordKut model = new WordKut();
		String content = model.readFile("article/党的十八大2.txt");//这世界需要你
		model.cutWord(content);
//		model.display();
		Classification classification = new Classification();
		classification.loadClassFromDir("Classification");
		HashMap<String, Double> resultMap = classification.classify(model.wordsList);
		Set<String> keySet = resultMap.keySet();
		System.out.println();
		for (String key : keySet) {
			System.out.println(key + "-->" + resultMap.get(key));
		}
		
	}
}

