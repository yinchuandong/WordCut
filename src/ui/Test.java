package ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	
	public static void main(String[] args){
		String msg = " һ/m ��/q ��/n ��/wd һ��/mq ����/vn ��/wj ��/rr һ��С��/dl ��/v ��/vf ��/ule ��/rr ��/ude1 ����/vn ��/wd ϲ��/vi ��/v ��/vf ����/n ����/nr2 ��/v ��/rzv ��/m Vae/x ��/wd �Ӵ�/d ��/rr ��/ude1 ����/n ��/f ��/d Ҳ/d ����/v û��/v ��/rr ��/wj ?/ww ?/ww ?/ww ����/wp ��/n ��/ng ��/wj ?/ww ?/ww ?/ww ��/rzv ����/n ��Ҫ/v ��/rr ��/wd ��Ϊ/c ��/rr ��/vshi һ��/mq ����/n ��/ude1 ��ʹ/n ";
		String msg2 = "123|abc|4532|adf|";
		Pattern pattern = Pattern.compile("( ([^ ])*?)(/n{1}) ");
		Matcher matcher = pattern.matcher(msg);	
		
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
	}
}
