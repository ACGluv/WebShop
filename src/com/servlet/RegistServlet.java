package com.servlet;

import com.util.Conn2Ora;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistServlet extends HttpServlet{
	
	/**
	 * request �� ������󣬰��������������Ĳ���
	 * 
	 * response ��Ӧ����
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Conn2Ora conn2ora = Conn2Ora.getInstance();
		Connection con = conn2ora.con;
		Statement st=null;
		int userid=0;
		String history_table_name="his_table";//�û������¼���ñ��б������û��������Ʒ��ţ�ʱ�䣬����
		String search_table_name="search_key_t";//�û������ؼ��ֱ�
		String caregood_name = "caregood_t";//��ע��Ʒ�б�
		String caresaler_name = "caresaler_t";//��ע���̱���
		ResultSet rs=null;
		String sql=null;
		
		request.setCharacterEncoding("utf-8");
		String mailnumber = request.getParameter("mail");//��ȡ�˺�����
		String password = request.getParameter("password");//����
		System.out.println(mailnumber+"  "+password);
		try {
			st = con.createStatement();
			rs=st.executeQuery("select userid_seq.nextval from dual");
			while(rs.next()){
				userid = rs.getInt("nextval");//��ȡ�û�id
				history_table_name+=userid;
				search_table_name+=userid;
				caregood_name += userid;
				caresaler_name += userid;
				System.out.println("ѭ�� "+userid);
			}
			System.out.println("id "+userid);
			sql="insert into users(mail,password,active,userid) values(\'"+mailnumber+"\',\'"+password+"\',1,"+userid+")";
			st.execute(sql);//����ע���û�
			sql="create table "+history_table_name+"(goodno char(8),see_date date,amount int,primary key(goodno,see_date))";
			st.execute(sql);//������ʷ��¼��
			sql="create table "+search_table_name+"(keyword varchar2(20),amount int)";
			st.execute(sql);//���������ؼ��ֱ�
			sql="create table "+caregood_name+"(goodno char(8) primary key,mail VARCHAR2(20) REFERENCES users(mail))";
			st.execute(sql);//�����û���ע��Ʒ�б�
			sql="create table "+caresaler_name+"(saler VARCHAR2(20) REFERENCES users(mail))";
			st.execute(sql);//�������û���ע���̱�
			con.commit();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		

		response.sendRedirect("home/registSuccess.jsp");
			

		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

}
