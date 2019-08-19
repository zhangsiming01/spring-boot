package com.example.springboot.common.utis.pdf;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-08-16 17:52
 **/
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
@Slf4j
public class FileToImgUtil {
    private static OpenOfficeConnection startOpenOffice() {
        //OpenOffice的安装目录，linux环境下需要手动启动openoffice服务
        String OpenOffice_HOME = "C:\\Program Files (x86)\\OpenOffice 4\\program";
        // 启动OpenOffice的服务
        String command = OpenOffice_HOME + "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
        try {
            Process pro = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建连接
        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
        return connection;
    }

    private static void doc2Pdf(String docPath, String pdfPath) throws ConnectException {
        File inputFile = new File(docPath);
        File outputFile = new File(pdfPath);
        OpenOfficeConnection connection = startOpenOffice();
        connection.connect();
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        //converter.convert(inputFile, outputFile);
        DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
        DocumentFormat txt = formatReg.getFormatByFileExtension("odt");
        DocumentFormat pdf = formatReg.getFormatByFileExtension("pdf");
        converter.convert(inputFile, txt, outputFile, pdf);
        connection.disconnect();
    }

    /**
     * 把ppt word excel等文件生成图片文件
     *
     * @param docPath    文件路径
     * @param imgDirPath 图片保存文件夹
     * @param fileName   文件名称点的前部分
     */
    public static void doc2Imags(String docPath, String imgDirPath, String fileName) {
        String pdfPath = String.format("%s%s.pdf", FilenameUtils.getFullPath(docPath), FilenameUtils.getBaseName(docPath));
        try {
//            doc2Pdf(docPath, pdfPath);
            pdf2Imgs(pdfPath, imgDirPath, fileName);
            File pdf = new File(pdfPath);
            /*if(pdf.isFile()){
                pdf.delete();
            }*/
            System.out.println(pdfPath);
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将pdf转换成图片
     *
     * @param pdfPath
     * @param imagePath
     * @return 返回转换后图片的名字
     * @throws Exception
     */
    private static List<String> pdf2Imgs(String pdfPath, String imgDirPath, String fileName) throws Exception {
        Document document = new Document();
        document.setFile(pdfPath);
        float scale = 2f;//放大倍数
        float rotation = 0f;//旋转角度
        List<String> imgNames = new ArrayList<String>();
        int pageNum = document.getNumberOfPages();
        File imgDir = new File(imgDirPath);
        if (!imgDir.exists()) {
            imgDir.mkdirs();
        }
        for (int i = 0; i < pageNum; i++) {
            BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
                    Page.BOUNDARY_CROPBOX, rotation, scale);
            RenderedImage rendImage = image;
            try {
                String filePath = imgDirPath + File.separator + fileName + i + ".jpg";
                File file = new File(filePath);
                ImageIO.write(rendImage, "jpg", file);
                imgNames.add(FilenameUtils.getName(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            image.flush();
        }
        document.dispose();
        return imgNames;
    }
}