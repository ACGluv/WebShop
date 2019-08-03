package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.Conn2Ora;
import com.util.ShopCart;

/**
 * Servlet implementation class DeleteFromCartServlet
 */
public class DeleteFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteFromCartServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		ShopCart cart = (ShopCart) session.getAttribute("cart");// ��ȡ�û����ﳵ
		int index = Integer.parseInt(request.getParameter("index"));// ���
		String flag = request.getParameter("state");
		System.out.println("flag "+flag);
		System.out.println("car: "+cart.getNumList().size());
		if (flag != null && flag != "") {
			if (flag.equals("add")) {// ��������
				cart.addNum(index);
				LoginServlet.updateCart(Conn2Ora.con, cart);
				response.sendRedirect("http://localhost:8080/WebShop/home/cart.jsp");
			} else if (flag.equals("decrease")) {// ��С����
				cart.decreaseNum(index);
				LoginServlet.updateCart(Conn2Ora.con, cart);
				response.sendRedirect("http://localhost:8080/WebShop/home/cart.jsp");
			}
		} else {// ɾ����Ʒ
			System.out.println("ɾ����Ʒ");
			String goodno = request.getParameter("goodno");// ��ȡ��ɾ����Ʒ���
			boolean state = cart.deleteGoods(goodno);// ɾ����Ʒ
			LoginServlet.updateCart(Conn2Ora.con, cart);
			session.setAttribute("cart", cart);
			if (state) {
				System.out.println("��Ʒ�ӹ��ﳵ��ɾ���ɹ�");
				session.setAttribute("delete_state", "success");
				response.sendRedirect("http://localhost:8080/WebShop/home/cart.jsp");
			} else {
				System.out.println("����");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
