package com.example.lsy.path;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathsUtils {
 
	/**
	 * Paths
	 */
	public static void main(String[] args) {
 
		String filePath = "E:\\tempCSDN\\Paths";
 
		// 创建Path对象的两种方式
 
		// 1.以当前路径创建Path对象
		Path path = Paths.get(filePath);
		// 2
		Path path_02 = Paths.get("E:", "tempCSDN", "Paths");
 
		System.out.println("Path的根路径:" + path.toAbsolutePath().getRoot()); // Path的根路径:E:\
		System.err.println("Path里包含的路径数量:" + path.getNameCount()); // Path里包含的路径数量:2
		System.out.println("Path的绝对路径:" + path.toAbsolutePath()); // Path的绝对路径:E:\tempCSDN\Paths
		System.out.println("Path的绝对路径下包含的路径数量:" + path.toAbsolutePath().getNameCount()); // Path的绝对路径下包含的路径数量:2
		System.out.println("Path的绝对路径下包含的具体路径路径:" + path.toAbsolutePath().getName(0)); // Path的绝对路径下包含的具体路径路径:tempCSDN
		System.out.println("Path的绝对路径下包含的具体路径路径:" + path.toAbsolutePath().getName(1)); // Path的绝对路径下包含的具体路径路径:Paths
		//Path里包含的路径数量:2
		//Path的根路径:E:\
		//Path的绝对路径:E:\tempCSDN\Paths
		//Path的绝对路径下包含的路径数量:2
		//Path的绝对路径下包含的具体路径路径:tempCSDN
		//Path的绝对路径下包含的具体路径路径:Paths
		
	}
}