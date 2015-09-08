package net.bartzz.pexmanager.util;

public class UtilFormat {
	
	public static boolean parseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
