package com.vector.pics2pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 图片转pdf工具类
 *
 * @author Administrator
 */
@Slf4j
public class Img2PdfUtil {


    /**
     * @param imageFiles 需要转换的图片File类Array,按array的顺序合成图片
     */
    public static File imagesToPdf(File[] imageFiles) throws Exception {

        log.info("进入图片合成PDF工具方法");
        File tempFile = File.createTempFile("tmpFile", ".pdf");
        // 第一步：创建一个document对象。
        Document document = new Document();
        document.setMargins(0, 0, 0, 0);
        // 第二步：
        // 创建一个PdfWriter实例，
        PdfWriter.getInstance(document, new FileOutputStream(tempFile));
        // 第三步：打开文档。
        document.open();
        // 第四步：在文档中增加图片。
        int len = imageFiles.length;

        for (int i = 0; i < len; i++) {
            if (imageFiles[i].getName().toLowerCase().endsWith(".bmp")
                    || imageFiles[i].getName().toLowerCase().endsWith(".jpg")
                    || imageFiles[i].getName().toLowerCase().endsWith(".jpeg")
                    || imageFiles[i].getName().toLowerCase().endsWith(".gif")
                    || imageFiles[i].getName().toLowerCase().endsWith(".png")) {
                String temp = imageFiles[i].getAbsolutePath();
                log.info("图片路径：" + temp);
                Image img = Image.getInstance(temp);
//                img.scaleAbsolute(597, 844);// 直接设定显示尺寸
                int percent = getPercent(img.getHeight(), img.getWidth());
                System.out.println("--" + percent);
                //设置图片居中显示
                img.setAlignment(Image.ALIGN_CENTER);
                img.setAlignment(Image.ALIGN_MIDDLE);
                //按百分比显示图片的比例
                img.scalePercent(percent);//表示是原来图像的比例;
                // 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
                //document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
//                document.setPageSize(new Rectangle(597, 844));
                document.setPageSize(getRectangle(img.getHeight(), img.getWidth()));
                document.newPage();
                document.add(img);
            }
        }
        // 第五步：关闭文档。
        document.close();
        log.info("图片合成PDF完成");
        return tempFile;
    }

    public static Rectangle getRectangle(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 841 / h * 100;
            return new Rectangle(595, 841, Math.round(p2));
        } else {
            p2 = 595 / w * 100;
            float v = (float) (595 / w * 1.0);
            return new Rectangle(595, h * v, Math.round(p2));
        }
    }

    /**
     * 第一种解决方案
     * 在不改变图片形状的同时，判断，如果h>w，则按h压缩，否则在w>h或w=h的情况下，按宽度压缩
     *
     * @param h
     * @param w
     * @return
     */

    public static int getPercent(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 841 / h * 100;
        } else {
            p2 = 595 / w * 100;
        }
        p = Math.round(p2);
        return p;
    }



}

