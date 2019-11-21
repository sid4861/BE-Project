package com.example.abc.homeautomation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validation {


	public static boolean isValidEmail(String email) {
		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Boolean b = email.matches(emailreg);

		return b;

	}
	// validate mobile
	public static boolean isMobNo(String str) {
		Pattern mobNO = Pattern.compile("^\\d{10,10}$");
		Matcher matcher = mobNO.matcher(str);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}
	// validate website
	public static boolean iswebsite(String str) {
		Pattern p = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");

		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	// validate number
	public static boolean isnumber(String str) {
		Pattern pattern = Pattern.compile(".*[^0-9].*");


		if (!pattern.matcher(str).matches()) {
			return true;
		} else {
			return false;
		}
	}
	// validate text
	public static boolean isText(String str) {
		if (str.matches("[a-zA-Z]+$")) {
			return true;
		}else {
			return false;
		}

	}
	// validate alphanumeric
	public static boolean isalphanumeric(String str) {
		if(str.matches("[a-zA-Z0-9]+")){
			return true;
		}else {
			return false;
		}

	}
	public static boolean isNAme(String str) {
		if(str.matches("^[a-zA-Z_ ]*$")){
			return true;
		}else {
			return false;
		}

	}
}
