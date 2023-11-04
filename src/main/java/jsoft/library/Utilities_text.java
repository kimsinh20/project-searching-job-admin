package jsoft.library;

public class Utilities_text {

	public static boolean checkValidPass(String pass1,String pass2) {
		if(pass1!=null && pass2!=null ) {
			if(!pass1.equalsIgnoreCase("") && !pass2.equalsIgnoreCase("")) {
				if(pass1.length()>6 && pass1.equals(pass2)) {
					return true;
				}
			}
		}
		return false;
	}
	public static String shortenText(String text, int maxLength) {
	    if (text == null ) {
	        return text;
	    }

	    // Tách văn bản thành các từ
	    String[] words = text.split(" ");

	    // Tạo một StringBuilder để xây dựng văn bản mới
	    StringBuilder shortenedText = new StringBuilder();
	    int count=0;

	    // Lặp qua các từ và chỉ lấy length từ đầu tiên
	    for (int i = 0; i < Math.min(words.length, maxLength); i++) {
	        shortenedText.append(words[i]).append(" ");
	    }

	    // Thêm dấu "..." nếu còn từ
	    if (words.length > maxLength) {
	        shortenedText.append("...");
	    }

	    return shortenedText.toString().trim();
	}
}
