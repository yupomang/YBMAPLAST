package com.yondervision.mi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
       
    /**
     * @param htmlStr
     * @return
     *  删除Html标签
     */
    public static String delHTMLTag(String htmlStr) {
    	htmlStr = stripHtml(htmlStr);
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签
   
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签
   
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签
   
        //Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
       // Matcher m_space = p_space.matcher(htmlStr);
       // htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        return htmlStr.trim(); // 返回文本字符串
    }
    
    public static String stripHtml(String content) { 
    	// <p>段落替换为换行 
    	content = content.replaceAll("<p.*?>", "\r\n"); 
    	// <br><br/>替换为换行 
    	content = content.replaceAll("<br\\s*/?>", "\r\n"); 
    	// 去掉其它的<>之间的东西 
    	content = content.replaceAll("\\<.*?>", ""); 
    	// 还原HTML  // content = HTMLDecoder.decode(content); 
    	return content; 
    }
    public static String getTextFromHtml(String htmlStr){
        htmlStr = delHTMLTag(htmlStr);
       // htmlStr = htmlStr.replaceAll("&nbsp;", "");
       // htmlStr = htmlStr.substring(0, htmlStr.indexOf("。")+1);
        return htmlStr;
    }
       
    public static void main(String[] args) {
    	//String str = "<p align=\"left\" class=\"MsoNormal\"><1>截至11月底，(1)全市完成住房公积金归集10.65亿元，比既定的年度目标任务10.50亿元超出0.15亿元，比上年同期多归集住房公积金1.79亿元，同比增加20.20%,住房公积金归集余额达到42.09亿元，超过年度目标任务2.09亿元；全市完成发放住房公积金个人住房贷款12.32亿元，比既定的年度目标任务7亿元超出5.32亿元，比上年同期多发放贷款7.36亿元，同比增加148.39%,住房公积金个人住房贷款余额达31.40亿元，超过年度目标任务3.40亿元。</p><p>我市住房公积金归集和贷款发放工作能提前完成中心在“实现新跨越 争当排头兵”大讨论中提出的“比学赶超”目标任务，主要采取的工作措施：一是年初以来，在巩固党的群众路线教育实践活动取得成果的基础上，扎实开展了“三严三实”和“忠诚干净担当”专题教育，开展了“实现新跨越，争当排头兵”大讨论活动，全体党员干部通过专题教育和大讨论活动，宗旨意识进一步增强，纪律约束进一步严明，工作作风进一步转变，担责干事的热情进一步激发，为中心完成和超额完成各项工作任务提供了坚强有力的保证。二是进一步规范和强化了住房公积金缴存工作。针对部分县区财政按月匹配住房公积金出现短暂困难的实际，中心通过加强与市县区财政部门和政府分管领导的沟通联系，保证了财政供养职工住房公积金财政匹配资金及时足额到位；针对部分缴存企业生产经营困难的实际，中心通过加强对企业的宣传动员、走访调研和催建催缴，实现了制度覆盖面继续扩大和企业缴存进一步规范。三是中心认真贯彻落实国家各部委和省市政府扩大住房公积金使用要求，在我市去年两次出台扩大住房公积金使用政策的基础上，今年5月继续推出了扩大住房公积金使用的多项新政，使我市下半年住房公积金贷款的月均发放额突破1.68亿，加大帮助职工解决改善住房问题的支持力度，对房地产市场平稳健康发展形成有力支撑，为稳增长做出力所能及的贡献。<span></span></p><p align=\"left\" class=\"MsoNormal\"><br/></p>";
		/*String subStr = str.replaceAll("\\&[a-zA-Z]{0,9};", "").replaceAll("<[^>]*>", "");
		System.out.println(subStr);*/
		
        String str = "<div style='text-align:center;'> 整治“四风”   清弊除垢<br/><span style='font-size:14px;'> </span><span style='font-size:18px;'>公司召开党的群众路线教育实践活动动员大会</span><br/></div>";
        String replaceStr = getTextFromHtml(str);
        System.out.println(replaceStr);
    }

}
