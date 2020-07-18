package com.Cloudandmoon.util;

public class SnGenerateUtil {
	
	public static String generatesn(int clazzId) {
		String sn = "";
		sn = "S"+clazzId +System.currentTimeMillis();
		return sn;
	}
	public static String generateTeachersn(int clazzId) {
		String sn = "";
		sn = "T"+clazzId +System.currentTimeMillis();
		return sn;
	}
	
}
