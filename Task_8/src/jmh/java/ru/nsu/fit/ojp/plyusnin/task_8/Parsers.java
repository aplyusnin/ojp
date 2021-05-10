package ru.nsu.fit.ojp.plyusnin.task_8;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parsers {

	public static boolean parseInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean parseCharCheck(String str) {
		for (char ch : str.toCharArray()) {
			if(!Character.isDigit(ch)) return false;
		}
		return true;
	}

	private static Pattern pat = Pattern.compile("^\\d+$");

	public static boolean parseRegex(String str){
		Matcher matcher = pat.matcher(str);
		return matcher.matches();
	}


}