package com.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/*
 * ����oracle���ݿ�
 */
public class Conn2Ora implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Conn2Ora instance=null;
	 public static Connection con = null;
	 public static Statement st = null;
	 
//	 public static
	
	public static Conn2Ora getInstance() {
        if(instance==null){
        	synchronized (Conn2Ora.class) {  //�ӳټ���Ҫ���ǲ��������⣬Ҫ���������
        		if(instance==null){
        			instance=new Conn2Ora();
        			}
               }
        }
        return instance;
	}
	static {

		try {
			System.out.println("��ʼ����");
			Class.forName("oracle.jdbc.driver.OracleDriver");// ����Oracle��������
			System.out.println("��ʼ�����������ݿ⣡");
			String url = "jdbc:oracle:" + "thin:@Poe:1521:PORACLE";// 127.0.0.1�Ǳ�����ַ��XE�Ǿ����Oracle��Ĭ�����ݿ���
			String user = "special";// �û���,ϵͳĬ�ϵ��˻���
			String password = "special";// �㰲װʱѡ���õ�����
			con = DriverManager.getConnection(url, user, password);// ��ȡ����
			st=con.createStatement();
			con.setAutoCommit(false);
			System.out.println("���ӳɹ���");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
