package com.olx.user.service.validation;

public class CharUtil {

    public static boolean containsNumber(String s) {
		boolean contains = false;
	
		if (s != null && !s.isEmpty()) {
		    for (char c : s.toCharArray()) {
				if (contains = Character.isDigit(c)) {
				    break;
				}
		    }
		}
		return contains;
    }

    public static boolean containsUpperCase(String s) {
		boolean contains = false;
	
		if (s != null && !s.isEmpty()) {
		    for (char c : s.toCharArray()) {
				if (contains = Character.isUpperCase(c)) {
				    break;
				}
		    }
		}
		return contains;
    }
    
    public static boolean containsLowerCase(String s) {
    	boolean contains = false;

		if (s != null && !s.isEmpty()) {
		    for (char c : s.toCharArray()) {
				if (contains = Character.isLowerCase(c)) {
				    break;
				}
		    }
		}
		return contains;
	}

    public static boolean containsSpecial(String s) {
		boolean contains = false;
	
		if (s != null && !s.isEmpty()) {
		    for (char c : s.toCharArray()) {
				if (contains = !Character.isLetterOrDigit(c)) {
				    break;
				}
		    }
		}
		return contains;
    }
}