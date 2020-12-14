/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.service
 * 文件名：     WebApiUploadFile.java
 * 创建日期：2013-10-28
 */
package com.yondervision.mi.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传公共service
 * @author LinXiaolong
 *
 */
public interface WebApiUploadFile {

	/**
	 * 文件上传
	 * @param filePath 文件上传绝对路径（建议使用CommonUtil.getFileFullPath方法取得）
	 * @param file 上传的文件
	 * @param maxSize 上传文件的最大大小（KB）
	 * @return 上传后的文件名
	 * @throws Exception
	 */
	public String uploadFile(String filePath, MultipartFile file, int maxSize) throws Exception;

}
