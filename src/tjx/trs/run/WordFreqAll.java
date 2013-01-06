package tjx.trs.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;
import love.cq.util.StringUtil;
import tjx.trs.pojo.WordFreq;
import tjx.trs.util.MyIOUtil;
import tjx.trs.util.StaticValue;

public class WordFreqAll {

	private static final Forest FOREST = StaticValue.getForest();

	public static void main(String[] args) throws Exception {
		BufferedReader reader = IOUtil.getReader("data/csdn_class.txt", "UTF-8");
		String temp = null;
		HashMap<String, WordFreq> hm = new HashMap<String, WordFreq>();
		String[] strings = null;
		WordFreq value = null;
		while ((temp = reader.readLine()) != null) {
			strings = temp.split("\t");
			value = new WordFreq();
			hm.put(strings[0], value);
		}
		File[] files = null ;
		Set<Entry<String, Integer>> entrySet = StaticValue.CATEGORYMAP.entrySet();
		for (Entry<String, Integer> entry : entrySet) {
			BufferedReader reader2 = IOUtil.getReader("data/training/" + entry.getKey() + ".txt", "UTF-8");
			while ((temp = reader2.readLine()) != null) {// zheli cuxin le hehe
				setFreq(hm, temp, 1);
			}
		}

		// other word freq
		files = new File("D:\\����\\SogouQ\\all").listFiles();
		for (int i = 0; i < files.length; i++) {
			if (!files[i].canRead() || !files[i].getName().endsWith("sogou")) {
				continue;
			}
			BufferedReader reader2 = IOUtil.getReader(new FileInputStream(files[i]), "GBK");

			while ((temp = reader2.readLine()) != null) {
				String[] split = temp.toLowerCase().split("\t");
				temp = split[2];
				setFreq(hm, temp, 0);
			}
		}
		
		
		files = new File("D:\\ѵ����\\������ѧtc-corpus-train\\train\\").listFiles() ;
		for (int i = 0; i < files.length; i++) {
			if(files[i].isDirectory()&&!files[i].isHidden()){
				
				int status = 0 ;
				if(files[i].getName().equals("C19-Computer")){
					continue ;
				}
				File[] allFiles = files[i].listFiles() ;
				for (File file : allFiles) {
					if(file.getName().toLowerCase().endsWith(".txt")&&file.canRead()){
						setFreq(hm, MyIOUtil.getContent(file), status);
					}
				}
			}
		}
		
		files = new File("D:/BlogData/").listFiles() ;
		for (int i = 0; i < files.length; i++) {
			if(files[i].isDirectory()&&!files[i].isHidden()){
				
				int status = 1 ;
				File[] allFiles = files[i].listFiles() ;
				for (File file : allFiles) {
					if(file.getName().toLowerCase().endsWith(".txt")&&file.canRead()){
						JSONObject jObject = JSONObject.parseObject(MyIOUtil.getContent(file))  ;
						setFreq(hm, StringUtil.rmHtmlTag(jObject.getString("content")), status);
					}
				}
			}
		}

		Set<Entry<String, WordFreq>> entrySet2 = hm.entrySet();
		StringBuilder sb = new StringBuilder();
		int id = 0;
		for (Entry<String, WordFreq> entry : entrySet2) {
			sb.append(entry.getKey());
			sb.append("\t");
			sb.append(id);
			sb.append("\t");
			sb.append(entry.getValue().getComputer());
			sb.append("\t");
			sb.append(entry.getValue().getOther());
			sb.append("\t");
			sb.append(entry.getValue().getBayesValue());
			sb.append("\n");
			id++;
		}
		FileOutputStream fos = new FileOutputStream(new File("data/library.dic"));
		fos.write(sb.toString().getBytes("UTF-8"));
		fos.flush();
		fos.close();
	}

	private static void setFreq(HashMap<String, WordFreq> hm, String temp, int status) {
		// TODO Auto-generated method stub
		GetWord gWord = new GetWord(FOREST, temp);
		String key = null;
		while ((key = gWord.getFrontWords()) != null) {
			if (status == 0) {
				hm.get(key).setOtherValue(1) ;
			} else {
				hm.get(key).setComputerValue(1);
			}
		}
	}
}
