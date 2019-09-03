package com.zhouww.first.springboot.CommonUtils;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


/**
 * 用于模拟浏览器请求的工具
 *
 * @author zhouweiwei
 * @since 1.0.0
 */
public class HttpClientUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 用于通过 url 直接获取输入流（一般用于处理 文件信息）
     *
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
        if (httpclient != null) {
            httpclient.close();
        }
        return in;
    }

    /**
     * 通过url 直接获取String
     *
     * @param url
     * @return
     */
    public static String doGetStringJson(String url) {
        // 创建一个客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 设置一种的请求的方式
        HttpGet httpGet = new HttpGet(url);
        // 响应模式
        CloseableHttpResponse closeableHttpResponse = null;
        String resultStr = "";
        try {
            // 执行请求
            closeableHttpResponse = httpClient.execute(httpGet);
            // 获取请求实体内容
            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            // 通过工具将 输入流直接转成String
            resultStr = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            logger.error("httpClient请求数据异常......", e);
        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("httpClient关闭异常......", e);
            }
        }
        if (closeableHttpResponse != null) {
            try {
                closeableHttpResponse.close();
            } catch (IOException e) {
                logger.error("httpClient关闭异常......", e);
            }
        }
        return resultStr;
    }

    /**
     * get请求传参数方法
     *
     * @param url   请求地址
     * @param param 参数
     * @return String
     */
    public static String doGet(String url, String param) {
        String urlP = url + "?" + param;
        String resultStr = doGetStringJson(urlP);
        return resultStr;
    }


    /**
     * post请获取
     *
     * @param url
     * @param param
     * @return
     */
    public static String postMap(String url, Map<String, String> param) throws IOException {
        // 创建httpClient 对象
        CloseableHttpClient client = HttpClientBuilder.create().build();
        // 创建 请方法
        HttpPost httpPost = new HttpPost(url);
        // 将请求参数变为json串进行请求
        String reqParam = JSONObject.toJSONString(param);
        //创建封装请求参数的的实体
        StringEntity entity = new StringEntity(reqParam, "UTF-8");
        httpPost.setEntity(entity);
        // 设置请求头信息
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        // 进行请求
        HttpResponse response = client.execute(httpPost);
        // 获取 请求结果的数据实体
        HttpEntity httpEntity = response.getEntity();
        String resultStr = EntityUtils.toString(httpEntity);
        // 关闭请求实体
        client.close();
        return resultStr;
    }

    /**
     * post 的请求通json串
     *
     * @param url   请求地址
     * @param param 请求参数
     * @return
     * @throws IOException
     */
    public static String postStringJson(String url, String param) throws IOException {
        // 创建httpClient实体
        CloseableHttpClient client = HttpClientBuilder.create().build();
        // 创建请求方式
        HttpPost httpPost = new HttpPost(url);
        //设置请求参数
        httpPost.setEntity(new StringEntity(param, "UTF-8"));
        // 设置请求头信息
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        // 执行请求
        HttpResponse response = client.execute(httpPost);
        // 获取请求实体
        HttpEntity httpEntity = response.getEntity();
        String resultStr = "";
        if (httpEntity != null) {// 如果响应体信息为空直接返回
            resultStr = EntityUtils.toString(httpEntity);
        }
        client.close();
        return resultStr;
    }

    /**
     * post请求通过map传值获取相关的结果
     *
     * @param url
     * @param param
     * @return
     * @throws IOException
     */
    public static Map post(String url, Map<String, String> param) throws IOException {
        //将 map数据转成json串
        String reqStr = JSONObject.toJSONString(param);
        // 发起请求 并且获取结果
        String resp = postStringJson(url, reqStr);
        // 将结果转成map
        Map map = JSONObject.parseObject(resp, Map.class);
        return map;
    }

    /**
     * post 无参请求
     *
     * @param url 请求路径
     * @return
     */
    public static String post(String url) throws IOException {
        // 创建请求客户端实体
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建请求方式
        HttpPost httpPost = new HttpPost(url);
        // 创建 请求响应体信息
        HttpResponse response = httpClient.execute(httpPost);
        // 获取信息响应体中的信息
        String resultResp = EntityUtils.toString(response.getEntity());
        httpClient.close();
        return resultResp;
    }

    /**
     * 上传 图片
     *
     * @param url
     * @param file
     * @return
     */
    public static String post(String url, File file) throws IOException {
        //创建 http请求实体
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //创建请求超时时间
        RequestConfig config=RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).setSocketTimeout(5000).build();
        // 创建请求方法
        HttpPost httpPost=new HttpPost(url);
        // 创建文件实体
        FileEntity fileEntity=new FileEntity(file,ContentType.getByMimeType(MediaType.APPLICATION_OCTET_STREAM_VALUE));
        httpPost.setEntity(fileEntity);
        // 执行并且获取响应信息
       HttpResponse response= httpClient.execute(httpPost);

       // httpPost.
        return null;
    }

    public static void main(String[] agrs0) throws IOException {
        System.out.println(post("http://localhost:8080/test1", new File("D:\\POReceiveReport.pdf")));

    }

}
