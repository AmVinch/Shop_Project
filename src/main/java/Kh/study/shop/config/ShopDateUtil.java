package Kh.study.shop.config;

import java.util.Calendar;

public class ShopDateUtil {
	
	//오늘 날짜 구하기 (문자열형태 ToString으로 리턴하는 메소드)
	// static을 사용하면, 
	//많이 호출할 객체들을 굳이 객체만들지않고도 클래스명을 호출하여 사용할 수 있어 편이하다.
	public static String getNowDateToString() {
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int date = cal.get(Calendar.DATE);
		
		String monthStr =  month/10 == 0 ? "0" + month: month + "" ;
		String dateStr =  date/10 == 0 ? "0" + date: date + "" ;
		
		String nowDate = year + "" + monthStr + "" + dateStr;//"20221005"
		
		return nowDate;
	}
	
	
	//오늘날짜를 문자열로 리턴 + 포맷지정 
	//ex) 20221005,2022-10-05, 22/10/05...
	// 사용방법
	// 만약 getNowDateToString("-"); 호출하면, 2022-10-05 형식으로 사용가능하다!
	public static  String getNowDateToString(String format) {
		Calendar cal = Calendar.getInstance();
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int date = cal.get(Calendar.DATE);
		
		String monthStr =  month/10 == 0 ? "0" + month: month + "" ;
		String dateStr =  date/10 == 0 ? "0" + date: date + "" ;
		
		String nowDate = year + format + monthStr + format + dateStr;
		
		return nowDate;
	}
	
	//한달 전 날짜 구하기 (문자열형태 ToString으로 리턴하는 메소드)
		public static String getMonthAgoDateToString(String format) {
			Calendar cal = Calendar.getInstance();
			
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int date = cal.get(Calendar.DATE);
			
			String monthStr =  month/10 == 0 ? "0" + month: month + "" ;
			String dateStr =  date/10 == 0 ? "0" + date: date + "" ;
			
			String nowDate = year + format + monthStr + format + dateStr;//"20221005"
			
			return nowDate;
		}
		
		/*
		 * // 한달전날짜 + 포맷 public static String getMonthAgoDateToString(String format) {
		 * Calendar cal = Calendar.getInstance();
		 * 
		 * int year = cal.get(Calendar.YEAR); int month = cal.get(Calendar.MONTH)+1; int
		 * date = cal.get(Calendar.DATE);
		 * 
		 * String monthStr = month/10 == 0 ? "0" + month: month + "" ; String dateStr =
		 * date/10 == 0 ? "0" + date: date + "" ;
		 * 
		 * String nowDate = year + format + monthStr + format + dateStr;
		 * 
		 * return nowDate; }
		 */
}
