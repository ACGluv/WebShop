package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.Conn2Ora;
import com.util.Good;
import com.util.ShopCart;

/**
 * ���������������Ժ󸶿��������servlet�������� 1�����ﳵ��ɾ��ѡ����Ʒ 2��������Ʒ�б�����Ʒ���������� 3������Ѹ������������������
 * 4�����Ҵ�������������
 */
public class AfterPay extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AfterPay() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Connection con = Conn2Ora.con;
		Statement st = Conn2Ora.st;
		String mail = (String) session.getAttribute("onlineuser");// �����û�����
		ShopCart cart = (ShopCart) session.getAttribute("cart");// ���ﳵ
		String address = request.getParameter("address");// ��ַ
		String nickname = request.getParameter("nickname");// �ջ���
		String state = request.getParameter("paystate");// ����״̬����right��after
		String goodnos_string = request.getParameter("goodnos");
		String[] goodnos = goodnos_string.split(",");// ����ѡ����Ʒ���
		// ��ȡ��ǰʱ����Oracle���ݿ����
		String sql = null;// sql���
		// 1���޸���Ҷ�����ÿ����Ʒ��Ŷ�Ӧһ��Ԫ��
		for (int i = 0; i < goodnos.length; i++) {// ÿ����Ʒ��Ŷ�Ӧ�������е�һ��Ԫ��
			String goodno = goodnos[i];// ȡ����Ʒ���
			Good g = cart.getGoods(goodno);// ��ȡ��Ӧ��Ʒ
			int ord_price = g.getGoodPrice();// ��Ʒ����
			int ord_num = cart.getBuyNum(goodno);// ��������
			int money = ord_price * ord_num;// �ܼ�
			int pay = 0;// ����״̬
			String seller = g.getGoodUserSale();// ��������
			if (state.equals("right")) {
				pay = 1;
			} else {
				pay = 0;
			}
			sql = "insert into order_t(ord_id,mail,goodno,ord_date,ord_price,ord_num,money,ord_address,pay,seller,receiver,send_state,receive_state) values(ord_id_seq.nextval,\'"
					+ mail + "\',\'" + goodno + "\',sysdate," + +ord_price + "," + ord_num + "," + money + ",\'"
					+ address + "\'," + pay + ",\'" + seller + "\',\'" + nickname + "\',0,0)";
			System.out.println(sql);
			try {
				st.execute(sql);
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 2���޸�����
			// 3�����ﳵ��ɾ����Ʒ
			cart.deleteGoods(g.getGoodNo());
			LoginServlet.updateCart(con, cart);//����

		}

		response.sendRedirect("home/buyer_order.jsp");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
