package com.yondervision.mi.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.UserContext;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.dao.CMi131DAO;
import com.yondervision.mi.dao.Mi130DAO;
import com.yondervision.mi.dao.Mi131DAO;
import com.yondervision.mi.dto.Mi130;
import com.yondervision.mi.dto.Mi130Example;
import com.yondervision.mi.dto.Mi131;
import com.yondervision.mi.dto.Mi131Example;
import com.yondervision.mi.dto.Mi131WithBLOBs;
import com.yondervision.mi.form.WebApi13003Form;
import com.yondervision.mi.form.Webapi13013Form;
import com.yondervision.mi.result.WebApi13003_queryResult;
import com.yondervision.mi.service.WebApi130Service;
import com.yondervision.mi.util.CommonUtil;

/** 
* @ClassName: WebApi130ServiceImpl 
* @author Zhanglei
* @date 2015-01-15  
* 
*/ 
public class WebApi130ServiceImpl implements WebApi130Service {
	
	private Mi130DAO mi130Dao;
	private Mi131DAO mi131Dao;
	private CMi131DAO cmi131Dao;

	public CMi131DAO getCmi131Dao() {
		return cmi131Dao;
	}


	public void setCmi131Dao(CMi131DAO cmi131Dao) {
		this.cmi131Dao = cmi131Dao;
	}


	public Mi131DAO getMi131Dao() {
		return mi131Dao;
	}


	public void setMi131Dao(Mi131DAO mi131Dao) {
		this.mi131Dao = mi131Dao;
	}


	public Mi130DAO getMi130Dao() {
		return mi130Dao;
	}


	public void setMi130Dao(Mi130DAO mi130Dao) {
		this.mi130Dao = mi130Dao;
	}


	public void AddGroup(String groupname) throws Exception{
		UserContext user = UserContext.getInstance();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
		long id = CommonUtil.genKeyStatic("MI130");
		Mi130 record = new Mi130();
		record.setCenterid(user.getCenterid());
		record.setCreatetime(dateFormatter.format(new Date()));
		record.setGroupid(String.valueOf(id));
		record.setGroupname(groupname);
		record.setOperator(user.getOpername());
		mi130Dao.insert(record);
	}


