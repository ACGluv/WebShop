package com.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.Conn2Ora;
import com.util.Good;
import com.util.ShopCart;

public class AddToCartServlet extends HttpServlet {
	/**
	 * �������ﳵ�������Ʒ
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ArrayList<Good> list = (ArrayList<Good>) session.getAttribute("goodslist");// ��ȡ��Ʒ�б�
		ShopCart cart = (ShopCart) session.getAttribute("cart");// ��ȡ���û��Ĺ��ﳵ
		String goodNo = request.getParameter("goodno");// ��ȡ��Ʒ���
		System.out.println(goodNo);
		if (!cart.contain(goodNo)) {
			for (Good g : list) {
				if (g.getGoodNo().equals(goodNo)) {
					cart.addGoods(g);// ���ﳵ�������Ʒ
					LoginServlet.updateCart(Conn2Ora.con, cart);
					System.out.println("���빺�ﳵ�ɹ�");
					break;
				}
			}
			response.sendRedirect("http://localhost:8080/WebShop/home/addsuccess.jsp?goodno="+goodNo);
		} else {
			System.out.println("��Ʒ�Ѿ����ڹ��ﳵ��");
			response.sendRedirect("http://localhost:8080/WebShop/home/carthave.jsp?goodno="+goodNo);
		}

		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
