package com.ywj.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MD5Util {
	public static String EncoderPwdByMd5(String str)
			throws UnsupportedEncodingException {
		// 获取MD5算法对象
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 编码JDK1.6以上
		BASE64Encoder baEncoder = new BASE64Encoder();
		return baEncoder.encode(md5.digest(str.getBytes("utf-8")));
	}

	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer("SELECT *FROM t_diary t1,t_diarytype t2 WHERE t1.typeId = t2.diaryId");
		sb.append(" order by t1.releaseDate desc");
		System.out.println(sb.toString());
		
		String psth = "http://localhost:8080/Diary/l";
		System.out.println(psth.indexOf("login"));
	}
}
