package book.mange.servlet.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private static Logger logger=LoggerFactory.getLogger(DateUtil.class);
	
	/*
	 * 获取当前系统日期
	 */
	public String  getDate() {
		SimpleDateFormat  sdf;
		
		return (sdf=new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
	}
	
   /*
    * 获取当前系统时间
    */
	public String getTime() {
		SimpleDateFormat sdf;
		return (sdf=new SimpleDateFormat("HH:mm:ss")).format(new Date());
	}

	/*
	 * 将字符串日期格式化Date格式
	 */
	
	public static Date parseDate(String dateStr) {
		 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		return parseDate(dateStr,format);
	}
	
	public static Date parseDate(String dateStr, SimpleDateFormat format) {
		if(""==dateStr||null==dateStr) {
			return null;
		}
		Date date=null;
		
		try {
			date=format.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		logger.error("error",e);
		}
		return date;
	}
	
	
	
}
