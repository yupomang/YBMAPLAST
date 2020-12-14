package com.yondervision.mi.controller;


import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.form.AppApi00237Form;
import com.yondervision.mi.service.MsgSendApi001Service;
import com.yondervision.mi.util.ReadProperty;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
* @ClassName: AppApi230Contorller
* @Description: 发布在省数据平台的接口(长三角 一网通办 异地缴存证明)
* @author luolin
* @date 04 08, 2020 PM
*/ 
@Controller
public class AppApi230Contorller {
	@Autowired
	private MsgSendApi001Service msgSendApi001Service = null;
	public void setMsgSendApi001Service(MsgSendApi001Service msgSendApi001Service) {
		this.msgSendApi001Service = msgSendApi001Service;
	}

	/**
	 * 长三角异地缴存证明及明细打印
	 * @param form 信息参数
	 * @param 返回数据容器
	 * @return 回调页面名
	 * @throws Exception 
	 */
	@RequestMapping("/appapi00237.{ext}")
	public String appapi00237(AppApi00237Form form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception{
		Logger logger = LoggerUtil.getLogger();
		String file = form.getFile();
		String file1 = form.getFile1();

		logger.info("form.getFile();========="+form.getFile());
		logger.info("form.getFile1();========="+form.getFile1());
		file1 = form.getFile1().replace(".txt","");
		String[] files = { file1,file };
		String savepath = "/ispshare/ftpdir/"+"lianhedaying"+file1.replace("/ispshare/ftpdir/","");
		logger.info("savepath========="+savepath);
		mergePdfFiles(files, savepath);
		File filepdf = new File(savepath);
		String PDFToBase64 = PDFToBase64(filepdf);
		logger.info("PDFToBase64========="+PDFToBase64);
		contentToTxt(savepath+".txt",PDFToBase64);

		JSONObject json = new JSONObject();
		json.put("filepath",savepath+".txt");
		response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
		return "";

	}
	//合并pdf 储存到指定目录文件
	public static boolean mergePdfFiles(String[] files, String newfile) {
		boolean retValue = false;
		Document document = null;
		try {
			document = new Document(new PdfReader(files[0]).getPageSize(1));
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));
			document.open();
			for (int i = 0; i < files.length; i++) {
				PdfReader reader = new PdfReader(files[i]);
				int n = reader.getNumberOfPages();
				for (int j = 1; j <= n; j++) {
					document.newPage();
					PdfImportedPage page = copy.getImportedPage(reader, j);
					copy.addPage(page);
				}
			}
			retValue = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
		System.out.println("retValue==="+retValue);
		return retValue;
	}


	/**
	 * Description: 将base64编码内容转换为Pdf
	 * @param  base64编码内容 文件的存储路径（含文件名）
	 * @Author fuyuwei
	 * Create Date: 2015年7月30日 上午9:40:23
	 */
	public static void base64StringToPdf(String base64Content,String filePath) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			byte[] bytes = decoder.decodeBuffer(base64Content);//base64编码内容转换为字节数组
			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
			bis = new BufferedInputStream(byteInputStream);
			File file = new File(filePath);
			File path = file.getParentFile();
			if(!path.exists()){
				path.mkdirs();
			}
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);

			byte[] buffer = new byte[1024];
			int length = bis.read(buffer);
			while(length != -1){
				bos.write(buffer, 0, length);
				length = bis.read(buffer);
			}
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			bis.close();
			fos.close();
			bos.close();
		}
	}

	/**
	 * Description: 将pdf文件转换为Base64编码
	 * @param  file
	 * @Author fuyuwei
	 * Create Date: 2015年8月3日 下午9:52:30
	 */
	public static String PDFToBase64(File file) {
		BASE64Encoder encoder = new BASE64Encoder();
		FileInputStream fin =null;
		BufferedInputStream bin =null;
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bout =null;
		try {
			fin = new FileInputStream(file);
			bin = new BufferedInputStream(fin);
			baos = new ByteArrayOutputStream();
			bout = new BufferedOutputStream(baos);
			byte[] buffer = new byte[1024];
			int len = bin.read(buffer);
			while(len != -1){
				bout.write(buffer, 0, len);
				len = bin.read(buffer);
			}
			//刷新此输出流并强制写出所有缓冲的输出字节
			bout.flush();
			byte[] bytes = baos.toByteArray();
			return encoder.encodeBuffer(bytes).trim();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fin.close();
				bin.close();
				bout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 创建文件
	 * @param fileName
	 * @return
	 */
	public static boolean createFile(File fileName)throws Exception{
		boolean flag=false;
		try{
			if(!fileName.exists()){
				fileName.createNewFile();
				flag=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("创建txt文件======="+flag);
		return true;
	}

	/**
	 * 读TXT文件内容
	 * @param fileName
	 * @return
	 */
	public static String readTxtFile(File fileName)throws Exception{
		String result=null;
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		try{
			fileReader=new FileReader(fileName);
			bufferedReader=new BufferedReader(fileReader);
			try{
				String read=null;
				while((read=bufferedReader.readLine())!=null){
					result=result+read+"\r\n";
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(bufferedReader!=null){
				bufferedReader.close();
			}
			if(fileReader!=null){
				fileReader.close();
			}
		}
		System.out.println("读取出来的文件内容是："+"\r\n"+result);
		return result;
	}

	/**
	 * 写文件内容
	 * @param fileName
	 * @return
	 */
	public static boolean writeTxtFile(String content,File  fileName)throws Exception{
		RandomAccessFile mm=null;
		boolean flag=false;
		FileOutputStream o=null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("GBK"));
			o.close();
//   mm=new RandomAccessFile(fileName,"rw");
//   mm.writeBytes(content);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if(mm!=null){
				mm.close();
			}
		}
		return flag;
	}


	/**
	 * 创建新文件并写入内容
	 * @param filePath,content
	 * @return
	 */
	public static void contentToTxt(String filePath, String content) {
		Logger logger = LoggerUtil.getLogger();
		String str = new String(); //原有txt内容
		String s1 = new String();//内容更新
		try {
			File f = new File(filePath);
			if (f.exists()) {
				System.out.print("文件存在");
				f =new File(filePath);
			} else {
				System.out.print("文件不存在");
				f.createNewFile();// 不存在则创建
			}
			BufferedReader input = new BufferedReader(new FileReader(f));

			/*while ((str = input.readLine()) != null) {
				s1 += str + "\n";
			}
			System.out.println(s1);*/
			input.close();
			s1 += content;
			logger.info("weu_s1======"+s1);

			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(s1);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
