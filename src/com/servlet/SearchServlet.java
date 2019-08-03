package com.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.Conn2Ora;
import com.util.Good;
import com.util.Similarity;


public class SearchServlet extends HttpServlet {
	
	 public static String [] getFileName(String path)
	    {
	        File file = new File(path);
	        String [] fileName = file.list();
	        return fileName;
	    }
	    public static void getAllFileName(String path,ArrayList<String> fileName)
	    {
	        File file = new File(path);
	        File [] files = file.listFiles();
	        String [] names = file.list();
	        if(names != null)
	        fileName.addAll(Arrays.asList(names));
	        for(File a:files)
	        {
	            if(a.isDirectory())
	            {
	                getAllFileName(a.getAbsolutePath(),fileName);
	            }
	        }
	    }
	
	
	/**
	 * request �� ������󣬰��������������Ĳ���
	 * 
	 * response ��Ӧ����
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Conn2Ora conn2ora=Conn2Ora.getInstance();
		Connection con = conn2ora.con;
		Statement st=conn2ora.st;
		ResultSet rs=null;
		String sql=null;//sql���
		int num=0;//ָcount(*)ѡ���ı���
		int search_amount=0;//��ʾĳ���ؼ�����ǰ�����Ĵ���
		
		//��ƷͼƬ�ļ���·��
		String goodspath=this.getServletContext().getRealPath("/")+"home\\goodsimgs";
		//��Ʒ�ڷ������е��ļ�������"���+ͼƬ��"���,�ڻ�ȡͼƬ����Ҫ���з���
		 String [] goodsName = getFileName(goodspath);//��ƷͼƬ�ļ���,��Ʒ�ڷ�������
//		 List<String> goodNoList = new ArrayList<String>();//ͼƬ��Ʒ���
//		 for(String s:goodsName){
//			 String[] temp=s.split("\\+");
//			 goodNoList.add(temp[0]);
//		 }
		 String searchtable=(String)session.getAttribute("searchtable");//���ڴ洢�����ؼ��ֵı���
		// ����Ա��¼��֤
		request.setCharacterEncoding("utf-8");
		final String keyword = request.getParameter("key"); // ��ȡ�ؼ���
		System.out.println("�ؼ���"+keyword);
		if(searchtable!=null && keyword!="" && keyword!=null){//��¼״̬���������عؼ���
			sql="select count(*) num from "+searchtable+" where keyword=\'"+keyword+"\'";
			try {
				rs=st.executeQuery(sql);
				if(rs.next()){
					num=rs.getInt("num");
				}
				if(num==0){//��ʾ�����޴˹ؼ��֣�Ӧ�ò���ùؼ���
					sql="insert into "+searchtable+" values(\'"+keyword+"\',1)";
					st.execute(sql);
					con.commit();
				}else{//��ʾ�����иùؼ���
					sql="select amount from "+searchtable+" where keyword=\'"+keyword+"\'";
					rs=st.executeQuery(sql);
					if(rs.next()){
						search_amount=rs.getInt("amount")+1;
					}
					sql="update "+searchtable+" set amount="+search_amount+" where keyword=\'"+keyword+"\'";
					st.execute(sql);
					con.commit();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		ArrayList<Good> list = (ArrayList<Good>)session.getAttribute("goodslist");//��ȡ������Ʒ�б�
		if(keyword!=null){//�ؼ��ַǿգ����ؼ��ֶ�list��������
			Collections.sort(list, new Comparator<Good>() {
	            public int compare(Good g1, Good g2) {
	            	float simi1=similarity_g_key(g1,keyword);
	            	float simi2=similarity_g_key(g2,keyword);
	            	return ((Float)simi2).compareTo((Float)simi1);
	            }
	        });
		}
		session.setAttribute("searchlist", list);
		
		
		request.setAttribute("result", goodsName);//���÷���ֵ
		//request.setAttribute("goodnolist", goodNoList);//������Ʒ��ŷ���ֵ
		
//		request.getRequestDispatcher("home/search.jsp").forward(request, response);
		response.sendRedirect("home/search.jsp");
			
		//request.getRequestDispatcher("/login.jsp").forward(request, response);

		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	/**
	 * ���������ַ���֮������ƶȣ����㷨����
	 * @param info��ʾ��Ʒ�����ֻ�����Ʒ������
	 * @param key���صĹؼ���
	 * @return����һ������0~1֮���С������ʾ���ƶ�
	 */
	private float similarity_info_key(String info,String key){
		float similarity=0;
		Similarity s=new Similarity();
		similarity=s.getSimilarityRatio(info, key);
		return similarity;
	}
	/**
	 * ������Ʒ�͹ؼ���֮������ƶȣ����㷨����Ʒ�������ƶ�*0.5+��Ʒ���������ض�*0.5����
	 * @param g��ʾ��Ʒ
	 * @param key��ʾ���ѵĹؼ���
	 * @return�������ƶȣ�Ϊһ������0~1֮���С��
	 */
	public float similarity_g_key(Good g,String key){
		float similarity=0;
		similarity=(similarity_info_key(g.getGoodName(),key)+similarity_info_key(g.getGoodDescription(),key))/2;
		return similarity;
	}
	
	
	
}
