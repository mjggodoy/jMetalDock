package es.uma.khaos.docking_service.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Utils {
	
	// TODO: Tratar o elevar excepciones
	
	public static void saveFile(InputStream inputStream, String serverLocation) {
	    try {
	        OutputStream outputStream = new FileOutputStream(new File(serverLocation));
	        int read = 0;
	        byte[] bytes = new byte[1024];
	        outputStream = new FileOutputStream(new File(serverLocation));
	        while ((read = inputStream.read(bytes)) != -1) {
	            outputStream.write(bytes, 0, read);
	        }
	        outputStream.flush();
	        outputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void unzip(String sourceFile, String destination){
	    try {
	    	ZipFile zipFile = new ZipFile(sourceFile);
	        zipFile.extractAll(destination);
	    } catch (ZipException e) {
	        e.printStackTrace();
	    }
	}
	
	public static List<String> searchFileWithExtension (String dir, String extension) {
		List <String> list = new ArrayList<>();
		File f = new File(dir);
		if (f.exists()) { // Directorio existe }
			File[] ficheros = f.listFiles();
			for (int x=0; x<ficheros.length; x++) {
				String fichero = ficheros[x].getName();
				if (fichero.indexOf(extension)!=-1) {
					list.add(fichero);
				}
			}
		}
		return list;
	}
	
	
	//delete folder
	public static void deleteFolder(String workDir){
    	File directory = new File(workDir);
    	try{
    		if(!directory.exists()){
    			System.out.println("The directory already exists");
    		}else{
    			deleteFile(directory);	
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	} 
    }
	
	
	public static void deleteFile(File file){
    	if(file.isDirectory()){
    		if(file.list().length==0){	
    			file.delete(); // si está vacío el directorio, elimina el directorio	
    		}else{
    			String files[] = file.list();
    			for (String temp : files) {
    				File fileDelete = new File(file, temp);
    				deleteFile(fileDelete);
    			}
    			if(file.list().length==0){
              	     file.delete();
    			}
    		}
    	}else{
    		file.delete(); //se borra	
    		System.out.println("File is deleted : " + file.getAbsolutePath());
    	}	
	}

}
