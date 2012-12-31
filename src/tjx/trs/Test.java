package tjx.trs;

import java.io.BufferedReader;
import java.io.StringReader;

import javax.crypto.Cipher;

import love.cq.domain.Forest;
import love.cq.library.Library;
import love.cq.splitWord.GetWord;

public class Test {
	public static void main(String[] args) throws Exception {
		 /**
         * �ʵ�Ĺ���.һ��һ���ʺ����ǲ���.���Դ��ļ���ȡ.������read��.
         */
        String dic = "�й�\t1\tzg\n����\t2\n�й�����\t4\n����\t3\n�｡\t5\nCSDN\t6\njava\t7\njavaѧϰ\t10\n";
        
        
       //����tire��
        
        Forest forest = Library.makeForest(new BufferedReader(new StringReader(dic)));

   
        String content = "�й�����ʶ�����й������һ������.�｡������CSDN��ѧ���˺ܶ�����iteye��javaѧϰ�ʼǽ�javaeye����java123ֻ��һ����";
        GetWord udg = forest.getWord(content);

        String temp = null;
        while ((temp = udg.getFrontWords()) != null)
       
        	
            System.out.println(temp + "\t\t" + udg.getParam(0) + "\t\t" + udg.getParam(1)+"\t\t" + udg.getParam(3));

	}
}
