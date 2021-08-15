package com.cmbcc.pics2pdf;

import jodd.util.StringUtil;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.activation.MimetypesFileTypeMap;
import java.awt.print.Book;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        testUploadImage();

    }

    /**
     * 测试上传png图片
     */
    public static void testUploadImage() {
        String url = "http://localhost:8080/reader/book/insertAPIBook";
        String lunboimg = "C:/Users/Administrator/Desktop/123.jpg";
        String reader = "C:/Users/Administrator/Desktop/reader.txt";
        Map<String, String> textMap = new HashMap<String, String>();
        //可以设置多个input的name，value
        textMap.put("author", "谢霆锋");
        textMap.put("bookName", "坏蛋是怎么练成的");
        //设置file的name，路径
        Map<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("lunboimg", lunboimg);
        fileMap.put("file", reader);
        String contentType = "";//image/png
        String ret = formUpload(url, textMap, fileMap, contentType);
        System.out.println(ret);
        //{"status":"0","message":"add succeed","baking_url":"group1\/M00\/00\/A8\/CgACJ1Zo-LuAN207AAQA3nlGY5k151.png"}
    }

    /**
     * 上传图片
     *
     * @param urlStr
     * @param textMap
     * @param fileMap
     * @param contentType 没有传入文件类型默认采用application/octet-stream
     *                    contentType非空采用filename匹配默认的图片类型
     * @return 返回response数据
     */
    @SuppressWarnings("rawtypes")
    public static String formUpload(String urlStr, Map<String, String> textMap,
                                    Map<String, String> fileMap, String contentType) {
        String res = "";
        HttpURLConnection conn = null;
        // boundary就是request头和上传文件内容的分隔符
        String BOUNDARY = "---------------------------123821742118716";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition:form-data;name=\""
                            + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }
            // file
            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();

                    //没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
                    contentType = new MimetypesFileTypeMap().getContentType(file);
                    //contentType非空采用filename匹配默认的图片类型
                    if (!"".equals(contentType)) {
                        if (filename.endsWith(".png")) {
                            contentType = "image/png";
                        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".jpe")) {
                            contentType = "image/jpeg";
                        } else if (filename.endsWith(".gif")) {
                            contentType = "image/gif";
                        } else if (filename.endsWith(".ico")) {
                            contentType = "image/image/x-icon";
                        }
                    }
                    if (contentType == null || "".equals(contentType)) {
                        contentType = "application/octet-stream";
                    }
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition:form-data;name=\""
                            + inputName + "\";filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(
                            new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }




    /*下面是Java后台接收的接口
            参数是以流的形式，所以后台也要用流的形式接收*/
    @RequestMapping(value = "/insertAPIBook", method = RequestMethod.POST)
    @ResponseBody
    public String insertAPIBook(DefaultMultipartHttpServletRequest request) {
        try {
        // 用DefaultMultipartHttpServletRequest来接收，参数会自动封装到request作用域中
        //普通参数，直接用request.getParameter("bookName")，文件接收如下
            Book book = new Book();
            /*if (StringUtil.isNotEmpty(request.getParameter("bookName"))) {
                book.setBookName(request.getParameter("bookName"));
            }
            if (StringUtil.isNotEmpty(request.getParameter("author"))) {
                book.setAuthor(request.getParameter("author"));
            }*/


            MultiValueMap<String, MultipartFile> mvmFs = request.getMultiFileMap();

            MultipartFile file = null;
            MultipartFile lunboimg = null;
            MultipartFile headimg = null;
            for (String key : mvmFs.keySet()) {
                System.out.println("----属性名---" + key);
                if ("file".equals(key)) {
                    file = request.getFile(key);
                }
                if ("lunboimg".equals(key)) {
                    lunboimg = request.getFile(key);
                }
                if ("headimg".equals(key)) {
                    headimg = request.getFile(key);
                }

            }
            //下面就是调用和form表单提交一样的接口，没啥区别
            /*bookServiceImpl.insertBook(everychapterfee, paychapter, book, file, lunboimg, headimg, request, tag);*/
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
