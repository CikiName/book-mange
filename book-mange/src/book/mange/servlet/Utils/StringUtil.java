package book.mange.servlet.Utils;

public class StringUtil {

	/*
	 * 判断当前字符串是否为空
	 */
  
	public static boolean isEmpty(String value) {
		return (value==null)||("".equals(value))||("null".equals(value));
	}
	
	/*
	 * 判断是否为空格
	 */
	public static boolean isBlank(String value) {
		int strlen;
		if((value==null)||((strlen=value.length())==0)) {
			return true;
		}
		for(int i=0;i<value.length();i++) {
			if(!Character.isWhitespace(value.charAt(i))) {
				return false;
			}
			
		}
		return true;
	}
	
	
	public static boolean isEmpty(Object[] array) {
		return (array==null)||(array.length==0);
	}
	
	
}
