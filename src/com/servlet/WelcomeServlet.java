package com.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.Conn2Ora;
import com.util.Good;

import oracle.sql.BLOB;

public class WelcomeServlet extends HttpServlet {
	/**
	 * ������ҳǰ�ȷ���welcomeServlet������һЩ��ʼ��
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Conn2Ora conn2ora = Conn2Ora.getInstance();
		Connection con = conn2ora.con;// �����ݿ������
//		saveList(con);//����goodslist
		ArrayList<Good> list = getList(con);// ��ȡ����ϵͳ����Ʒ����
		System.out.println(list.size()+" ����������");
		System.out.println(list.get(0).getGoodfilename()+" "+list.get(0).getGoodDescription());
		HttpSession session = request.getSession();
		session.setAttribute("goodslist", list);// ���棬����ͻ��˻�ȡ

		session.getServletContext().getRequestDispatcher(response.encodeURL("/home/home.jsp")).forward(request,
				response);// ��ת����ҳ
		
		
		/*System.out.println("������"+list.size());
		Good g=new Good("00000001","iphone", 6888, "123@qq.com", "iphone���¿�",4,"11.jpg");
		list.add(g);
		updateGoodList(con,list);//����
		list = getList(con);
		System.out.println("������"+list.size());
		
		list.get(0).setGoodfilename("11.png");
		
		list = getList(con);
		
		list.add(g);
		updateGoodList(con,list);//����
		list = getList(con);*/

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// ��װGood�����ArrayList�������ݿ�
	@SuppressWarnings("deprecation")
	public void saveList(Connection con) {
		ArrayList<Good> goodslist=new ArrayList<Good>();
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream outObj = new ObjectOutputStream(byteOut);
			outObj.writeObject(goodslist);
			final byte[] objbytes = byteOut.toByteArray();

			Statement st = con.createStatement();
			st.executeUpdate("insert into listtable (list) values (empty_blob())");
			ResultSet rs = st.executeQuery("select list from listtable for update");
			if (rs.next()) {
				BLOB blob = (BLOB) rs.getBlob("list");
				OutputStream outStream = blob.getBinaryOutputStream();
				outStream.write(objbytes, 0, objbytes.length);
				outStream.flush();
				outStream.close();
			}
			byteOut.close();
			outObj.close();
			con.commit();
			System.out.println("goodslist����ɹ�");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateGoodList(Connection con, ArrayList<Good> list) {
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream outObj = new ObjectOutputStream(byteOut);
			outObj.writeObject(list);
			final byte[] objbytes = byteOut.toByteArray();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select list from listtable for update");
			if (rs.next()) {
				BLOB blob = (BLOB) rs.getBlob("list");
				OutputStream outStream = blob.getBinaryOutputStream();
				outStream.write(objbytes, 0, objbytes.length);
				outStream.flush();
				outStream.close();
			}
			byteOut.close();
			outObj.close();
			con.commit();
			System.out.println("goodlist���³ɹ�");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ȡArrayList����
	public static ArrayList<Good> getList(Connection con) {
		ArrayList<Good> list = null;
		Statement st = null;
		ResultSet rs = null;
		try {

			con.setAutoCommit(false);
			st = con.createStatement();
			// ȡ��blob�ֶ��еĶ��󣬲��ָ�
			rs = st.executeQuery("select list from listtable");
			BLOB inblob = null;
			if (rs.next()) {
				inblob = (BLOB) rs.getBlob("list");
				System.out.println(inblob);
			}
			if (inblob != null) {
				// list = (ArrayList<Good>)inblob.toJdbc();
				InputStream is = inblob.getBinaryStream();
				ObjectInputStream in = new ObjectInputStream(is);
				list = (ArrayList<Good>) in.readObject();
				// BufferedInputStream input = new BufferedInputStream(is);
				//
				// byte[] buff = new byte[inblob.getBufferSize()];
				// while (-1 != (input.read(buff, 0, buff.length)))
				// ;
				//
				// ObjectInputStream in = new ObjectInputStream(new
				// ByteArrayInputStream(buff));
				// list = (ArrayList<Good>) in.readObject();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("��ȡlist�ɹ�");
		return list;
	}

}
