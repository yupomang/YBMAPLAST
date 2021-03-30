package com.yondervision.mi.test;

import com.yd.util.FileUtil;

import java.io.File;
import java.util.UUID;

public class AppApi00338test {

    public static void main(String[] args) {
       /* String voucherData = "icount=1&certinum=330224197903151441&userid=stgy&channel=10&centerId=00057400&inaccdate=2021-01-19&zy=统发&begym=202101&payamt=5540.00&bal=5540.00," +
                "icount=2&certinum=330224197903151441&userid=stgy&channel=10&centerId=00057400&inaccdate=2021-01-19&zy=统发&begym=202101&payamt=4617.00&bal=4617.00" +
                "icount=3&certinum=330224197903151441&userid=stgy&channel=10&centerId=00057400&inaccdate=2020-12-18&zy=统发&begym=202012&payamt=5540.00&bal=5540.00";*/
        String voucherData = "[{\"table1\":[\"1~2021-02-22~统发~202102~3432.00~261902.20\",\"2~2021-02-22~统发~202102~1144.00~65936.05\",\"3~2021-01-21~统发~202101~3432.00~258470.20\"]}]";
        File pdfFile = null;
        String templateName = "gjjjcmxcxdy.doc";//模板文件名
        String templatePath = "D:\\工作文档\\我做的接口-新接口开发\\公积金缴存明细查询打印\\公积金缴存明细查询打印模板\\";
        File docFile = FileUtil.createWord(templatePath + templateName, new File("D:\\工作文档\\我做的接口-新接口开发\\公积金缴存明细查询打印\\公积金缴存明细查询打印模板\\word\\",  UUID.randomUUID().toString()  + ".doc"), voucherData);
        pdfFile = FileUtil.wordToPdf(docFile, new File("D:\\工作文档\\我做的接口-新接口开发\\公积金缴存明细查询打印\\公积金缴存明细查询打印模板\\word\\",  UUID.randomUUID().toString() + ".pdf"));
    }
}
