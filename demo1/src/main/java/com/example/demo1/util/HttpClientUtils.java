package com.example.demo1.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.cxf.common.util.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

/**
 * Created by liudianbing on 2016/10/20.
 */
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static CloseableHttpClient httpclient;

    /**
     * HTTP连接管理器线程
     */
    private static HttpClientConnectionMonitorThread thread;

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 10;
    /**
     * 获取连接的最大等待时间
     */
    public final static int WAIT_TIMEOUT = 10000;
    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 5;
    /**
     * 连接超时时间,3.5秒
     */
    public final static int CONNECT_TIMEOUT = 3500;

    public final static int SOCKET_TIMEOUT = 10000;
    /**
     * 连接池获取连接超时时间，4秒
     */
    public final static int CONNECT_REQUEST_TIMEOUT = 4000;

    private static void init() {
        try {

            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustAnySignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build(), new TrustAnyHostnameVerifierForHttpClient());

            // 初始化线程池
            RequestConfig params = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT)
                    .setExpectContinueEnabled(false).build();

            PoolingHttpClientConnectionManager pccm = new PoolingHttpClientConnectionManager();
            pccm.setMaxTotal(MAX_TOTAL_CONNECTIONS); // 连接池最大并发连接数
            pccm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS); // 单路由最大并发数

            HttpClientUtils.thread = new HttpClientConnectionMonitorThread(pccm); //管理http连接池
            httpclient = HttpClients.custom().setConnectionManager(pccm).setDefaultRequestConfig(params)
                    .setSSLSocketFactory(sslsf).setRetryHandler(httpRequestRetryHandler).build();
        } catch (Exception ex) {
            logger.error("error", ex);
        }

    }

    // 请求重试处理
    private static HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
        @Override
        public boolean retryRequest(IOException exception,
                                    int executionCount, HttpContext context) {
            if (executionCount >= 2) {// 如果已经重试了2次，就放弃
                return false;
            }
            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                return true;
            }
            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                return false;
            }
            if (exception instanceof InterruptedIOException) {// 超时
                return false;
            }
            if (exception instanceof UnknownHostException) {// 目标服务器不可达
                return false;
            }
            if (exception instanceof SSLException) {// SSL握手异常
                return false;
            }

            HttpClientContext clientContext = HttpClientContext
                    .adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        }
    };

    static {
        init();
    }

    public static HttpClient getHttpClient() {
        if (httpclient == null) {
            init();
        }
        return httpclient;
    }

    public static HttpClientConnectionMonitorThread getThread() {
        return thread;
    }

    public static void setThread(HttpClientConnectionMonitorThread thread) {
        HttpClientUtils.thread = thread;
    }

    public interface Configurable {
        void config(HttpRequestBase httpRequestBase);
    }

    public static ByteArrayOutputStream getDataFromStream(InputStream in) {
        return getDataFromStream(in, false);
    }

    public static ByteArrayOutputStream getDataFromStream(InputStream in, boolean forceGzip) {

        byte buf[] = new byte[1024];
        int l = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzipoutputstream = null;
        try {
            if (forceGzip) {
                gzipoutputstream = new GZIPOutputStream(out);
            }

            while ((l = in.read(buf)) > 0) {
                if (forceGzip) {
                    gzipoutputstream.write(buf, 0, l);
                } else {
                    out.write(buf, 0, l);
                }
            }
            if (forceGzip) {
                gzipoutputstream.finish();
                gzipoutputstream.close();
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return out;

    }

    public static Configurable addDefaultUserAgentConfig = new Configurable() {
        @Override
        public void config(HttpRequestBase httpRequestBase) {
            httpRequestBase.addHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
        }
    };
    public static Configurable addAccepteGzipConfig = new Configurable() {
        @Override
        public void config(HttpRequestBase httpRequestBase) {
            httpRequestBase.addHeader("Accept-Encoding",
                    "gzip, deflate");
        }
    };
    public static Configurable ignoreCookiesConfig = new Configurable() {
        @Override
        public void config(HttpRequestBase httpRequestBase) {
            httpRequestBase.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
        }
    };

    public static class AddReferConfig implements Configurable {
        private String refer;

        public AddReferConfig(String refer) {
            this.refer = refer;
        }

        @Override
        public void config(HttpRequestBase httpRequestBase) {
            if (!StringUtils.isEmpty(refer)) {
                httpRequestBase.addHeader("Referer", refer);
            }
        }
    }

    public static String getWebContent(String httpurl) {
        return getWebContent(httpurl, null);
    }

    public static String getWebContent(String httpurl, String charSet) {
        return getWebContent(httpurl, charSet, true, addDefaultUserAgentConfig);
    }

    public static String getWebContent(String httpurl, String charSet, String refer) {
        return getWebContent(httpurl, charSet, refer, true);
    }

    public static String getWebContent(String httpurl, String charSet, String refer, boolean useGzip) {
        return getWebContent(httpurl, charSet, useGzip, addDefaultUserAgentConfig, new AddReferConfig(refer));
    }

    public static String getWebContent(String httpurl, String charset, boolean useGzip, Configurable... configurables) {
        if (StringUtils.isEmpty(httpurl)) {
            return null;
        }

        try {
            HttpGet httpget = new HttpGet(httpurl);
            if (configurables != null) {
                for (Configurable configurable : configurables) {
                    configurable.config(httpget);
                }
            }
            if (useGzip) {
                addAccepteGzipConfig.config(httpget);
            }
            HttpResponse response = httpclient.execute(httpget);
            if (response.getStatusLine().getStatusCode() != 200) {//add by lizhe 20140623
                httpget.abort();
                return null;
            }
            return getResponseBody(response, charset);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static String getPostData(String url, Map<String, String> paras, boolean useGzip, String charset,
                                     Configurable... configurables) {
        try {

            HttpPost httppost = new HttpPost(url);
            setPostParam(httppost, paras);
            if (configurables != null) {
                for (Configurable configurable : configurables) {
                    configurable.config(httppost);
                }
            }
            if (useGzip) {
                addAccepteGzipConfig.config(httppost);
            }
            HttpResponse response = httpclient.execute(httppost);
            return getResponseBody(response, charset);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 把文件发送出去
     *
     * @param idCard 是身份证图片
     * @param best   是大头照
     */
    public static JSONObject postFacePic(byte[] idCard, byte[] best, String postUrl) {
        HttpPost httpPost = new HttpPost(postUrl);
        //发送消息体中封装身份证图片和大头照
        MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
        mEntityBuilder.addBinaryBody("image_best", best, ContentType.MULTIPART_FORM_DATA, "image_best");
        mEntityBuilder.addBinaryBody("image_idcard", idCard, ContentType.MULTIPART_FORM_DATA, "image_idcard");
        httpPost.setEntity(mEntityBuilder.build());
        JSONObject json = new JSONObject();
        InputStream inputStream = null;
        BufferedReader br = null;
        try {
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            //返回来的消息
            String body = "";
            if (responseEntity != null) {
                inputStream = responseEntity.getContent();
                br = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line = "";
                while ((line = br.readLine()) != null) {
                    body += line;
                }
                json = JSONObject.parseObject(body);
                logger.info("return msg:" + body);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            logger.info("finally");
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }

        }
        return json;
    }

    private static void setPostParam(HttpPost httppost, Map<String, String> paras) {
        if (paras != null && paras.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (String para : paras.keySet()) {
                nvps.add(new BasicNameValuePair(para, paras.get(para)));
            }
            // 封包添加到Post请求
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            } catch (UnsupportedEncodingException e1) {
                logger.error("e.printStackTrace():{}", e1);
            }
        }
    }

    private static String getResponseBody(HttpResponse response, String charset) throws IOException {
        String body = null;
        try {
            int status = response.getStatusLine().getStatusCode();

            if (status != 200) {
                logger.info("HttpClient--------->http status:{}, reason:{}", status, response.getStatusLine().getReasonPhrase());
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                logger.info("HttpClient--------->response entity is null");
                return null;
            }
            if (StringUtils.isEmpty(charset) && entity.getContentType() != null) {
                String realCharset = StringParse.getRightString(entity.getContentType().getValue(), "charset=");
                if (!StringUtils.isEmpty(realCharset)) {
                    charset = realCharset.toUpperCase();
                }
            }
            if (!StringUtils.isEmpty(charset)) {
                return EntityUtils.toString(entity, charset);
            }

            boolean isGzip = false;
            Header header;
            header = response.getLastHeader("Content-Encoding");
            if (header != null && header.getValue().equals("gzip")) {
                isGzip = true;
            }
            InputStream in = entity.getContent();
            ByteArrayOutputStream out = getDataFromStream(in, isGzip);
            String tempBody = null;
            if (StringUtils.isEmpty(charset)) {
                header = entity.getContentType();
                if (header != null) {
                    charset = header.getValue();
                    if (charset == null || charset.length() == 0 || charset.startsWith("ISO")) {
                        tempBody = out.toString();
                        charset = getCharset(tempBody);
                    } else {
                        charset = StringParse.getRightString(charset, "=");
                    }
                }
            }

            if (charset == null || charset.length() == 0) {
                body = tempBody == null ? out.toString() : tempBody;
            } else {
                body = out.toString(charset);
            }
            in.close();
            out.close();

        } catch (Exception ex) {
            logger.error("error", ex);
        } finally {
            try {
                EntityUtils.consume(response.getEntity());
            } catch (Exception e) {
            }
        }
        return body;
    }

    private static Pattern p = Pattern.compile("<meta[^>]*charset\\s*=\\s*['\"]?\\S+['\"]?[^>]*>", Pattern.CASE_INSENSITIVE);

    public static String getCharset(String content) {
        Matcher m = p.matcher(content);
        if (m.find()) {
            String str = m.group(0).toLowerCase();
            str = StringParse.getMidString(str, "charset", ">");
            str = StringParse.getRightString(str, "=").trim();
            if (str.length() > 3) {
                int pos = str.indexOf("\"", 3);
                if (pos > 3) {
                    str = str.substring(0, pos);
                } else {
                    pos = str.indexOf("'");
                    if (pos > 3) {
                        str = str.substring(0, pos);
                    } else {
                        pos = str.indexOf(" ", 3);
                        if (pos > 0) {
                            str = str.substring(0, pos);
                        }
                    }
                }
            }
            str = str.replaceAll("['\"/]", "").trim();
            if (str.toLowerCase().startsWith("gb")) {
                str = "GBK";
            }
            return str;
        } else {
            return "";
        }

    }

    public static JSONObject doPost(String url, JSONObject jsonParam) {
        return doPost(url, jsonParam, CONNECT_TIMEOUT);
    }

    public static JSONObject doPost(String url, JSONObject jsonParam, int timeout) {
        JSONObject jsonResult = null;
        //
        HttpClient httpClient = getHttpClient();
        if (timeout < 1000) {//设置单位为毫秒
            timeout = timeout * 1000;
        }
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).setSocketTimeout(timeout)
                .setExpectContinueEnabled(false).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try {
            //打印请求体
            logger.info("HttpClientUtils.doPost:" + jsonParam.toString());
            //
            StringEntity entity = new StringEntity(jsonParam.toString(), "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            //
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    str = EntityUtils.toString(response.getEntity(), "UTF-8");
                    //打印响应体
                    logger.info("HttpClientUtils.doPost Result:" + str);
                    jsonResult = JSONObject.parseObject(str);
                    EntityUtils.consume(response.getEntity());
                } catch (Exception e) {
                    logger.error("Post请求提交失败：" + url);
                }
            }
        } catch (IOException e) {
            logger.error("Post请求提交失败：" + url, e);
        }
        return jsonResult;
    }

    public static JSONObject doPostWith(String url, JSONObject jsonParam) {
        JSONObject jsonResult = null;
        //
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        try {
            //打印请求体
            logger.info("HttpClientUtils.doPost:" + jsonParam.toString());
            //
            StringEntity entity = new StringEntity(jsonParam.toString(), "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            //
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    str = EntityUtils.toString(response.getEntity(), "UTF-8");
                    //jsonResult = JSONObject.fromObject(str);
                    jsonResult = JSONObject.parseObject(str);

                } catch (Exception e) {
                    logger.error("Post请求提交失败：" + url);
                }
            }
        } catch (IOException e) {
            logger.error("Post请求提交失败：" + url, e);
        }
        return jsonResult;
    }

    public static String post(String serverUrl, String data, String contentType, String charset, Configurable... configurables) {
        try {
            HttpPost httppost = new HttpPost(serverUrl);
            if (configurables != null) {
                for (Configurable configurable : configurables) {
                    configurable.config(httppost);
                }
            }
            if (!StringUtils.isEmpty(contentType)) {
                httppost.addHeader("Content-Type", contentType);
            }
            try {
                httppost.setEntity(new StringEntity(data, charset));
            } catch (UnsupportedCharsetException e1) {
                logger.error("e.printStackTrace():{}", e1);
            }
            HttpResponse response = httpclient.execute(httppost);
            return getResponseBody(response, charset);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;


    }

    public static String postWithoutHttpClient(String serverUrl, String data, String contentType) {
        StringBuilder responseBuilder = null;
        BufferedReader reader = null;
        OutputStreamWriter wr = null;

        try {
            URL url = new URL(serverUrl);
            URLConnection e = (HttpURLConnection) url.openConnection();
            if (!StringUtils.isEmpty(contentType)) {
                e.addRequestProperty("Content-Type", contentType);
            }
            e.setDoOutput(true);
            e.setConnectTimeout(5000);
            wr = new OutputStreamWriter(e.getOutputStream());
            wr.write(data);
            wr.flush();
            if (logger.isDebugEnabled()) {
                reader = new BufferedReader(new InputStreamReader(e.getInputStream()));
                responseBuilder = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line).append("\n");
                }

                logger.debug(responseBuilder.toString());
            }
        } catch (IOException var22) {
            logger.error("", var22);
        } finally {
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException var21) {
                    logger.error("close error", var21);
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException var20) {
                    logger.error("close error", var20);
                }
            }

        }
        if (responseBuilder != null) {
            return responseBuilder.toString();
        } else {
            return null;
        }

    }

    public static String upLoadVoice(String methodUrl, String parameter, byte[] voice) {
        String BOUNDARY = "----------HV2ymHFg03ehbqgZCaKO6jyH";
        InputStream is = null;
        DataInputStream dis = null;
        OutputStream out = null;
        String line;
        try {
            URL e = new URL(methodUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) e.openConnection();
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            StringBuffer contentBody = new StringBuffer("--" + BOUNDARY);
            String endBoundary = "\r\n--" + BOUNDARY + "--\r\n";
            httpURLConnection.connect();
            out = httpURLConnection.getOutputStream();
            contentBody.append("\r\n").append("Content-Disposition: form-data; name=\"").append("123\"").append("\r\n").append("\r\n").append(parameter).append("\r\n").append("--").append(BOUNDARY);
            String boundaryMessage1 = contentBody.toString();
            out.write(boundaryMessage1.getBytes("utf-8"));
            contentBody = new StringBuffer();
            contentBody.append("\r\n").append("Content-Disposition:form-data; name=\"").append("123\"; ").append("filename=\"").append("123\"").append("\r\n").append("Content-Type:application/octet-stream").append("\r\n\r\n");
            String boundaryMessage2 = contentBody.toString();
            out.write(boundaryMessage2.getBytes("utf-8"));
            out.write(voice, 0, voice.length);
            contentBody.append("------------HV2ymHFg03ehbqgZCaKO6jyH");
            out.write("------------HV2ymHFg03ehbqgZCaKO6jyH--\r\n".getBytes("UTF-8"));
            out.write(endBoundary.getBytes("utf-8"));
            out.flush();
            is = httpURLConnection.getInputStream();
            dis = new DataInputStream(is);
            line = dis.readLine();
            return line;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (dis != null) {
                    dis.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignored) {
            }
        }
    }
}
