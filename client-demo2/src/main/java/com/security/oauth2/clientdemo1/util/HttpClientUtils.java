package com.security.oauth2.clientdemo1.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpClientUtils {


    public static CloseableHttpClient acceptsUntrustedCertsHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        HttpClientBuilder b = HttpClientBuilder.create();
 
        // setup a Trust Strategy that allows all certificates.
        //
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                return true;
            }

        }).build();
        b.setSSLContext(sslContext);
 
        // don't check Hostnames, either.
        //      -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
 
        // here's the special part:
        //      -- need to create an SSL Socket Factory, to use our weakened "trust strategy";
        //      -- and create a Registry, to register it.
        //
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();
 
        // now, we create connection-manager using our Registry.
        //      -- allows multi-threaded use
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);
        connMgr.setMaxTotal(200);
        connMgr.setDefaultMaxPerRoute(100);
        b.setConnectionManager( connMgr);
 
        // finally, build the HttpClient;
        //      -- done!
        CloseableHttpClient client = b.build();
 
        return client;
    }


    /**
     * 发送邮件
     * @param emailTitle    邮件标题
     * @param emailAddress  邮件地址
     * @param templateCode  模板code
     * @param templateParam 模板中变量
     * @param files         附件，可多个
     * @throws IOException
     */
    public static String sendEmail(String url,String emailTitle, String emailAddress,
                          String templateCode, JSONObject templateParam,
                          File[] files) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);

            ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
                    .create()
                    .setMode(HttpMultipartMode.RFC6532)
                    .addTextBody("email_title", emailTitle, contentType)
                    .addTextBody("email_address", emailAddress, contentType)
                    .addTextBody("template_code", templateCode, contentType)
                    .addTextBody("template_param", templateParam.toJSONString(), contentType);
            if (files != null && files.length>0){
                for (File file : files){
                    multipartEntityBuilder.addBinaryBody("email_attachment",file);
                }
            }
            httppost.setEntity(multipartEntityBuilder.build());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {

                HttpEntity resEntity = response.getEntity();
                String result = EntityUtils.toString(resEntity);
                return result;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

    }


    /**
     * 发送短信
     * @param phoneNumber        手机号
     * @param templateCode       模板code
     * @param templateParam      模板中变量
     * @return
     * @throws Exception
     */
    public static String sendSms(String url,String phoneNumber,String templateCode,JSONObject templateParam) throws Exception{

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);

            ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
                    .create()
                    .setMode(HttpMultipartMode.RFC6532)
                    .addTextBody("phone_number", phoneNumber, contentType)
                    .addTextBody("template_code", templateCode, contentType)
                    .addTextBody("template_param", templateParam.toJSONString(), contentType);

            httppost.setEntity(multipartEntityBuilder.build());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                return EntityUtils.toString(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
