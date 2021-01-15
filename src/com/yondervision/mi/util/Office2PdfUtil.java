package com.yondervision.mi.util;

import java.io.File;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.yondervision.mi.common.log.LoggerUtil;
import org.apache.log4j.Logger;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.util.PropertiesReader;

public class Office2PdfUtil {
	private static Office2PdfUtil office2PdfUtil = new Office2PdfUtil();
    private static OfficeManager officeManager;
    //openOffice安装路径
//	private static String OPEN_OFFICE_HOME = "C:\\Program Files (x86)\\OpenOffice 4\\";
    //服务端口
//    private static int OPEN_OFFICE_PORT[] = {8100};
    
    public static Office2PdfUtil getOffice2PdfUtil() {
		return office2PdfUtil;
	}
    
    /**
     * 
     * office2Pdf 方法
     * @descript：TODO
     * @param inputFile 文件全路径
	 * @param pdfFilePath pdf文件全路径
     * @return void
     * @author lxz
     * @return 
     */
    public void office2Pdf(String inputFile, String pdfFilePath) {
        
        File pdfFile = new File(pdfFilePath);
        try{
            if (pdfFile.exists()) {
                boolean del = pdfFile.delete();
                if(!del){
                    throw new RuntimeException("file delete is error,deltet file name:["+pdfFilePath+"]");
                }
            }
	        long startTime = System.currentTimeMillis();
			//打开服务
	        startService();        
	        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
			//开始转换
	        converter.convert(new File(inputFile),new File(pdfFilePath));
	        //关闭
	        stopService();
	        System.out.println("运行结束");
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
    }
    
    public static void stopService(){
        if (officeManager != null) {
            officeManager.stop();
        }
    }
    
    public static void startService(){
        DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
        try {
        	String OPEN_OFFICE_HOME = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "openOfficeHome");
        	String OPEN_OFFICE_PORT = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "openOfficePort");		
            configuration.setOfficeHome(OPEN_OFFICE_HOME);//设置安装目录
            configuration.setPortNumbers(Integer.parseInt(OPEN_OFFICE_PORT)); //设置端口
            configuration.setTaskExecutionTimeout(1000 * 60 * 5L);
            configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);
            officeManager = configuration.buildOfficeManager();
            officeManager.start();    //启动服务
        } catch (Exception ce) {
        	ce.printStackTrace();
            System.out.println("office转换服务启动失败!详细信息:" + ce);
        }
    }
    
    public static void wordToPdf(String word ,String pdf){
    	Office2PdfUtil.getOffice2PdfUtil().office2Pdf(word, pdf);
    }


    public static void wordToPdfRPC(String word ,String pdf)
    {
        try {
            String OPEN_OFFICE_IP_PRC = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "openOfficeIPRPC");
            String OPEN_OFFICE_PORT_RPC = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "openOfficePortRPC");
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(OPEN_OFFICE_IP_PRC,Integer.parseInt(OPEN_OFFICE_PORT_RPC));
            DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
            connection.connect();
            File file = new File(word);
            File outputFile = new File(pdf);
            converter.convert(file, outputFile);
            connection.disconnect();
        } catch (ConnectException e) {
            //Logger log = LoggerUtil.getLogger();
            Logger log = LoggerUtil.getLogger();
            log.info("wordToPdfRPCerror:"+e.toString());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
    	//Office2PdfUtil.getOffice2PdfUtil().office2Pdf("C:/download/export_report/SCBG_P.docx", "C:/download/export_report/SCBG_P.pdf");
        try {
//            OpenOfficeConnection connection = new SocketOpenOfficeConnection("10.22.20.90",8100);
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("ys.12329app.cn",9089);
            DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
            connection.connect();
            File file = new File("E:/1.doc");
            File outputFile = new File("E:/1.pdf");
            converter.convert(file, outputFile);
            connection.disconnect();
        } catch (ConnectException e) {
            e.printStackTrace();
        }

    }
}
