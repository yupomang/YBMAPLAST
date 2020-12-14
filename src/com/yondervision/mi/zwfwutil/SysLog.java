package com.yondervision.mi.zwfwutil;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class SysLog {

	private static final String logRoot = ReadProperties.getString("log_path");

	public static void writeSCBW(String sb) {
		if (sb != null) {
			try {
				String path = logRoot;
				File f = new File(path);
				if (!f.exists()) {
					f.mkdir();
				}
				path = path
						+ "/"
						+ (new java.sql.Date(System.currentTimeMillis()))
								.toString();
				f = new File(path);
				System.out.println("-------------scbw----------------------");
				System.out.println(sb);
				System.out.println("logs = " + f);
				if (!f.exists()) {
					f.mkdirs();
				}
				FileWriter LogFileWrite = new FileWriter(path + "/"
						+ "scbw.log", true);
				LogFileWrite.write("["
						+ (new java.sql.Timestamp(System.currentTimeMillis()))
								.toString() + "]" + sb);
				LogFileWrite.write(System.getProperty("line.separator"));
				LogFileWrite.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void writeXCBW(String sb) {
		if (sb != null) {
			try {
				String path = logRoot;
				File f = new File(path);
				if (!f.exists()) {
					f.mkdir();
				}
				path = path
						+ "/"
						+ (new java.sql.Date(System.currentTimeMillis()))
								.toString();
				f = new File(path);
				if (!f.exists()) {
					f.mkdirs();
				}
				FileWriter LogFileWrite = new FileWriter(path + "/"
						+ "xcbw.log", true);
				LogFileWrite.write("["
						+ (new java.sql.Timestamp(System.currentTimeMillis()))
								.toString() + "]" + sb);
				System.out.println("-------------xcbw----------------------");
				System.out.println(sb);
				LogFileWrite.write(System.getProperty("line.separator"));
				LogFileWrite.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void writeError(Exception e) {
		try {
			String path = logRoot;
			File f = new File(path);
			if (!f.exists()) {
				f.mkdir();
			}
			path = path
					+ "/"
					+ (new java.sql.Date(System.currentTimeMillis()))
							.toString();
			System.out.println("-------------error----------------------");
			System.out.println("logs = " + path);
			f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileWriter LogFileWrite = new FileWriter(path + "/" + "error.log",
					true);
			LogFileWrite.write("["
					+ (new java.sql.Timestamp(System.currentTimeMillis()))
							.toString() + "]");
			PrintWriter pw = new PrintWriter(LogFileWrite);
			e.printStackTrace(pw);
			e.printStackTrace();
			LogFileWrite.close();
			pw.close();
		} catch (Exception ee) {
			ee.printStackTrace();
		}

	}
}
