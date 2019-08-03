package test;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.imageclassify.AipImageClassify;

public class Sample {
    //����APPID/AK/SK
    public static final String APP_ID = "11683154";
    public static final String API_KEY = "cfOI87u4VEDtpG3QewYYwXIk";
    public static final String SECRET_KEY = "Qs3lRBca7lIFnhQNGzUBxQZzuxGTk0bs";

    public static void Logosample(AipImageClassify client) {
        // �����ѡ�������ýӿ�
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("custom_lib", "false");
        
        
        // ����Ϊ����·��
        String image = "D:\\jee-neon\\workspace\\WebShop\\src\\test\\4.jpg";
        JSONObject res = client.logoSearch(image, options);
        System.out.println(res.toString(2));

//        // ����Ϊ����������
//        byte[] file = readFile("test.jpg");
//        res = client.logoSearch(file, options);
//        System.out.println(res.toString(2));
    }
    public static void main(String[] args) {
        // ��ʼ��һ��AipImageClassifyClient
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

        // ��ѡ�������������Ӳ���
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        Logosample(client);

        // ��ѡ�����ô����������ַ, http��socket��ѡһ�����߾�������
//        client.setHttpProxy("proxy_host", proxy_port);  // ����http����
//        client.setSocketProxy("proxy_host", proxy_port);  // ����socket����

        // ��ѡ������log4j��־�����ʽ���������ã���ʹ��Ĭ������
        // Ҳ����ֱ��ͨ��jvm�����������ô˻�������
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // ���ýӿ�
//        String path = "D:\\jee-neon\\workspace\\WebShop\\src\\test\\iphone8.jpg";
//        JSONObject res = client.objectDetect(path, new HashMap<String, String>());
//        System.out.println(res.toString(2));
//        
    }
}