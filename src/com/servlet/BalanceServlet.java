package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BalanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public BalanceServlet() {
        super();
    }
    /**
     * ���㣬һ������Servlet����������������
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] tele = request.getParameterValues("teleCheckbox");//��ȡ��ѡ���ֵ</span>
		System.out.println("����Servlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