	public void uploadImage(HttpServletRequest request,HttpServletResponse response,
			MultipartFile file, String centerid, String opername){
		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,pdf");

		response.setContentType("text/html; charset=UTF-8");
		
		File uploadedFile = null;
		try {
			if (!ServletFileUpload.isMultipartContent(request)) {
				writeMsg(response, "请选择文件。");
				return;
			}
			String uploadType = request.getParameter("uploadtype");//uploadtype:上传素材类型
			if(CommonUtil.isEmpty(uploadType)){
				throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),
						"没有获取到上传素材的类型uploadtype，请检查后提交！");
			}
			// 检查文件扩展名
			//图片名称
			String uploadName = file.getOriginalFilename();
			uploadName = new String(uploadName.getBytes("ISO8859-1"),"UTF-8");
			//图片格式
			String fileExt = uploadName.substring(
					uploadName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(extMap.get(uploadType).split(",")).contains(
					fileExt)) {
				throw new NoRollRuntimeErrorException(WEB_ALERT.SELF_ERR.getValue(),
						"上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(uploadType)+"格式。");
			}
			
			// 检查文件大小 
			if("image".equals(uploadType)){
				long maxSize = 32*1024;
				if(maxSize < file.getSize()){
					writeMsg(response, "上传文件大小超过"+maxSize+"。");
					return;
				}
			}else if ("flash".equals(uploadType) || ("media".equals(uploadType))){
				long maxSize = 20 * 1024 *1024;
				if(maxSize < file.getSize()){
					writeMsg(response, "上传文件大小超过"+maxSize+"。");
					return;
				}
			}
			
			String dirname = request.getParameter("dirname");//dirname:分组id
			//根据分组定义图片保存路劲
			String filePath = CommonUtil.getFileFullPath("server_upload_img", centerid + File.separator + dirname, true) + File.separator;
			File dirCityFile = new File(filePath);
			if (!dirCityFile.exists()) {
				dirCityFile.mkdirs();
			}
			
			//定义图片保存名称
			String newFileName = UUID.randomUUID().toString();
			InputStream in = file.getInputStream();
			uploadedFile = new File(filePath, newFileName + "." + fileExt);
			OutputStream out = new FileOutputStream(uploadedFile);
			byte[] bs = new byte[1024 * 1024];
			int count = 0;
			while ((count = in.read(bs)) > 0) {
				out.write(bs, 0, count);
			}
			out.close();
			in.close();
			String imgUrl = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
					"server_upload_img", centerid + File.separator + dirname  + File.separator
					+ newFileName + "." + fileExt, true);
			SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
			Mi131WithBLOBs record = new Mi131WithBLOBs();
			long id = 0;
			id = CommonUtil.genKeyStatic("MI131");
			record.setPicid(String.valueOf(id));
			record.setCenterid(centerid);
			record.setUploadtime(dateFormatter.format(new Date()));
			record.setOperator(opername);
			record.setSysname(newFileName + "." + fileExt);
			record.setRealname(uploadName);
			record.setPicurl(imgUrl);
			record.setGroupid(dirname);
			//record.setFreeuse1(uploadType);//freeuse1存放素材类型
			record.setMaterialtype(uploadType);
			mi131Dao.insert(record);
		} catch (IOException e1) {
			uploadedFile.delete();
			System.out.println("上传文件失败。");
			System.out.println("e1====" + e1);
			return;
		}catch (Exception e) {
			uploadedFile.delete();
			System.out.println("上传文件失败。");
			System.out.println("e====" + e);
			return;
		}
	}

	
	/**
	 * 输出信息
	 * @param response
	 * @param message
	 */
	private void writeMsg(HttpServletResponse response, String message){
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("error", 1);
		msg.put("message", message);
		writeJson(response, msg);
		
	}
	/**
	 *输出json
	 * @param response
	 * @param msg
	 */
	private void writeJson(HttpServletResponse response, Object msg){
		response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            
			writer.println(JSON.toJSONString(msg));
            
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
	}

	private int DeleteGroupById(UserContext user, String id){
		Mi130Example example1301 = new Mi130Example();
		example1301.createCriteria().andCenteridEqualTo(user.getCenterid()).andGroupidEqualTo(id);
		int result = mi130Dao.deleteByExample(example1301);
		return result;
	}
	
	public WebApi13003_queryResult GetImageInfoByGrouId(WebApi13003Form form) {
		WebApi13003_queryResult queryResult = new WebApi13003_queryResult();
		
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			return null;
		}
		Mi131 mi131 = new Mi131();
		mi131.setCenterid(user.getCenterid());
		mi131.setGroupid(form.getGroupid());
		int pages = Integer.valueOf(form.getPage());
		int rows = Integer.valueOf(form.getNum());
		pages = pages==0 ? Integer.valueOf(1) : (pages + 1);
		rows = rows==0 ? Integer.valueOf(12) : rows;
		int skipResults = (pages-1) * rows;
		List<Mi131WithBLOBs> list = cmi131Dao.selectByExample(mi131,skipResults,rows);
		Mi131Example mi131Example = new Mi131Example();
		mi131Example.createCriteria().andCenteridEqualTo(user.getCenterid())
		.andGroupidEqualTo(form.getGroupid());
		int total = cmi131Dao.countByExample(mi131Example);
		int totalPage = total/rows;
		int mod = total%rows;
		if(mod > 0){
			totalPage = totalPage + 1;
		}
		queryResult.setPageNumber(pages);
		queryResult.setPageSize(rows);
		queryResult.setTotal(total);
		queryResult.setTotalPage(totalPage);
		queryResult.setList131(list);
		
		return queryResult;
	}


	public boolean UpdateGroupName(String groupname,String id) {
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			return false;
		}
		Mi130Example example = new Mi130Example();
		example.createCriteria().andGroupidEqualTo(id).andCenteridEqualTo(user.getCenterid());
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
		Mi130 mi130 = new Mi130();
		mi130.setGroupname(groupname);
		mi130.setCreatetime(dateFormatter.format(new Date()));
		int result = mi130Dao.updateByExampleSelective(mi130, example);
		if (0 == result) {
			return false;
		}
		return true;
	}


	@SuppressWarnings("unchecked")
	public boolean DeleteGroup(String id,HttpServletRequest request,HttpServletResponse response) {
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			return false;
		}
		//查询中心要删除分组的所有的图片
		Mi131Example example131 = new Mi131Example();
		example131.createCriteria().andCenteridEqualTo(user.getCenterid()).andGroupidEqualTo(id);
		List<Mi131> picList = mi131Dao.selectByExampleWithoutBLOBs(example131);
		//分组下没有图片直接删除分组
		if(picList == null || picList.size() == 0){
			int result = this.DeleteGroupById(user, id);
			if(result == 0){
				return false;
			}
			try {
				String filePath = CommonUtil.getFileFullPath("server_upload_img", user.getCenterid() + File.separator + id, true);
				File file = new File(filePath);
				file.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		//中心分组下有图片先找到中心未分组id
		Mi130Example example130 = new Mi130Example();
		example130.createCriteria().andCenteridEqualTo(user.getCenterid());
		example130.setOrderByClause("groupid");
		List<Mi130> groupList = mi130Dao.selectByExample(example130);
		String wgroupid = groupList.get(0).getGroupid();
		try {
			String filePath = CommonUtil.getFileFullPath("server_upload_img", user.getCenterid() + File.separator + id, true) + File.separator;
			String newfilepath = CommonUtil.getFileFullPath("server_upload_img", user.getCenterid() + File.separator + wgroupid, true) + File.separator;
			for(int i = 0; i < picList.size(); i++){
				File file = new File(filePath + picList.get(i).getSysname());
				file.renameTo(new File(newfilepath + picList.get(i).getSysname()));
				//file.delete();
				String imgUrl = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
						"server_upload_img", user.getCenterid() + File.separator + wgroupid  + File.separator + picList.get(i).getSysname(),true);
				Mi131WithBLOBs mi131 = new Mi131WithBLOBs();
				mi131.setPicurl(imgUrl);
				mi131.setGroupid(wgroupid);
				Mi131Example example = new Mi131Example();
				example.createCriteria().andPicidEqualTo(picList.get(i).getPicid());
				mi131Dao.updateByExampleSelective(mi131, example);
			}
			File file = new File(filePath);
			file.delete();
			int result = this.DeleteGroupById(user, id);
			if(result == 0){
				return false;
			}
			return true;
		} catch (IOException e){
			e.printStackTrace();
			return false;
		}
	}


	public String DeletePicById(String id) {
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			return "999999";
		}
		String arrid[] = id.split(",");
		String filePath = null;
		for(int i = 0; i < arrid.length; i++){
			Mi131 mi131 = mi131Dao.selectByPrimaryKey(arrid[i]);
			Mi131Example example131 = new Mi131Example();
			example131.createCriteria().andCenteridEqualTo(user.getCenterid()).andPicidEqualTo(arrid[i]);
			mi131Dao.deleteByExample(example131);
			try {
				filePath = CommonUtil.getFileFullPath("server_upload_img", user.getCenterid() + File.separator + mi131.getGroupid(), true) + File.separator + mi131.getSysname();
				File file = new File(filePath);
				file.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "000000";
	}


	public List<Mi130> GetGroupInfo() {
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			return null;
		}
		Mi130Example example130 = new Mi130Example();
		example130.createCriteria().andCenteridEqualTo(user.getCenterid());
		List<Mi130> list = mi130Dao.selectByExample(example130);
		return list;
	}


	public boolean MovePicToOtherGroup(String groupid, String picidarr,HttpServletRequest request,HttpServletResponse response) {
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			return false;
		}
		String arr[] = picidarr.split(",");
		try{
			for(int i = 0; i < arr.length; i++){
				Mi131WithBLOBs mi131 = mi131Dao.selectByPrimaryKey(arr[i]);
				String filePath = CommonUtil.getFileFullPath("server_upload_img", user.getCenterid() + File.separator + mi131.getGroupid(), true) + File.separator;
				String newfilepath = CommonUtil.getFileFullPath("server_upload_img", user.getCenterid() + File.separator + groupid, true) + File.separator;
				File dirCityFile = new File(newfilepath);
				if (!dirCityFile.exists()) {
					dirCityFile.mkdirs();
				}
				File file = new File(filePath + mi131.getSysname());
				file.renameTo(new File(newfilepath + mi131.getSysname()));
				//file.delete();
				String imgUrl = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
						"server_upload_img", user.getCenterid() + File.separator + groupid  + File.separator + mi131.getSysname(),true);
				Mi131WithBLOBs nmi131 = new Mi131WithBLOBs();
				nmi131.setPicurl(imgUrl);
				nmi131.setGroupid(groupid);
				Mi131Example example = new Mi131Example();
				example.createCriteria().andPicidEqualTo(mi131.getPicid());
				mi131Dao.updateByExampleSelective(nmi131, example);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public boolean UpdatePicName(String newname, String allpicid) {
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			return false;
		}
		Mi131Example example131 = new Mi131Example();
		example131.createCriteria().andCenteridEqualTo(user.getCenterid()).andPicidEqualTo(allpicid);
		Mi131WithBLOBs record = new Mi131WithBLOBs();
		record.setRealname(newname);
		mi131Dao.updateByExampleSelective(record, example131);
		return true;
	}


	public List<Mi131WithBLOBs> InitCurrentPage(String groupid) {
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			return null;
		}
		Mi131Example example131 = new Mi131Example();
		example131.createCriteria().andCenteridEqualTo(user.getCenterid()).andGroupidEqualTo(groupid);
		List<Mi131WithBLOBs> list = mi131Dao.selectByExampleWithBLOBs(example131);
		return list;
	}


	public String SavePicToSetUrl(HttpServletRequest request,HttpServletResponse response,String realpicurl, String savepicurl,String sysname) throws Exception{
		UserContext user = UserContext.getInstance();
		if (CommonUtil.isEmpty(user.getCenterid())) {
			return null;
		}
		String filePath = CommonUtil.getFileFullPath("srver_img", savepicurl, true) + File.separator;
		File fileout = new File(filePath);
		if (!fileout.exists()) {
			fileout.mkdirs();
		}
		URL url = new URL(realpicurl);
		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();
		byte[] bs = new byte[1024];
		int len;
		OutputStream os = new FileOutputStream(fileout + File.separator + File.separator + sysname);
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		os.close();
		is.close();
		String imgUrl = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/")+1)+CommonUtil.getDownloadFileUrl(
				"srver_img", savepicurl + File.separator + sysname, true);
		String serverimg = filePath + File.separator + sysname;
		return imgUrl + "*" + serverimg;
	}
	
	/**
	 * 上传图文素材
	 */
	public void uploadImgTxt(Webapi13013Form form) throws Exception{
		UserContext user = UserContext.getInstance();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.APP_DATE_TIME_FORMAT);
		Mi131WithBLOBs record = new Mi131WithBLOBs();
		long id = 0;
		id = CommonUtil.genKeyStatic("MI131");
		record.setPicid(String.valueOf(id));
		record.setCenterid(user.getCenterid());
		record.setUploadtime(dateFormatter.format(new Date()));
		record.setOperator(user.getOpername());
		record.setSysname(form.getRealname());
		record.setRealname(form.getRealname());
		record.setPicurl(form.getPicurl());
		record.setGroupid(form.getGroupid());
		//record.setFreeuse1(uploadType);//freeuse1存放素材类型
		record.setMaterialtype("imgtxt");
		record.setHtmlcontent(form.getHtmlContent());
		record.setTxtcontent(form.getTxtContent());
		mi131Dao.insert(record);
		
	}
}
