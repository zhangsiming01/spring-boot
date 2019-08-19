package com.example.springboot.common.utis.pdf;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-08-19 09:04
 **/
@Slf4j
public class PdfDemo {
    private static final Logger logger = LoggerFactory.getLogger(PdfDemo.class);

    /**
     * word保存为PDF格式宏，值为17
     */
    private static final Integer WORD_TO_PDF_OPERAND = 17;

    /***
     * ppt保存为PDF格式宏，值为32
     */
    private static final Integer PPT_TO_PDF_OPERAND = 32;

    private static final Integer EXCEL_TO_PDF_OPERAND = 0;

    /***
     * WORD转换PDF
     * @param docFilePath
     * @param pdfFilePath
     */
    public static void doc2Pdf(String docFilePath, String pdfFilePath) {
        ActiveXComponent activeXComponent = null;
        Dispatch dispatch = null;
        try {
            ComThread.InitSTA();
            activeXComponent = new ActiveXComponent("Word.Application");
            activeXComponent.setProperty("Visible", false);
            Dispatch docs = activeXComponent.getProperty("Documents").toDispatch();
            Object[] objects = new Object[]{
                    docFilePath,
                    new Variant(false),
                    //是否只读
                    new Variant(false),
                    new Variant(false),
                    new Variant("pwd")
            };
            dispatch = Dispatch.invoke(docs, "Open", Dispatch.Method, objects, new int[1]).toDispatch();
            //兼容性检查，为特定值false不正确
            Dispatch.put(dispatch, "RemovePersonalInformation", false);
            //word保存为PDF格式宏，值为17
            Dispatch.call(dispatch, "ExportAsFixedFormat", pdfFilePath, WORD_TO_PDF_OPERAND);
        } catch (Exception e) {
            logger.error("word转换PDF异常-{}", e);
        } finally {
            if (dispatch != null) {
                Dispatch.call(dispatch, "Close", false);
            }
            if (activeXComponent != null) {
                activeXComponent.invoke("Quit", 0);
            }
            ComThread.Release();
        }
    }

    /***
     *  ppt转换PDF
     * @param pptFilePath
     * @param pdfFilePath
     */
    public static void ppt2Pdf(String pptFilePath, String pdfFilePath) {
        ActiveXComponent activeXComponent = null;
        Dispatch dispatch = null;
        try {
            ComThread.InitSTA();
            activeXComponent = new ActiveXComponent("PowerPoint.Application");
            Dispatch ppt = activeXComponent.getProperty("Presentations").toDispatch();
            /***
             * call
             * param 4 :ReadOnly
             * param 5: Untitled指定文件是否有标题
             * param 6: WithWindow指定文件是否可见
             */
            dispatch = Dispatch.call(ppt, "Open", pptFilePath, true, true, false).toDispatch();
            //PPTSaveAsPDF 为特定值32
            Dispatch.call(dispatch, "SaveAs", pdfFilePath, PPT_TO_PDF_OPERAND);
        } catch (Exception e) {
            logger.error("ppt转换PDF异常->{}", e);
        } finally {
            if (dispatch != null) {
                Dispatch.call(dispatch, "Close");
            }
            if (activeXComponent != null) {
                activeXComponent.invoke("Quit");
            }
            ComThread.Release();
        }
    }

    /***
     * Excel转PDF
     * @param excelFilePath
     * @param pdfFilePath
     */
    public static void excel2Pdf(String excelFilePath, String pdfFilePath) {
        ActiveXComponent activeXComponent = null;
        Dispatch dispatch = null;
        try {
            ComThread.InitSTA();
            activeXComponent = new ActiveXComponent("Excel.Application");
            activeXComponent.setProperty("Visible", new Variant(false));
            //禁用宏
            activeXComponent.setProperty("AutomationSecurity", new Variant(3));
            Dispatch excel = activeXComponent.getProperty("WorkBooks").toDispatch();

            Object[] objects = new Object[]{
                    excelFilePath,
                    new Variant(false),
                    new Variant(false)
            };
            dispatch = Dispatch.invoke(excel, "Open", Dispatch.Method, objects, new int[9]).toDispatch();

            //转换格式
            Object[] objects1 = new Object[]{
                    //Excel转PDF 宏为0
                    new Variant(EXCEL_TO_PDF_OPERAND),
                    pdfFilePath,
                    //0=标准（生成的PDF图标不会变模糊）；1= 最小文件
                    new Variant(0)
            };
            Dispatch.invoke(dispatch, "ExportAsFixedFormat", Dispatch.Method, objects1, new int[1]);
        } catch (Exception e) {
            logger.error("Excel转换PDF异常->{}", e);
        } finally {
            if (dispatch != null) {
                Dispatch.call(dispatch, "Close", new Variant(false));
            }
            if (activeXComponent != null) {
                activeXComponent.invoke("Quit", new Variant[]{});
                activeXComponent = null;
            }
            ComThread.Release();
        }
    }
}
