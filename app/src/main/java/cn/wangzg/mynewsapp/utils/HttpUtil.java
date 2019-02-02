package cn.wangzg.mynewsapp.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Time: 2019/2/2
 * Author: wangzhiguo
 * Description: 使用HttpUrlConnection进行网络访问
 */
public class HttpUtil {

    /**
     　　　* Post服务请求
     *
     * @param requestUrl 请求地址
     * @param requestbody 请求参数
     * @return
     */
    public static String sendPost(String requestUrl, String requestbody){

        try {
            //建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //设置连接属性
            //使用URL连接进行输出
            connection.setDoOutput(true);
            //使用URL连接进行输入
            connection.setDoInput(true);
            //忽略缓存
            connection.setUseCaches(false);
            //设置URL请求方法
            connection.setRequestMethod("POST");
            String requestString = requestbody;

            //设置请求属性
            //获取数据字节数据
            byte[] requestStringBytes = requestString.getBytes();
            connection.setRequestProperty("Content-length", "" + requestStringBytes.length);
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            // 维持长连接
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");

            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            //建立输出流,并写入数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestStringBytes);
            outputStream.close();

            //获取响应状态
            int responseCode = connection.getResponseCode();

            //连接成功
            if (HttpURLConnection.HTTP_OK == responseCode) {
                //当正确响应时处理数据
                StringBuffer buffer = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                //处理响应流
                responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    buffer.append(readLine).append("\n");
                }
                responseReader.close();
                Log.d("HttpPOST", buffer.toString());
                //成功
                return buffer.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //失败
        return null;

    }

    /**
     * Get服务请求
     *
     * @param requestUrl
     * @return
     */
    public static String sendGet(String requestUrl){
        try{
            //建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setDoInput(true);

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            connection.connect();

            //获取响应状态
            int responseCode = connection.getResponseCode();

            //连接成功
            if (HttpURLConnection.HTTP_OK == responseCode) {
                //当正确响应时处理数据
                StringBuffer buffer = new StringBuffer();
                String readLine;
                BufferedReader responseReader;
                //处理响应流
                responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    buffer.append(readLine).append("\n");
                }
                responseReader.close();
                Log.d("HttpGET", buffer.toString());
                return buffer.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
