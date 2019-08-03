package com.util;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.aip.imageclassify.AipImageClassify;

public class Logoknow {
	// ����APPID/AK/SK
	public static final String APP_ID = "11683154";
	public static final String API_KEY = "cfOI87u4VEDtpG3QewYYwXIk";
	public static final String SECRET_KEY = "Qs3lRBca7lIFnhQNGzUBxQZzuxGTk0bs";

	public Logoknow() {
		init();
	}

	public void init() {
		// ��ʼ��һ��AipImageClassifyClient
		AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
		//��ѡ
		client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("custom_lib", "false");
		// ����Ϊ����·��
        String image = "D:\\jee-neon\\workspace\\WebShop\\src\\test\\4.jpg";
        JSONObject res = client.logoSearch(image, options);
        System.out.println(res.toString(2));
        //ʶ������̱���
        
        JSONArray result = (JSONArray) res.get("result");
        JSONObject obj0 =  (JSONObject) result.get(0);            
        String logo = obj0.getString("name");
        System.out.println("����ƷΪ��"+logo);
	}
	public static void main(String[] args) {
		new Logoknow();
	}
}
