package com.yondervision.mi.test;

import com.aspose.words.*;
import com.yondervision.mi.util.ConvertUpMoneyUtil;
import com.yondervision.mi.util.DigitalSealService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description //TODO
 * @Author RenMingMing
 * @Version 1.0 2021/01/12 初始化创建
 */
public class RenmingmingFileUtil {

    public static void main(String[] args) {
        Map<String, String> resultMap = new HashMap<String, String>();
        //resultMap.put("zgxm", "职工姓名");
        //resultMap.put("sfzh", "身份证号");
        //resultMap.put("dwmc", "单位名称");
        //resultMap.put("grgjjzh", "个人公积金账号");
        resultMap.put("loancontrnum", "20201222nbbj02");
        resultMap.put("accname", "张杰");
        resultMap.put("certinum", "330283198802270016");
        resultMap.put("unitaccname", "宁波易才人力资源咨询有限公司");
        resultMap.put("accnum", "0238557595");
        resultMap.put("opnaccdate", "2021年09月");
        resultMap.put("indiaccstate","■    正常       □    封存\r" + "□    欠款       □    其他" );
        String money = "3815.12";
        String my = ConvertUpMoneyUtil.toChinese(money);
        System.out.println(my);
        resultMap.put("basenum", my);
        String unitprop = "8.020";
        String unitp = getNumber(unitprop);
        resultMap.put("unitprop", "单位：  "+unitp+"%");
        resultMap.put("indiprop", "个人：  "+"5%");
        String money1 = "382";
        String my1 = ConvertUpMoneyUtil.toChinese(money1);
        resultMap.put("indipaysum", my1);

        String money2 = "1529.00";
        String my2 = ConvertUpMoneyUtil.toChinese(money2);
        resultMap.put("bal", my2);
        resultMap.put("Accinstcode", "宁波市住房公积金管理中心: \r" +
                "                   我中心缴存职工的住房公积金缴存使用情况如下。");
        resultMap.put("time", "2020" + "年" + "05" + "月" + "     —     " +
                "2020" + "年" + "12" + "月");
        resultMap.put("flag","   ■无贷款记录        □仅有一次贷款记录且贷款已还清\r" +
                "   □有两次以上(含两次)贷款记录且贷款已还清");
        resultMap.put("addr", "浙江省宁波市奉化区");
        String money3 = "1529000.00";
        String my3 = ConvertUpMoneyUtil.toChinese(money3);
        resultMap.put("amt", my3);
        resultMap.put("linkman", "   我中心保证以上信息真实准确。 " +
                "                 本证明自开具之日起，2个月内有效。\r " + " \r" +
                "单位经办人： " + "陈倩颖" + "联系电话：" + "0574-89180737" +
                "   单位公章（或业务专用章）：\r" +
                "       2020年09月11日");

        doc2pdf("D:\\Desktop\\temp.doc", "D:\\Desktop\\" + UUID.randomUUID().toString() + ".pdf", resultMap);

        //盖章有ip限制
       /* File pdfFile = new File( "D:\\Desktop\\" + UUID.randomUUID().toString() + ".pdf");
        String pdftempUrl = "D:\\Desktop\\" + pdfFile.getName();//盖章前pdf存放位置
        String pdfUrl = "D:\\Desktop\\market" + pdfFile.getName();//盖章后pdf存放位置
        String errmassage = DigitalSealService.digitalSealByKey("单位公章（或业务专用章）：",pdftempUrl,pdfUrl);*/
    }


    private static boolean getLicense() {
        boolean result = false;
        License aposeLic = new License();
        StringBuffer strlic = new StringBuffer();
        strlic.append("<License>");
        strlic.append("  <Data>");
        strlic.append("    <Products>");
        strlic.append("      <Product>Aspose.Total for Java</Product>");
        strlic.append("      <Product>Aspose.Words for Java</Product>");
        strlic.append("    </Products>");
        strlic.append("    <EditionType>Enterprise</EditionType>");
        strlic.append("    <SubscriptionExpiry>20991231</SubscriptionExpiry>");
        strlic.append("    <LicenseExpiry>20991231</LicenseExpiry>");
        strlic.append("    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>");
        strlic.append("  </Data>");
        strlic.append("  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>");
        strlic.append("</License>");

        try {
            aposeLic.setLicense(new ByteArrayInputStream(strlic.toString().getBytes()));
            result = true;
        } catch (Exception var4) {
            var4.printStackTrace();
        }
        return result;
    }

    public static boolean doc2pdf(String inPath, String outPath, Map<String, String> map) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return false;
        }
        FileOutputStream os = null;
        try {
            long old = System.currentTimeMillis();
            // 新建一个空白pdf文档
            File file = new File(outPath);
            os = new FileOutputStream(file);
            // Address是将要被转化的word文档
            Document doc = new Document(inPath);
            if(map.get("loancontrnum") != null && map.get("loancontrnum")!="") {
                doc.getRange().replace(Pattern.compile("loancontrnum"), map.get("loancontrnum"));
            }
            NodeCollection tables = doc.getChildNodes(NodeType.TABLE, true);
            for (Table table : (Iterable<Table>) tables) {
                for (Row row : table.getRows()) {
                    for (Cell cell : row.getCells()) {
                        for (Run run : (Iterable<Run>) cell.getChildNodes(NodeType.RUN, true)) {
                            String text = run.getText();
                            String tempText = map.get(text);
                            if (tempText != null) {
                                text = tempText;
                                run.getFont().setName("Courier New");
                                run.getFont().setSize(10.0);

                            }
                            //run.getDocument().getStyles().
                            run.setText(text);
                            boolean hidden = run.getFont().getHidden();
                            if (hidden) {
                                run.setText("");
                            }
                        }
                    }
                }
            }
            doc.save(os, SaveFormat.PDF);
            // EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            // 转化用时
            System.out.println("pdf转换成功，共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static String getNumber(String str) {
        // 需要取整数和小数的字符串
        // 控制正则表达式的匹配行为的参数(小数)
        Pattern p = Pattern.compile("(\\d)");
        //Matcher类的构造方法也是私有的,不能随意创建,只能通过Pattern.matcher(CharSequence input)方法得到该类的实例.
        Matcher m = p.matcher(str);
        //m.find用来判断该字符串中是否含有与"(\\d+\\.\\d+)"相匹配的子串
        if (m.find()) {
            //如果有相匹配的,则判断是否为null操作
            //group()中的参数：0表示匹配整个正则，1表示匹配第一个括号的正则,2表示匹配第二个正则,在这只有一个括号,即1和0是一样的
            str = m.group(1) == null ? "" : m.group(1);
        } else {
            //如果匹配不到小数，就进行整数匹配
            p = Pattern.compile("(\\d+)");
            m = p.matcher(str);
            if (m.find()) {
                //如果有整数相匹配
                str = m.group(1) == null ? "" : m.group(1);
            } else {
                //如果没有小数和整数相匹配,即字符串中没有整数和小数，就设为空
                str = "";
            }
        }
        return str;
    }
}
