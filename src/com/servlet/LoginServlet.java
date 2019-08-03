package com.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.Conn2Ora;
import com.util.Good;
import com.util.ShopCart;

import oracle.sql.BLOB;
//import org.hibernate.Hibernate;  

public class LoginServlet extends HttpServlet {

	/**
	 * request �� ������󣬰��������������Ĳ���
	 * 
	 * response ��Ӧ����
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String psw = null;// ��ʵ����
		int act = 0;// �����־
		int userid=0;//�û����
		String history_table_name="his_table";
		String search_table_name="search_key_t";
		PreparedStatement pst = null;
		ResultSet res = null;
		String sqlstatement = null;
		Conn2Ora conn2ora = Conn2Ora.getInstance();// ��ȡ�����ݿ�����ӣ����������֤�û���������
		Connection con = conn2ora.con;
		// saveList(con);
		// System.out.println("list����ɹ�");

		// Good g=new Good("003");
		// l.add(g);
		// list.clear();
		// Good good = new Good("00000001","iphone8", 6888, "123@qq.com",
		// "iphone���¿�",4);
		// list.add(good);//������Ʒ
		// updateGoodList(con,list);//���±��е�list
		// System.out.println("��С"+list.size());

		request.setCharacterEncoding("utf-8");
		String mailnumber = request.getParameter("loginname");// ��ȡ�˺�����
		String password = request.getParameter("nloginpwd");
		sqlstatement = "select password,active,userid from users where mail=\'" + mailnumber + "\'";
		try {
			pst = con.prepareStatement(sqlstatement);
			res = pst.executeQuery();
			if (res.next()) {
				psw = res.getString("password");
				act = res.getInt("active");
				userid=res.getInt("userid");
				if(userid!=0){
					history_table_name+=userid;
					search_table_name+=userid;
				}
			}

			if (psw == null) {// �����ݿ������û�����ʱ��resҲ��Ϊ��
				System.out.println("�޴��û���");
			} else if (psw.equals(password)) {
				if (act == 1) {//��½�ɹ�
					HttpSession session = request.getSession();
					session.setAttribute("onlineuser", mailnumber);// �����û���
					session.setAttribute("historytable", history_table_name);
					session.setAttribute("searchtable", search_table_name);
					// �û����߳ɹ��������жϸ��û��Ƿ����й��ﳵ����û���򴴽�һ�������棬������ȡ�����ﳵ
					ShopCart c = getShopCart(con, mailnumber);
					if (c == null) {// ���û��������ڹ��ﳵ
						c = new ShopCart(mailnumber);// �������ﳵʵ��
						// ���湺�ﳵʵ��
						saveObject(con, c);
					}else{
						System.out.println("���ﳵ������"+c.getTotal());
					}
					session.setAttribute("cart", c);

					// �����Ǳ��������û�
					// sqlstatement = "insert into usersOnline
					// values(\'"+mailnumber+"\')";
					// pst = con.prepareStatement(sqlstatement);
					// pst.execute();
					// con.commit();

					String loginState = (String) session.getAttribute("loginstate");// ��ȡ��¼�Ƿ����ڽ��빺�ﳵʱ�ĵ�¼
					// String addToCart = (String)
					// session.getAttribute("addToCart");
					if (loginState == null || loginState.equals("")) {
						System.out.println("ƽ����¼");
						response.sendRedirect("http://localhost:8080/WebShop/");
//						session.getServletContext().getRequestDispatcher(response.encodeURL("/home/home.jsp"))
//								.forward(request, response);
					} else if (loginState.equals("cart")) {
						System.out.println("�����ﳵ");
						session.setAttribute("loginstate", "");
						response.sendRedirect("home/cart.jsp");
//						session.getServletContext().getRequestDispatcher(response.encodeURL("/home/cart.jsp"))
//								.forward(request, response);
					} else if (loginState.equals("myStore")) {
						System.out.println("�ҵ��̳�ǰ�ĵ�¼");
						session.setAttribute("loginstate", "");
						response.sendRedirect("home/myStore.jsp");
//						session.getServletContext().getRequestDispatcher(response.encodeURL("/home/myStore.jsp"))
//								.forward(request, response);
					} else if (loginState.equals("addToCart")) {
						System.out.println("���빺�ﳵǰ�ĵ�¼");
						session.setAttribute("loginstate", "");
						response.sendRedirect("home/search.jsp");
//						session.getServletContext().getRequestDispatcher(response.encodeURL("/home/search.jsp"))
//								.forward(request, response);
					}else if (loginState.equals("upload")) {
						System.out.println("�ϴ���Ʒǰ�ĵ�¼");
						session.setAttribute("loginstate", "");
						response.sendRedirect("home/upload.jsp");
//						session.getServletContext().getRequestDispatcher(response.encodeURL("/home/upload.jsp"))
//								.forward(request, response);
					}
				} else {
					System.out.println("���û���δ����");
				}
			} else {
				System.out.println("�������");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}

	// ������������ݿ�
	@SuppressWarnings("deprecation")
	public void saveObject(Connection con, ShopCart c) {
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream outObj = new ObjectOutputStream(byteOut);
			outObj.writeObject(c);
			final byte[] objbytes = byteOut.toByteArray();
			Statement st = con.createStatement();
			st.executeUpdate("insert into cartofuser (mail, cart) values (\'" + c.getMail() + "\', empty_blob())");
			ResultSet rs = st.executeQuery("select cart from cartofuser where mail=\'" + c.getMail() + "\' for update");
			if (rs.next()) {
				BLOB blob = (BLOB) rs.getBlob("cart");
				OutputStream outStream = blob.getBinaryOutputStream();
				outStream.write(objbytes, 0, objbytes.length);
				outStream.flush();
				outStream.close();
			}
			byteOut.close();
			outObj.close();
			con.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ȡ�û��Ĺ��ﳵ
	public ShopCart getShopCart(Connection con, String mail) {
		ShopCart cart = null;
		Statement st = null;
		ResultSet rs = null;
		try {

			con.setAutoCommit(false);
			st = con.createStatement();
			// ȡ��blob�ֶ��еĶ��󣬲��ָ�
			rs = st.executeQuery("select cart from cartofuser where mail=\'" + mail + "\'");
			BLOB inblob = null;
			if (rs.next()) {
				inblob = (BLOB) rs.getBlob("cart");
			}
			if (inblob != null) {
				InputStream is = inblob.getBinaryStream();
				BufferedInputStream input = new BufferedInputStream(is);

				byte[] buff = new byte[inblob.getBufferSize()];
				while (-1 != (input.read(buff, 0, buff.length)))
					;

				ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buff));
				cart = (ShopCart) in.readObject();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(cart.getMail());
		return cart;
	}
	//���¹��ﳵ�����ݿ��еĴ洢
	public static void updateCart(Connection con, ShopCart cart){
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream outObj = new ObjectOutputStream(byteOut);
			outObj.writeObject(cart);
			final byte[] objbytes = byteOut.toByteArray();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select cart from cartofuser where mail=\'" + cart.getMail() + "\' for update");
			if (rs.next()) {
				BLOB blob = (BLOB) rs.getBlob("cart");
				OutputStream outStream = blob.getBinaryOutputStream();
				outStream.write(objbytes, 0, objbytes.length);
				outStream.flush();
				outStream.close();
			}
			byteOut.close();
			outObj.close();
			con.commit();
			System.out.println("���ݿ��еĹ��ﳵ���³ɹ�");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
