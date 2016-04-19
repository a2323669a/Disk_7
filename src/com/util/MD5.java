package com.util;

import com.ndktools.javamd5.Mademd5;

public class MD5 {
	private static Mademd5 md5;
	
	static{
		md5 = new Mademd5();
	}
	
	public static String tomd5(String password){
		return md5.toMd5(password);
	}
}
