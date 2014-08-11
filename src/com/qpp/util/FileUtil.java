package com.qpp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class FileUtil {
	
	/**
	 * 
	 * @param sourceDir
	 * @param targetDir
	 * @throws java.io.IOException
	 */
	public static void copyFile(String sourceDir, String sourceName, String targetDir, String targetDirName) throws IOException{
		(new File(targetDir)).mkdirs();
		File sourceFile = new File(sourceDir + File.separator + sourceName);
		File targetFile = new File(targetDir + File.separator + targetDirName);
		copyFile(sourceFile, targetFile);
	}
	
	/**
	 * copy文件
	 * @param sourceFile
	 * @param targetFile
	 * @throws java.io.IOException
	 */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
    
   	/**
   	 * copy目录
   	 * @param sourceDir
   	 * @param targetDir
   	 * @throws java.io.IOException
   	 */
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }
    public static String ReadFile(String contentPath)  {
        String content = "";
        String str = null;
        BufferedReader br = null;
        try {
            FileInputStream fis = new FileInputStream(contentPath);
            InputStreamReader isr = new InputStreamReader(fis,"utf-8");
            br = new BufferedReader(isr);
            while ((str = br.readLine()) != null) {
                content = content + str + "\n";
            }
        }catch(Exception e){
            System.out.println("File Not found -------------------  "+contentPath);
        }finally {
            try{
                br.close();
            }catch(Exception ee){
            }
        }
        return content;
    }
    public static boolean inputStreamToFile(InputStream ins,File file){
    	try{
     	   OutputStream os = new FileOutputStream(file);
     	   int bytesRead = 0;
     	   byte[] buffer = new byte[8192];
     	   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
     	      os.write(buffer, 0, bytesRead);
     	   }
     	   os.close();
     	   ins.close();
     	   return true;
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }
    public static String getRandomFileName(){
    	java.util.Date dt=new java.util.Date(System.currentTimeMillis());  
        SimpleDateFormat fmt=new   SimpleDateFormat("yyyyMMddHHmmssSSS");  
        String fileName=fmt.format(dt);  
        fileName = fileName +".png";
        return fileName;
    }
    public static void main(String[] args) {
    	try {
			String ret=ReadFile("d:\\temp\\test.txt");
            System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
