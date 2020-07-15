package com.project.flik.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utill {

	public static Date stringToUtillDate(String date){
		Date newDate = null;
		try {
			newDate = new SimpleDateFormat("yyyy-mm-dd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}
}
