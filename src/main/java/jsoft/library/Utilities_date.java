package jsoft.library;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Utilities_date {
	public static String getDate(String format) {
		DateFormat dateFormat= new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}
	public static String getDateFomat(String d) {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		String formattedDate="";
		try {
			date = inputFormat.parse(d);
			formattedDate = outputFormat.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return formattedDate;
	}
	public static String getDateForJs(String dateString) {
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat inputFormat  = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		String formattedDate="";
		try {
			date = inputFormat.parse(dateString);
			formattedDate = outputFormat.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return formattedDate;
	}
	public static String getDate() {
		return Utilities_date.getDate("dd/MM/yyyy");
	}
	public static String getDateProfiles() {
		return Utilities_date.getDate("ddMMyyHHmmss");
	}

}
