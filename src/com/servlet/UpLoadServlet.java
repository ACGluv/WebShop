package com.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.util.Conn2Ora;
import com.util.Good;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UpLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// �ϴ��ļ��洢Ŀ¼
	private static final String UPLOAD_DIRECTORY = "home\\goodsimgs";

	// �ϴ�����
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	/**
	 * �ϴ����ݼ������ļ�
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		HttpSession session = request.getSession();
		Conn2Ora conn2 = Conn2Ora.getInstance();
		Connection con = conn2.con;
		// ��ȡ�洢��Ʒ������
		ArrayList<Good> list = com.servlet.WelcomeServlet.getList(con);
		String goodNo = String.format("%08d", (list.size() + 1));// ��ȡ��Ʒ���
		System.out.println("��ţ�" + goodNo);
		String goodName = null;// ��Ʒ��
		Integer goodPrice = null;// ��Ʒ�۸�
		String goodUserSale = (String) session.getAttribute("onlineuser");// ��Ʒ�ϴ���
		String goodDescription = null;// ��Ʒ����
		Integer goodNum = null;// ��Ʒ������-ָĳ���û��ϴ���ĳһ����Ʒ������
		String goodfilename = null;// �ϴ����������ϵ�ͼƬ���ļ��������������
		String type = null;// ��Ʒ������
		String detial_type = null;//��ϸ����
		// ����Ƿ�Ϊ��ý���ϴ�
		if (!ServletFileUpload.isMultipartContent(request)) {
			// ���������ֹͣ
			PrintWriter writer = response.getWriter();
			writer.println("Error: ��������� enctype=multipart/form-data");
			writer.flush();
			return;
		}

		// �����ϴ�����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// ������ʱ�洢Ŀ¼
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		// ��������ļ��ϴ�ֵ
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// �����������ֵ (�����ļ��ͱ�����)
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// ������ʱ·�����洢�ϴ����ļ�
		// ���·����Ե�ǰӦ�õ�Ŀ¼
		String uploadPath = getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;
		// String uploadPath = "G:\\�½��ļ���";

		// ���Ŀ¼�������򴴽�
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			// ���������������ȡ�ļ�����
			@SuppressWarnings("unchecked")
			List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 0) {
				// ����������
				for (FileItem item : formItems) {
					// �����ڱ��е��ֶ�
					if (!item.isFormField()) {
						File f = new File(item.getName());
						String fileName = f.getName();
						goodfilename = fileName;
						String filePath = uploadPath + File.separator + goodNo + "+" + fileName;
						File storeFile = new File(filePath);
						// �ڿ���̨����ļ����ϴ�·��
						System.out.println("·���� " + filePath);
						// �����ļ���Ӳ��
						item.write(storeFile);

					} else {
						String temp = new String(item.getString("UTF-8"));
						String itemfildname = item.getFieldName();
						if (itemfildname.equals("goodname")) {
							goodName = temp;// ��Ʒ��
							System.out.println("���֣�" + goodName);
						} else if (itemfildname.equals("pro_shortdesc")) {
							goodDescription = temp;
							System.out.println("������" + goodDescription);
						} else if (itemfildname.equals("goodprice")) {
							goodPrice = Integer.parseInt(temp);// ��Ʒ�۸�
							System.out.println("�۸�" + goodPrice.intValue());
						} else if (itemfildname.equals("goodamount")) {
							goodNum = Integer.parseInt(temp);
							System.out.println("��棺" + goodNum.intValue());
						} else if (itemfildname.equals("goodtype")) {
							type = temp;
							System.out.println("���ͣ�" + type);
						} else if (itemfildname.equals("detailid")) {
							detial_type = temp;
							System.out.println("��ϸ���ͣ�" + detial_type);
						}

					}
				}
				Good g = new Good(goodNo, goodName, goodPrice, goodUserSale, goodDescription, goodNum, goodfilename,
						type,detial_type);
				list.add(g);
				com.servlet.WelcomeServlet.updateGoodList(con, list);// ����list
				request.setAttribute("message", "�ļ��ϴ��ɹ�!");
			}
		} catch (Exception ex) {
			request.setAttribute("message", "������Ϣ: " + ex.getMessage());
		}
		// ��ת�� message.jsp
		getServletContext().getRequestDispatcher("/home/message.jsp").forward(request, response);
	}
}
