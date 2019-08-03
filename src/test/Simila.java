package test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.util.Good;

public class Simila {
	private static int compare(String str, String target)
    {
        int d[][];              // ����
        int n = str.length();
        int m = target.length();
        int i;                  // ����str��
        int j;                  // ����target��
        char ch1;               // str��
        char ch2;               // target��
        int temp;               // ��¼��ͬ�ַ�,��ĳ������λ��ֵ������,����0����1
        if (n == 0) { return m; }
        if (m == 0) { return n; }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++)
        {                       // ��ʼ����һ��
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++)
        {                       // ��ʼ����һ��
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++)
        {                       // ����str
            ch1 = str.charAt(i - 1);
            // ȥƥ��target
            for (j = 1; j <= m; j++)
            {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2+32 || ch1+32 == ch2)
                {
                    temp = 0;
                } else
                {
                    temp = 1;
                }
                // ���+1,�ϱ�+1, ���Ͻ�+tempȡ��С
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    private static int min(int one, int two, int three)
    {
        return (one = one < two ? one : two) < three ? one : three;
    }

    /**
     * ��ȡ���ַ��������ƶ�
     */

    public static float getSimilarityRatio(String str, String target)
    {
        return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
    }

    public static void main(String[] args)
    {
    	System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());
    	ArrayList<Good> list=new ArrayList<Good>();
    	Good g1=new Good("00000001","iphone", 6888, "123@qq.com", "iphone���¿�",4,"11.jpg");
    	Good g2=new Good("00000002","��Ϊ", 6888, "123@qq.com", "��Ϊ���¿�",4,"11.jpg");
    	Good g3=new Good("00000003","iphone8", 6888, "123@qq.com", "iphone8���¿�",4,"11.jpg");
    	list.add(g1);list.add(g2);list.add(g3);
    	for(Good g:list){
    		System.out.print(g.getGoodName()+" ");
    	}
    	System.out.println();
    	final String key="iPhone8";
    	Collections.sort(list, new Comparator<Good>() {
            public int compare(Good g1, Good g2) {
            	float simi1=(getSimilarityRatio(g1.getGoodName(),key)+getSimilarityRatio(g1.getGoodDescription(),key))/2;
            	float simi2=(getSimilarityRatio(g2.getGoodName(),key)+getSimilarityRatio(g2.getGoodDescription(),key))/2;
            	return ((Float)simi2).compareTo((Float)simi1);
            }
        });
    	for(Good g:list){
    		System.out.print(g.getGoodName()+" ");
    	}
    	
    	
    }

}
