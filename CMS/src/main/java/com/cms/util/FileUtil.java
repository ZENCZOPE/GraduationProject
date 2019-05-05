package com.cms.util;

import java.io.File;

import org.springframework.stereotype.Service;


public class FileUtil {

	/*public boolean checkFile(String path) {
		
	}*/
	
	public static boolean checkExist(String filepath) throws Exception{

	       File file=new File(filepath);

	       if (file.exists()) {
	    	   return true;
	       }else {
	           return false;
	       }
	    }
	

}
