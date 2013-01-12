package tjx.trs.run;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import tjx.trs.pojo.URLScore;
import tjx.trs.util.StaticValue;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;
import love.cq.util.IOUtil;

public class Recall {
	public static void main(String[] args) throws IOException, Exception {
		BufferedReader reader = IOUtil.getReader(new FileInputStream("D:\\ѵ����\\url��ȷ.txt"), "GBK");
		String temp = null;
		int success = 0;
		while ((temp = reader.readLine()) != null) {

			String[] split = temp.toLowerCase().split("\t");
			//System.out.println(split[1]);

			if (filter(temp.toLowerCase(),split[1])) {
				//System.out.println(split[1]);
				success++;
				
			}else{
				System.out.println(split[0]+":"+split[1]);
			}
		}
	//	System.out.println(success);
		int error = 0;
		reader = IOUtil.getReader(new FileInputStream("D:\\ѵ����\\���������.txt"), "GBK");
		while ((temp = reader.readLine()) != null) {
			// System.out.println(temp);
			String[] split = temp.toLowerCase().split("\t");

			if (filter(split[2].toLowerCase(),split[5].toLowerCase())) {
				error++;
				//System.out.println(split[2]+":"+split[5]);
			}
		}
		 Double R=((double) success / 500);
		 Double P=((double) success / (success + error));
		 Double F=2*P*R/(P+R);
		System.out.println("�ٻ���=" +R);
		System.out.println("׼ȷ��=" +P );
		System.out.println("Fֵ="+F);
	}

	private static Forest forest = null;

	public static boolean filter(String query,String url) throws Exception {
		if (forest == null) {
			forest = Library.makeForest("data/library.dic");
			// forest = Library.makeForest(IOUtil.getReader("data/��������Զ���.txt",
			// "UTF-8")) ;
		}
		GetWord gw = new GetWord(forest, query);
		double pe1 = 1;
		double pe2 = 1;
		int size = 0;
		int tempAllFreq = 0;
		String temp = null;

		while ((temp = gw.getFrontWords()) != null) {
			pe1 = pe1 * Double.parseDouble(gw.getParam(3));
			pe2 = pe2 * (1 - Double.parseDouble(gw.getParam(3)));
			tempAllFreq = Integer.parseInt(gw.getParam(2));
			size++;
		}
		// ���ֻ�ֳ�һ�������������ʵ�otherƵ�� ����1000 ��ô�����������ҹ������ ���� ����ƥ�� 0.946122608833829
		// ���ִʾͲ��������
	
		double p = pe1 / (pe1 + pe2);
		
		URLScore us = StaticValue.getUrlScore(url) ;
		
		//wo xian cou he xie a  
		if(us!=null&&us.score>0.8&&us.computer>2){
		//	System.out.println(query);
			return true ;
		}
		
		if (size == 0) {
			return false;
		}

		if (p > 0.85) {
			return true;
		}
		return false;
	}
}
