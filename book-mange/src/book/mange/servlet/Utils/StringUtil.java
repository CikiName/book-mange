package book.mange.servlet.Utils;

public class StringUtil {

	/*
	 * �жϵ�ǰ�ַ����Ƿ�Ϊ��
	 */
  
	public static boolean isEmpty(String value) {
		return (value==null)||("".equals(value))||("null".equals(value));
	}
	
	/*
	 * �ж��Ƿ�Ϊ�ո�
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
