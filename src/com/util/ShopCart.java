package com.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ���ﳵ�࣬ÿ���û���Ӧһ�����ﳵ��ÿ�����ﳵӵ����Ʒ������ÿ����Ʒ����ϸ��Ϣ
 * 
 * @author Poe
 *
 */
public class ShopCart implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4558876142427402513L;
	private String mail;// ���û���
	private int total;// ��Ʒ������
	private ArrayList<Good> goodList;// ������Ʒ����
	private ArrayList<Integer> numList;// ����Ʒ��Ӧ���������
	private Conn2Ora conn2ora;
	// private Connection con;

	// �����û������������ﳵ
	public ShopCart(String mail) {
		this.mail = mail;
		this.goodList = new ArrayList<Good>();
		this.numList = new ArrayList<Integer>();
		this.conn2ora = Conn2Ora.getInstance();// ��ȡ�����ݿ�����ӣ����������֤�û���������
		// this.con = conn2ora.con;
	}

	public String getMail() {
		return mail;
	}

	// �����ﳵ�������Ʒ
	public void addGoods(Good g) {
		if (!this.goodList.contains(g)) {
			this.goodList.add(g);
			this.numList.add(1);// �����ʼ����
			this.total++;
		} else {
			System.out.println("��Ʒ�Ѵ��ڹ��ﳵ��");
		}
	}

	// ������Ż�ȡ��Ʒ
	public Good getGoods(int i) {
		return this.goodList.get(i);
	}

	// ���ﳵ�л�ȡ��Ʒ
	public Good getGoods(String goodNo) {
		for (Good g : goodList) {
			if (g.getGoodNo().equals(goodNo)) {
				return g;
			}
		}
		System.out.println("���ﳵ���޸���Ʒ");
		return null;
	}

	// �����ﳵ��ɾ����Ʒ
	public void deleteGoods(Good g) {
		if (!this.goodList.contains(g)) {
			int index = this.goodList.indexOf(g);
			this.numList.remove(index);
			this.goodList.remove(g);
			this.total--;
		} else {
			System.out.println("��Ʒ���ڹ��ﳵ��");
		}
	}

	// ������Ʒ���ɾ����Ʒ
	public boolean deleteGoods(String goodno) {
		if (!contain(goodno)) {
			System.out.println("����Ʒ���ڹ��ﳵ��");
			return false;
		} else {
			for (int i = 0; i < this.goodList.size(); i++) {
				if (this.goodList.get(i).getGoodNo().equals(goodno)) {
					this.goodList.remove(i);
					this.numList.remove(i);
					this.total--;
					return true;
				}
			}
			return false;
		}
	}

	// ��ȡ���ﳵ����Ʒ����
	public int getTotal() {
		// String sqlstatement = "select count(*) total from cart where mail=\'"
		// + this.mail + "\'";
		// PreparedStatement pst = null;
		// ResultSet res = null;
		// try {
		// pst = con.prepareStatement(sqlstatement);
		// res = pst.executeQuery();
		// if(res.next()){
		// this.total = res.getInt("total");
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		total = this.goodList.size();
		return total;
	}

	// ��ȡ���ﳵ�е�������Ʒ�ļ���
	public ArrayList<Good> getGoodList() {
		String sqlstatement = "select goodno from cart where mail= \'" + this.mail + "\'";
		PreparedStatement pst = null;
		ResultSet res = null;
		Connection con = this.conn2ora.con;
		try {
			pst = con.prepareStatement(sqlstatement);
			res = pst.executeQuery();
			while (res.next()) {
				String goodNo = res.getString("goodno");
				Good g = new Good(goodNo);
				this.goodList.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return goodList;
	}

	public ArrayList<Integer> getNumList() {
		return this.numList;
	}

	// ������Ʒ��Ż�ȡ��Ʒ���������
	public int getBuyNum(String goodno) {
		int num = 0;
		for (int i = 0; i < this.goodList.size(); i++) {
			if (this.goodList.get(i).getGoodNo().equals(goodno)) {
				num = this.numList.get(i);
				System.out.println("����Ϊ��" + num);
				break;
			}
		}
		return num;
	}

	public void addNum(int i) {
		int now_num = this.numList.get(i) + 1;
		this.numList.set(i, now_num);
	}

	public void decreaseNum(int i) {
		int now_num = this.numList.get(i) - 1;
		this.numList.set(i, now_num);
	}

	// �жϹ��ﳵ���Ƿ���ĳ����Ʒ
	public boolean contain(String goodNo) {
		for (Good g : this.goodList) {
			if (g.getGoodNo().equals(goodNo)) {
				return true;// �Ѿ�����
			}
		}
		return false;
	}

}
