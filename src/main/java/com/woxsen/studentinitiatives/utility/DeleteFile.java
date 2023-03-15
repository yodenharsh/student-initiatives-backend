package com.woxsen.studentinitiatives.utility;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFile {
	
	public static void deleteFile(String fileName, String path, String[] extensions) {
		
		Path filePath = Paths.get(path);
		
		for(String ext: extensions) {
			
			Path filePathObj = filePath.resolve(fileName + ext);
			System.out.println(filePathObj);
	        if (Files.exists(filePathObj) && Files.isRegularFile(filePathObj)) {
	            // Delete the file and print a message
	            try {
	                Files.delete(filePathObj);
	                System.out.println("File deleted successfully.");
	            } catch (Exception e) {
	                System.out.println("Failed to delete the file.");
	            }
	            return;
	        }
		}
	}
}
