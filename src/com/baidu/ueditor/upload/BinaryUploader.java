package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.yondervision.mi.common.Constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class BinaryUploader {

	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		
		
		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}
		DiskFileItemFactory linshipath = new DiskFileItemFactory();
		//linshipath.setRepository(new File("d:/a"));//临时文件夹存放图片 
		ServletFileUpload upload = new ServletFileUpload(linshipath);

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String savePath = (String) conf.get("savePath");
			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}
			System.out.println((String)request.getAttribute("ueditorYcmapType"));
			savePath = PathFormat.parse(savePath, originFileName);
			String ycmapImgPushPath = (String) conf.get("ycmapImgPushPath");
			String ycmapImgNewsPath = (String) conf.get("ycmapImgNewsPath");
			String centerId = (String)request.getAttribute("centerId");
			String fileName = savePath.substring(savePath.lastIndexOf("/")+1);
			String ueditorYcmapType = (String)request.getAttribute("ueditorYcmapType");
//			String physicalPath = (String) conf.get("rootPath") + savePath;//存储路径
			String ycmapImgPath = "";
			if("duanxinImg".equals(ueditorYcmapType)){
				ycmapImgPath = ycmapImgPushPath;
			}else{
				ycmapImgPath = ycmapImgNewsPath;
			}
			String physicalPath = ycmapImgPath+centerId+"/image/"+formatter.format(date)+"/"+fileName;
//			physicalPath = "D:/edoc/app/info_upload_img/00041100/image/20150701/1437727087454001347.jpg";
			InputStream is = fileStream.openStream();
			State storageState = StorageManager.saveFileByInputStream(is,
					physicalPath, maxSize);
			is.close();

			if (storageState.isSuccess()) {
				String ycmapImgPathDown = "duanxinImg".equals(ueditorYcmapType)?(String) conf.get("ycmapImgPathPushDown"):(String) conf.get("ycmapImgPathNewsDown");
				storageState.putInfo("url", ycmapImgPathDown+"&fileName="+centerId+"/image/"+formatter.format(date)+"/"+fileName+"&isFullUrl=true");//PathFormat.format(savePath)
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
