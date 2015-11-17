package com.yjn.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.entity.UrlEncodedFormEntityHC4;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.client.methods.HttpRequestBaseHC4;
import org.apache.http.entity.HttpEntityWrapperHC4;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpExecute {


    public static int METHOD_POST = 0;

    public static final int METHOD_GET = 1;

    //访问网页的客户端
    private CloseableHttpClient client = null;

    HttpRequestBaseHC4 mMthod;

    //作为传送Param数据的载体
    private UrlEncodedFormEntityHC4 entity = null;
    //发送状态
    private CloseableHttpResponse httpResponse = null;


    NetworkTask mTask = null;


    private int ExecuteGrabWebInfo() {

        if (mTask.url == null || mTask.url.trim().length() == 0) return 100;

        int code = 0;
        try {
            //记载要传递的数据
            getHttpMethod();
            //设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000).build();
            mMthod.setConfig(requestConfig);
            client = HttpClients.custom().useSystemProperties().build();
            //获取发送状态
            httpResponse = client.execute(mMthod);
            //发送自后返回结果
            code = httpResponse.getStatusLine().getStatusCode();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return code;
    }


    private HttpRequestBaseHC4 getHttpMethod() {
        final String url = mTask.url;
        final String charSet = mTask.charSet;
        if (mTask.method == METHOD_POST) {
            mMthod = new HttpPostHC4(url);
            try {
                entity = new UrlEncodedFormEntityHC4(mTask.params, charSet);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //发送请求
            ((HttpPostHC4) mMthod).setEntity(entity);

            //设置好Cookie
            for (int i = 0; mTask.cookies != null && i < mTask.cookies.size(); i++) {
                Cookie co = mTask.cookies.get(i);
                //设置表头信息
                mMthod.setHeader("Cookie", co.getName() + "=" + co.getValues());
            }

        } else {
            mMthod = new HttpGetHC4(url);
        }
        return mMthod;
    }


    private void closeHttp() {
        abortPost();
        closeRespone();
        closeClient();
    }

    private void closeClient() {
        try {
            if (client != null) {
                client.close();
                client = null;
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private void closeRespone() {
        if (httpResponse != null) {
            try {
                httpResponse.close();
                httpResponse = null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void abortPost() {
        if (mMthod != null) {
            mMthod.abort();
            mMthod = null;
        }
    }

    public HttpEntity getBitmapInputStream(NetworkTask task) {
        mTask = task;

        if (ExecuteGrabWebInfo() == 200) {

            try {
                return httpResponse.getEntity();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }



    public void close() {
        closeHttp();
    }


    public static class NetworkTask {
        public int method = METHOD_POST;
        public String url = null;

        public String charSet = "UTF-8";
        public List<NameValuePair> params = new ArrayList<NameValuePair>();
        public List<Cookie> cookies = null;
    }


    public static class Cookie {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValues() {
            return values;
        }

        public void setValues(String values) {
            this.values = values;
        }

        private String values;
    }

}
