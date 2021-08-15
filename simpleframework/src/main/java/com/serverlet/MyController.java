package com.serverlet;

import com.cmbcc.pics2pdf.Img2PdfUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Controller
public class MyController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(value = "/hello", produces = "text/html;charset=utf-8")
    public String sayHello() {
        return "Hello,new Job!";
    }

    @GetMapping("testImgToPdf")
    public void testImgToPdf(HttpServletResponse response) throws Exception {
        List<String> urls = Arrays.asList("http://pic.netbian.com/uploads/allimg/180826/113958-153525479855be.jpg",
                "https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2543938585,4255194900&fm=26&gp=0.jpg");
        List<File> files = new ArrayList<>();
        urls.forEach(new Consumer<String>() {
            @Override
            public void accept(String item) {
                //InputStream inputStream = returnBitMap(item);
                String fileName=item.substring(item.lastIndexOf("/")+1);

                try {
                    //File file = inputStream2File(inputStream, fileName);
                    File file=new File("/Users/liyun/Downloads/",fileName);
                    files.add(file);
                    //dowloaImageGraceFully(item,fileName,"/Users/liyun/Downloads/");
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
        File file = Img2PdfUtil.imagesToPdf(files.toArray(new File[files.size()]));
        download(response, file);
    }

    private File inputStream2File(InputStream input, String s) throws FileNotFoundException {
        String destination = "/Users/liyun/Downloads/";
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(new File(destination,s));
        try {
            while ((index = input.read(bytes)) != -1) {

                downloadFile.write(bytes, 0, index);
            }
            downloadFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        } finally {
            try {
                downloadFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File file=new File(destination);
        return file;


    }


    private void download(HttpServletResponse response, File file) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", "test.pdf"));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;fileName=" + "test.pdf");

        byte[] buffer = new byte[1024];
        FileInputStream fis = null; //文件输入流
        BufferedInputStream bis = null;

        OutputStream os; //输出流
        try {
            os = response.getOutputStream();
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer);
                i = bis.read(buffer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            bis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过图片url返回图片Bitmap
     *
     * @param
     * @return
     */
    public static InputStream returnBitMap(String path) {
        URL url = null;
        InputStream is = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();    //得到网络返回的输入流

        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    public void dowloaImageGraceFully(String imageUrl, String formatName, String storePath) {
        InputStream is = null;
        FileOutputStream fos = null;
        File storeDir = new File(storePath);
        if(!storeDir.exists()){
            storeDir.mkdirs();
        }
        String[] str =imageUrl.substring(imageUrl.lastIndexOf("/")+1).split("\\.");
        String suffix =null;
        if(str.length==1){
            suffix="jpg";
        }else {
            suffix = str[1];
        }
        String filename = formatName+"."+suffix;

        try {
            URL url = new URL(imageUrl);
            URLConnection con = url.openConnection();
            is = con.getInputStream();
            fos = new FileOutputStream(new File(storeDir,filename));
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = is.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //return new File(storePath, filename);
    }


}
