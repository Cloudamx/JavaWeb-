package com.Cloudandmoon.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
	public static String getFormantDate(Date date, String string) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		return dateFormat.format(date);
	}
	
	
}
