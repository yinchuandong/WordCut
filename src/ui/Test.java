package ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	
	public static void main(String[] args){
		String msg = " 一/m 首/q 歌/n ，/wd 一个/mq 回忆/vn 。/wj 我/rr 一不小心/dl 走/v 进/vf 了/ule 你/rr 的/ude1 回忆/vn ，/wd 喜欢/vi 戴/v 上/vf 耳机/n 静静/nr2 听/v 那/rzv 首/m Vae/x ，/wd 从此/d 我/rr 的/ude1 世界/n 里/f 再/d 也/d 不能/v 没有/v 你/rr 。/wj ?/ww ?/ww ?/ww ——/wp 题/n 记/ng 。/wj ?/ww ?/ww ?/ww 这/rzv 世界/n 需要/v 你/rr ，/wd 因为/c 你/rr 是/vshi 一个/mq 音乐/n 的/ude1 天使/n ";
		String msg2 = "123|abc|4532|adf|";
		Pattern pattern = Pattern.compile("( ([^ ])*?)(/n{1}) ");
		Matcher matcher = pattern.matcher(msg);	
		
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
	}
}
