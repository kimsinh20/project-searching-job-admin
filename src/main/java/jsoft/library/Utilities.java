package jsoft.library;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.htmlparser.jericho.CharacterReference;


public class Utilities {
	public static byte getByteParam(ServletRequest request,String name) {
		byte value = -1;
		String str_value = request.getParameter(name);
		if(str_value!=null && !str_value.equalsIgnoreCase("")) {
			value = Byte.parseByte(str_value);
		}
		return value;
	}
	public static short getShortParam(ServletRequest request,String name) {
		short value = -1;
		String str_value = request.getParameter(name);
		if(str_value!=null && !str_value.equalsIgnoreCase("")) {
			value = Short.parseShort(str_value);
		}
		return value;
	}
	public static int getIntParam(ServletRequest request,String name) {
		int value = -1;
		String str_value = request.getParameter(name);
		if(str_value!=null && !str_value.equalsIgnoreCase("")) {
			value = Integer.parseInt(str_value);
		}
		return value;
	}
	public static String encode(String str_unicode) {
		return CharacterReference.encode(str_unicode);
	}
	public static String decode(String str_html) {
		return CharacterReference.decode(str_html);
	}
	public static boolean saveFile(InputStream file, String filePath) {
//      System.out.println("file:"+filePath);
	    try {
	        File newFile = new File(filePath);
	        boolean fileCreated = true;

	        if (!newFile.exists()) {
	            fileCreated = newFile.createNewFile();
	        }

	        if (fileCreated) {
	            FileOutputStream outputStream = new FileOutputStream(newFile);
	            int read = 0;
	            byte[] bytes = new byte[1024];

	            while ((read = file.read(bytes)) != -1) {
	                outputStream.write(bytes, 0, read);
	            }

	            outputStream.flush();
	            outputStream.close();
	            return true; // Trả về true nếu ghi thành công
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return false; // Trả về false nếu ghi không thành công hoặc có lỗi
	}
	public static boolean export(String path) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
	    sheet.createRow(0);
	    sheet.getRow(0).createCell(0).setCellValue("abc");
	    sheet.getRow(0).createCell(1).setCellValue("2");
	    File file = new File("D:\\test3.xls");
	    try {
	    	System.out.println("dit me đúng");
			workbook.write(file);
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("dit me sai");
			e.printStackTrace();
		}
		return false;
	}
}
