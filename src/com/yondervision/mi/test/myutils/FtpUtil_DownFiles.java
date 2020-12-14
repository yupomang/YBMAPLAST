package com.yondervision.mi.test.myutils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.yondervision.mi.util.CommonUtil;


public class FtpUtil_DownFiles {
	Logger log = Logger.getLogger("YFMAP");
	String url=null;
	int port=0;
	String username=null;
	String password=null;
	String localPath=null;
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
    public FtpUtil_DownFiles(){
    	this(null);
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public FtpUtil_DownFiles(String groupname){		
		if(groupname==null){			
			this.url = ReadProperties.getString("ftp_url");// FTP服务器hostname
			this.port = Integer.parseInt(ReadProperties.getString("ftp_port"));// FTP服务器端口
			this.username = ReadProperties.getString("ftp_user");// FTP登录账号
			this.password = ReadProperties.getString("ftp_password");// FTP登录密码
			this.localPath = ReadProperties.getString("local_path");
		}else{
			this.url = ReadProperties.getString(groupname+"_ftp_url");// FTP服务器hostname
			this.port = Integer.parseInt(ReadProperties.getString(groupname+"_ftp_port"));// FTP服务器端口
			this.username = ReadProperties.getString(groupname+"_ftp_user");// FTP登录账号
			this.password = ReadProperties.getString(groupname+"_ftp_password");// FTP登录密码
			this.localPath = ReadProperties.getString(groupname.replace("00089800", "")+"_local_path");
		}		
		
	}
	
	/**
	 * 功能说明：通过递归实现ftp目录文件与本地文件同步更新
	 * 
	 * @param ftpfilepath
	 *            当前ftp目录
	 * @param localpath
	 *            当前本地目录
	 */
	public boolean ftpDownFiles(FTPClient ftpClient,String filename,String ftpfilepath, String localpath) {

		boolean boo = false;
		try {

			FTPFile[] ff = ftpClient.listFiles(ftpfilepath);
			if (ff != null) {
				if(CommonUtil.isEmpty(filename))
				{
					for (int i = 0; i < ff.length; i++) {
						String localfilepath = localpath + ff[i].getName();
						File localFile = new File(localfilepath);
						// 根据ftp文件生成相应本地文件
//						Date fflastModifiedDate = ff[i].getTimestamp().getTime();
//						// 获取ftp文件最后修改时间
//						Date localLastModifiedDate = new Date(
//								localFile.lastModified());
//						// 获取本地文件的最后修改时间
//						int result = localLastModifiedDate
//								.compareTo(fflastModifiedDate);
						// result=0，两文件最后修改时间相同；result<0，本地文件的最后修改时间早于ftp文件最后修改时间；result>0，则相反
						if (ff[i].isDirectory()) {
							// 如果是目录
							localFile.mkdir();
							// 如果本地文件夹不存在就创建
							String ftpfp = ftpfilepath + ff[i].getName() + "/";
							// 转到ftp文件夹目录下
							String localfp = localfilepath + "/";
							// 转到本地文件夹目录下
							this.ftpDownFiles(ftpClient,ff[i].getName(),ftpfp, localfp);
							// 递归调用
						}
						if (ff[i].isFile()) {
							// 如果是文件
							File lFile = new File(localpath);
							lFile.mkdir();
							// 如果文件所在的文件夹不存在就创建
							if (!lFile.exists()) {
								return true;
							}
							// || result < 0
							if (ff[i].getSize() != localFile.length()) {
								// 如果ftp文件和本地文件大小不一样或者本地文件不存在或者ftp文件有更新，就进行创建、覆盖
								String filepath = ftpfilepath + ff[i].getName();
								// 目标ftp文件下载路径
								FileOutputStream fos = new FileOutputStream(
										localFile);
								try {
									boo = ftpClient.retrieveFile(new String(
											filepath.getBytes("UTF-8"),
											"ISO-8859-1"), fos);
									// 从FTP服务器上取回一个文件
								} catch (Exception e) {
									boo = false;
									e.printStackTrace();
								}
								fos.flush();
								// 将缓冲区中的数据全部写出
								fos.close();
								// 关闭流
								return boo;
							} else {
								// System.out.println("两个文件相同！");
							}
						}
					}
				}else
				{
					// System.out.println(ff[i].getName());
					String localfilepath = localpath + filename;
					System.out.println("localfilepath"+localfilepath);
					File localFile = new File(localfilepath);
					// 根据ftp文件生成相应本地文件
					Date fflastModifiedDate = new Date();
					// 获取ftp文件最后修改时间
					Date localLastModifiedDate = new Date(
							localFile.lastModified());
					// 获取本地文件的最后修改时间
					int result = localLastModifiedDate
							.compareTo(fflastModifiedDate);
					// result=0，两文件最后修改时间相同；result<0，本地文件的最后修改时间早于ftp文件最后修改时间；result>0，则相反
					// 如果是文件
					File lFile = new File(localpath);
					lFile.mkdir();
					// 如果文件所在的文件夹不存在就创建
					if (!lFile.exists()) {
						return true;
					}
					// 如果ftp文件和本地文件大小不一样或者本地文件不存在或者ftp文件有更新，就进行创建、覆盖
					String filepath = ftpfilepath + filename;
					System.out.println("filepath"+filepath);
					// 目标ftp文件下载路径
					FileOutputStream fos = new FileOutputStream(
							localFile);
					try {
						boo = ftpClient.retrieveFile(new String(
								filepath.getBytes("UTF-8"),
								"ISO-8859-1"), fos);
						// 从FTP服务器上取回一个文件
					} catch (Exception e) {
						boo = false;
						e.printStackTrace();
					}
					fos.flush();
					// 将缓冲区中的数据全部写出
					fos.close();
					// 关闭流
					return boo;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boo;
	}
	
	
	/**
	 * 功能说明：连接ftp服务器
	 * 
	 * @param hostip
	 *            服务器地址
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param port
	 *            端口
	 */
	public void connectServer(FTPClient ftpClient,String hostip, String username, String password,
			int port) {

		try {
			ftpClient.connect(hostip, port);
			// 连接服务器
			ftpClient.login(username, password);
			// 登录
			// 检测是否连接成功
			int reply = ftpClient.getReplyCode();
			System.out.println("reply:"+reply);
			// 看返回的值是不是230，如果是，表示登陆成功
			if (!FTPReply.isPositiveCompletion(reply)) {
				// 返回的code>=200&&code<300return
				ftpClient.disconnect();
				// 关闭FTP连接
			}
			ftpClient.setControlEncoding("UTF-8");
			// 设置字符编码
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 设置文件传输格式

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	public static void main(String[] args) throws IOException 
	{
		FtpUtil_DownFiles fd = new FtpUtil_DownFiles();
		FTPClient ftpClient = new FTPClient();
		fd.connectServer(ftpClient,"115.239.132.22", "vc80ftp", "Nbszrx80*!",10021);
		boolean success = fd.ftpDownFiles(ftpClient,"cc_calldetail_201804201412.txt","/20180420/","d:/"); 
		System.out.println(success);

	}
}

