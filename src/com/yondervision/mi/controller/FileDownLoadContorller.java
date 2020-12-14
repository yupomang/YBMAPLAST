/**
 * 
 */
package com.yondervision.mi.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.exolab.castor.types.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.dao.Mi040DAO;
import com.yondervision.mi.dao.Mi701DAO;
import com.yondervision.mi.dao.Mi711DAO;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi040Example;
import com.yondervision.mi.dto.Mi701WithBLOBs;
import com.yondervision.mi.dto.Mi711;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.JsonUtil;

/**
 * @author LinXiaolong
 * 
 */
@Controller
public class FileDownLoadContorller {
	@RequestMapping("/downloadimg.{ext}")
	public ResponseEntity<byte[]> downloadimg(
			@RequestParam String filePathParam, @RequestParam String fileName,
			@RequestParam boolean isFullUrl, ModelMap modelMap,HttpServletRequest request) {
		HttpHeaders headers = null;
		File downLoadFile = null;
		byte[] bFile = null;
		try {
			if(StringUtils.isNotBlank(request.getParameter("validflag"))){
				if(request.getParameter("validflag").equals("true")){
					Mi040DAO mi040DAO = (Mi040DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi040DAO");
					Mi040Example m040e=new Mi040Example();
					com.yondervision.mi.dto.Mi040Example.Criteria ca= m040e.createCriteria();
					ca.andChannelEqualTo(request.getParameter("appid")).andCenteridEqualTo(request.getParameter("centerid"));
					List<Mi040> list040 = mi040DAO.selectByExample(m040e);
					Mi701DAO mi701DAO = (Mi701DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi701Dao");
					Mi701WithBLOBs mi701 = mi701DAO.selectByPrimaryKey(Integer.parseInt(request.getParameter("seqno")));
					if(mi701!=null){
						Mi711DAO mi711DAO = (Mi711DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi711Dao");
						Mi711 mi711=new Mi711();
						mi711.setSeqno(Integer.parseInt(request.getParameter("seqno")));
						mi711.setCenterid(request.getParameter("centerid"));
						mi711.setClassfication(request.getParameter("classfication"));
						mi711.setDatecreated(CommonUtil.getSystemDate());
						mi711.setPid(list040.get(0).getPid());
						mi711.setTitle(mi701.getTitle());
						mi711.setValidflag("1");
						mi711DAO.insert(mi711);
					}
				}
			}
			String encoding = "UTF-8";
			if (java.nio.charset.Charset.forName("ISO-8859-1").newEncoder().canEncode(fileName)) {
				fileName = new String(fileName.getBytes("ISO-8859-1"), encoding);
			}

			String filePath = CommonUtil.getFileFullPath(filePathParam,
					fileName, isFullUrl);
			downLoadFile = new File(filePath);
			bFile = FileUtils.readFileToByteArray(downLoadFile);

			headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment;filename=\""
					+ URLEncoder.encode(fileName.replaceAll("\\\\", "_"),
							encoding) + "\"");
			headers.add("Content-Type", "application/octet-stream; charset="
					+ encoding);
			headers.setDate(new Date().toLong());
		} catch (Exception e) {
			// TODO 写日志，取得异常类中的异常信息和异常码进行put(recode和msg)
			e.printStackTrace();
		}

		return new ResponseEntity<byte[]>(bFile, headers, HttpStatus.OK);
	}
	
	@RequestMapping("/downloadmedia.{ext}")
	public void downloadedia(@RequestParam String filePathParam,
			@RequestParam String fileName,
			@RequestParam boolean isFullUrl,
			ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		System.out.println("filePathParam:" + filePathParam);
		System.out.println("fileName:" + fileName);
		System.out.println("isFullUrl:" + isFullUrl);
		HashMap map = new HashMap();
		String encoding = "UTF-8";
		if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			encoding = "UTF-8";
		}else {
			encoding = request.getCharacterEncoding();
		}
		try {
			String filePath = CommonUtil.getFileFullPath(filePathParam, fileName, isFullUrl);
			System.out.println("filePath:" + filePath);
			File downLoadFile = new File(filePath);
			if (downLoadFile.exists()) {
				String dfileName = downLoadFile.getName();
				System.out.println("dfileName:" + dfileName);
				InputStream fis = new BufferedInputStream(new FileInputStream(downLoadFile));
				response.reset();
				response.setContentType("application/x-download");
				response.addHeader("Content-Disposition","attachment;filename="+ new String(dfileName.getBytes(),"iso-8859-1"));
				response.addHeader("Content-Length", "" + downLoadFile.length());
				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				byte[] buffer = new byte[1024 * 1024 * 4];
				int i = -1;   
				while ((i = fis.read(buffer)) != -1) {
					toClient.write(buffer, 0, i);
				}
				fis.close();
				toClient.flush();
				toClient.close();
				try {
					response.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				map.put("recode", "999999");
				map.put("msg", "not find the file");
				String rep = JsonUtil.getDisableHtmlEscaping().toJson(map);
				response.getOutputStream().write(rep.getBytes(encoding));
				return ;
			}
		} catch (Exception ex) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					WEB_ALERT.SYS.getValue(), ERROR.SYS.getValue());
			map.put("recode", tre.getErrcode());
			map.put("msg", "not find the file");
			String rep = JsonUtil.getDisableHtmlEscaping().toJson(map);
			response.getOutputStream().write(rep.getBytes(encoding));
			return ;
		}
	}
}
