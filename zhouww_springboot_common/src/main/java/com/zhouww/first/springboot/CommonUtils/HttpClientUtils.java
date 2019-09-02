package com.zhouww.first.springboot.CommonUtils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于模拟浏览器请求的工具
 * @author zhouweiwei
 * @since 1.0.0
 */
public class HttpClientUtils {
    private static Logger logger=LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 用于通过 url 直接获取输入流（一般用于处理 文件信息）
     * @param url 请求地址
     * @return
     * @throws IOException
     */
    public static InputStream doGetInputStream(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        if(httpclient!=null){
            httpclient.close();
        }
        return  in;
    }

    /**
     * 通过url 直接获取String
     * @param url
     * @return
     */
    public static String doGetStringJson(String url){
        // 创建一个客户端
        CloseableHttpClient httpClient=HttpClientBuilder.create().build();
        // 设置一种的请求的方式
        HttpGet httpGet=new HttpGet(url);
        // 响应模式
        CloseableHttpResponse closeableHttpResponse=null;
        String resultStr="";
        try {
            // 执行请求
            closeableHttpResponse=httpClient.execute(httpGet);
            // 获取请求实体内容
            HttpEntity httpEntity=closeableHttpResponse.getEntity();
            // 通过工具将 输入流直接转成String
            resultStr= EntityUtils.toString(httpEntity);
        }catch (Exception e){
            logger.error("httpClient请求数据异常......",e);
        }
        if(httpClient!=null){
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("httpClient关闭异常......",e);
            }
        }
        if(closeableHttpResponse!=null){
            try {
                closeableHttpResponse.close();
            } catch (IOException e) {
                logger.error("httpClient关闭异常......",e);
            }
        }
        return resultStr;
    }

    /**
     *  get请求传参数方法
     * @param url 请求地址
     * @param param 参数
     * @return String
     */
    public static String doGet(String url,String param){
       String urlP=url+"?"+param;
       String resultStr= doGetStringJson(urlP);
        return resultStr;
    }
    public static  void main(String[] agrs0){
        System.out.println(doGet("http://localhost:8080/test","qq=55"));

    }

}
