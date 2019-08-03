package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.Conn2Ora;

/**
 * ���������ɾ����������servlet
 */
public class PayAndDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PayAndDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String state = request.getParameter("condition");// ״̬�룬pay��ʾ���delete��ʾɾ��
		String ord_id = request.getParameter("ord_id");// ������Ʒ���
		String mail = request.getParameter("mail");// ������Ʒ���
		Connection con = Conn2Ora.con;
		Statement st = Conn2Ora.st;
		String sql = null;
		String url = null;// Ҫ��ת��url
		String mial = null;//���򶩵����û�����
		try {
			if (state.equals("pay")) {// ��������ݿ����޸�
				sql = "update order_t set pay=1 where ord_id=\'" + ord_id + "\'";
				st.execute(sql);
				System.out.println("��������ɹ�");
				con.commit();
				url = "home/buyer_order.jsp";
			} else if (state.equals("delete")) {// ɾ������
				sql = "delete from order_t where ord_id=\'" + ord_id + "\'";
				st.execute(sql);
				System.out.println("����ɾ���ɹ�");
				con.commit();
				url = "home/buyer_order.jsp";
			} else if (state.equals("confirm_receive")) {// ȷ���ջ�
				sql = "update order_t set receive_state=1 where ord_id=\'" + ord_id + "\'";
				st.execute(sql);
				System.out.println("�����ջ��ɹ�");
				con.commit();
				url = "home/payed_order.jsp";
			} else if (state.equals("confirm_send")) {// ����
				sql = "update order_t set send_state=1 where ord_id=\'" + ord_id + "\'";
				st.execute(sql);
				System.out.println("���������ɹ�");
				con.commit();
				url = "home/send_after_order.jsp";
			} else if (state.equals("evaluate")) {// ����ɹ��
				String pkscore = request.getParameter("pkscore");// ��װ����
				String spscore = request.getParameter("spscore");// �ٶ�����
				String atscore = request.getParameter("atscore");// ̬������
				String goscore = request.getParameter("goscore");// ��Ʒ����
				String tags = request.getParameter("tags");// ��ǩ�ַ�������","���ӣ�ֱ�Ӵ������ݿ�
				String def_text = request.getParameter("def_text");// �Զ����ǩ
				String words = request.getParameter("words");// ��������
				String anony = request.getParameter("anony");// �������ۣ�Ĭ��Ϊ��0��1-��
				// String[] tagArray = tags.split(",");//��ǩ�ַ�������
				sql = "select mail from order_t where ord_id=\'"+ord_id+"\'";
				ResultSet rs = st.executeQuery(sql);//ȡ���û�����
				if(rs.next()){
					mail = rs.getString("mail");
				}

				System.out.println(pkscore + " " + spscore + " " + atscore + " " + goscore + " " + tags + " " + def_text
						+ " " + words + " " + anony);
				sql = "insert into evaluation(eva_id,ord_id,mail,eva_date,pkscore,spscore,atscore,goscore,tags,def_text,words,anony) values(eva_id_seq.nextval,"
						+ ord_id + ",\'" + mail + "\',sysdate,\'" + pkscore + "\',\'" + spscore + "\',\'" + atscore
						+ "\',\'" + goscore + "\',\'" + tags + "\',\'" + def_text + "\',\'" + words + "\',\'" + anony+"\')";
				System.out.println(sql);
				 st.execute(sql);
				 con.commit();
				 url = "home/evaluate_success.jsp?ord_id="+ord_id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect(url);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
