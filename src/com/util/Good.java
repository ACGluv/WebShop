package com.util;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * ��ȡ��Ʒ��Ϣ�� ����ÿһ����Ʒ������Ʒ��š���Ʒ������Ʒ�۸���Ʒ�ϴ��ߡ���Ʒ��������Ʒ��������Щ����
 * 
 * @author Poe
 *
 */
public class Good implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4558876127402513L;
	private String goodNo;// ��Ʒ���
	private String goodName;// ��Ʒ��
	private Integer goodPrice;// ��Ʒ�۸�
	private String goodUserSale;// ��Ʒ�ϴ���
	private String goodDescription;// ��Ʒ����
	private Integer goodNum;// ��Ʒ������-ָĳ���û��ϴ���ĳһ����Ʒ������
	private String goodfilename;// ��ֻͼƬ���ļ��������������
	private String picture_name;// ��������ͼƬ�����������
	private String type;// ��Ʒ�����ͺţ��У�iPhoneϵ�У���Ϊϵ�У�С��ϵ�У�OPPOϵ�У�vivoϵ�У�����ϵ�У�һ��ϵ�У�����ϵ��
	private String detil_type;// ��Ʒ��Ӧϵ��������ͺţ�
	/*
	 * iphone��iphoneX,iphone8,iphone7,���� 
	 * ��Ϊ�� Mate / Pϵ /��ҫ�� 
	 * С�ף� MIX /����/Note
	 * OPPO:A /R /Find; 
	 * vivo:Xplay /X / Y 
	 * ����:Pro /���� 
	 * һ��:X /T
	 * ����:M/S
	 * 
	 */

	public Good(String goodNo) {
		this.goodNo = goodNo;

	}

	public Good(String goodNo, String goodName, int goodPrice, String goodUserSale, String goodDescription, int goodNum,
			String goodfilename) {
		super();
		this.goodNo = goodNo;
		this.goodName = goodName;
		this.goodPrice = goodPrice;// �Զ�װ��
		this.goodUserSale = goodUserSale;
		this.goodDescription = goodDescription;
		this.goodNum = goodNum;// �Զ�װ��
		this.goodfilename = goodfilename;
	}

	public Good(String goodNo, String goodName, int goodPrice, String goodUserSale, String goodDescription, int goodNum,
			String goodfilename, String type) {
		super();
		this.goodNo = goodNo;
		this.goodName = goodName;
		this.goodPrice = goodPrice;// �Զ�װ��
		this.goodUserSale = goodUserSale;
		this.goodDescription = goodDescription;
		this.goodNum = goodNum;// �Զ�װ��
		this.goodfilename = goodfilename;
		this.type = type;
	}
	public Good(String goodNo, String goodName, int goodPrice, String goodUserSale, String goodDescription, int goodNum,
			String goodfilename, String type,String detail_type) {
		super();
		this.goodNo = goodNo;
		this.goodName = goodName;
		this.goodPrice = goodPrice;// �Զ�װ��
		this.goodUserSale = goodUserSale;
		this.goodDescription = goodDescription;
		this.goodNum = goodNum;// �Զ�װ��
		this.goodfilename = goodfilename;
		this.type = type;
		this.detil_type = detail_type;
	}
	

	public String getDetil_type() {
		return detil_type;
	}

	public void setDetil_type(String detil_type) {
		this.detil_type = detil_type;
	}

	public String getPicture_name() {
		return this.goodNo + "+" + this.goodfilename;
	}

	public void setPicture_name(String picture_name) {
		this.picture_name = picture_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setGoodPrice(Integer goodPrice) {
		this.goodPrice = goodPrice;
	}

	public void setGoodNum(Integer goodNum) {
		this.goodNum = goodNum;
	}

	public String getGoodNo() {
		return goodNo;
	}

	public String getGoodfilename() {
		return goodfilename;
	}

	public void setGoodfilename(String goodfilename) {
		this.goodfilename = goodfilename;
	}

	public void setGoodNo(String goodNo) {
		this.goodNo = goodNo;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public int getGoodPrice() {
		return goodPrice.intValue();
	}

	public void setGoodPrice(int goodPrice) {
		this.goodPrice = goodPrice;
	}

	public String getGoodUserSale() {
		return goodUserSale;
	}

	public void setGoodUserSale(String goodUserSale) {
		this.goodUserSale = goodUserSale;
	}

	public String getGoodDescription() {
		return goodDescription;
	}

	public void setGoodDescription(String goodDescription) {
		this.goodDescription = goodDescription;
	}

	public int getGoodNum() {
		return goodNum.intValue();
	}

	public void setGoodNum(int goodNum) {
		this.goodNum = goodNum;
	}

}
