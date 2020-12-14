package com.yondervision.mi.test;

import java.util.HashMap;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.yd.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import com.yondervision.mi.common.message.SimpleHttpMessageUtil;

public class MyTest {

	//测试
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V = "172.10.0.1";
	//20201214

	public  static void searchAndReplace(String srcPath, String destPath, Map<String, String> map) {
		try {
			XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
			Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
			while (itPara.hasNext()) {
				XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
				Set<String> set = map.keySet();
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					List<XWPFRun> run = paragraph.getRuns();
					for (int i = 0; i < run.size(); i++) {
						if (run.get(i).getText(run.get(i).getTextPosition()) != null &&
								run.get(i).getText(run.get(i).getTextPosition()).equals(key)) {
							run.get(i).setText(map.get(key), 0);
						}
					}
				}
			}

			/**
			 * 单元格
			 */
			Iterator<XWPFTable> itTable = document.getTablesIterator();
			while (itTable.hasNext()) {
				XWPFTable table = (XWPFTable) itTable.next();
				int count = table.getNumberOfRows();
				for (int i = 0; i < count; i++) {
					XWPFTableRow row = table.getRow(i);
					List<XWPFTableCell> cells = row.getTableCells();
					for (XWPFTableCell cell : cells) {
						for (Entry<String, String> e : map.entrySet()) {
							if (e.getKey().equals(cell.getText())) {
								if (e.getValue().indexOf("\n") != -1) {
									//删除原单元格值
									cell.removeParagraph(0);
									XWPFParagraph xwpfParagraph1 = cell.addParagraph();
									String[] split = e.getValue().split("\n");
									//xwpfParagraph.setAlignment(ParagraphAlignment.LEFT);//需要设置，否则中文换行会很生硬很难看
									for (String s : split) {
										XWPFRun run = xwpfParagraph1.createRun();//对某个段落设置格式
										run.setText(s);
										run.addBreak();//换行
									}
								} else {
									//删除原数据
									cell.removeParagraph(0);
									//填充新数据
									cell.setText(e.getValue());
								}
							}
						}
					}
				}
			}
			FileOutputStream outStream = null;
			outStream = new FileOutputStream(destPath);
			document.write(outStream);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void main(String[] args) throws Exception {
		/*SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String encoding = "UTF-8";
		String url = "http://gjjwt.nbjs.gov.cn:7001/gjj-wsyyt/viewwsyyt";//生产环境
		HashMap hashMap = new HashMap();
		hashMap.put("accnum", "0076458492");
		hashMap.put("certinum","330726198810170330");
		hashMap.put("projectname", "宁波市住房公积金管理中心");
		hashMap.put("centername", "宁波市住房公积金管理中心");
		hashMap.put("userid", "330726198810170330");
		hashMap.put("brccode", "05740008");
		String rep = msm.sendPost(url, hashMap, encoding);
		System.out.println(rep);*/

		Map<String, String> map = new HashMap<String, String>();
		//编号
		map.put("loancontrnum", "20200911nbbj08");
		map.put("accinstcode", "衢州市住房公积金管管理中心: \n\n" +
				"                       我中心缴存职工的住房公积金缴存使用情况如下。");
		//职工姓名
		map.put("《accname》", "张杰");
		//身份证号
		map.put("《certinum》", "330283198802270016");
		//单位名称
		map.put("《unitaccname》", "宁波XXXXX");
		//个人公积金账号
		map.put("《accnum》", "123456789");
		//开户时间
		map.put("《opnaccdate》", "2020年05月");
		//账户状态
		map.put("《indiaccstate》", "remark info");
		//缴存基数
		map.put("《basenum》", "壹仟元整");
		//缴存比例：单位
		map.put("unitprop", "单位: 5%");
		//缴存比例：个人
		map.put("indiprop", "个人：6%");
		//月缴存额
		map.put("《indipaysum》", "壹佰元整");
		//缴存余额
		map.put("bal", "二十元整");
		//最近连续教程时间
		map.put("《time》", "2020年05月 — 2020年08月");
		//公积金贷款记录
		map.put("《flag》", "remark info");
		//公积金缴存城市
		map.put("《addr》", "宁波");
		//贷款金额
		map.put("《amt》", "无");
		map.put("《linkman》", "   我中心保证以上信息真实准确。 " +
				"                 本证明自开具之日起，2个月内有效。\n " +
				"单位经办人： " + "陈倩颖     " + "联系电话：" + "057489180737     " +
				" 单位公章（或业务专用章）：\n" +
				"    2020年09月11日\n");

		int a = 15;
		if(a > 15){
			map.put("《indiaccstate》","□正常  □封存\n" +
					"□欠款 ■其他" );
		} else {
			map.put("《indiaccstate》","■正常  □封存\n" +
					"□欠款 □其他" );
		}

		map.put("《flag》", "■无贷款记录 □仅有一次贷款记录且贷款已还清\n" +
				"□有两次以上(含两次)贷款记录且贷款已还清");

		String srcPath = "C:/Users/yd/Desktop/temp.doc";

		String destPath = "C:/Users/yd/Desktop/test";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String saleContractNo = dateFormat.format(new Date());
		System.out.println("path: " + srcPath);
		searchAndReplace(srcPath, destPath + saleContractNo + ".doc", map);

      /*  File inputFile = new File(destPath + saleContractNo + ".doc");
        POITextExtractor extractor = ExtractorFactory.createExtractor(inputFile);
        FileInputStream fis =  new FileInputStream(destPath + saleContractNo + ".pdf");*/



		File tempFile = new File(destPath + saleContractNo + ".doc");
		File file = new File(destPath + saleContractNo + "_001.doc");
		FileUtils.copyFile(tempFile, file );
		//File pdfFile = FileUtil.wordToPdf(file, new File(destPath + saleContractNo + ".pdf"));

		File pdfFile = new File(destPath + saleContractNo + ".pdf");
		//TODO copy doc文件
		FileUtil.wordToPdf(file,pdfFile);
		System.out.println("doc文件内容: " + FileUtils.readFileToString(tempFile, "UTF-8"));
	}

} 
