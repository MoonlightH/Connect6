package com.jp.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 把源代码的编码格式从GBK转换为UTF-8
 * @author 蒋鹏
 */
public class CovertGBKToUTF8 {
	
	public static ArrayList<File> files=new ArrayList<>();

	public static void main(String[] args) {
		String srcDir=System.getProperty("user.dir")+"\\src";
		CovertGBKToUTF8.findSourceFiles(srcDir);
		for (File sfile : CovertGBKToUTF8.files) {
			File dfile=new File(sfile.getPath()+"d");
			try {
				OutputStream out=new FileOutputStream(dfile);
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
				InputStream in=new FileInputStream(sfile.getPath());
				BufferedReader br=new BufferedReader(new InputStreamReader(in,"GBK"));
				String temp;
				while((temp=br.readLine())!=null){
					bw.write(temp);
					bw.newLine();
					bw.flush();
				}
				in.close();
				out.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(sfile.delete()){
				dfile.renameTo(sfile);
			}
		}
		System.out.println("转码结束");
	}
	
	/**
	 * 递归出所有src下的文件
	 */
	public static void findSourceFiles(String srcDir){
		File file=new File(srcDir);
		File[] filePathes=file.listFiles();
		for (File f : filePathes) {
			if(f.isDirectory()){
				findSourceFiles(f.getPath());
			}else{
				files.add(f);
			}
		}
	}

}
