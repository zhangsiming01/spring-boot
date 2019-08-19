package com.example.springboot.common.utis.jvm;


import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;


/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年12月21日 上午11:22:38 
* 类说明  获取操作系统内存空间 
 * JAVA获取JVM内存空间和物理内存空间
 */
@SuppressWarnings("restriction")
public class MonitorInfoTest {
	  public static void main(String[] args) {
		//虚拟机内存情况查询
		long vmFree = 0;
		long vmUse = 0;
		long vmTotal = 0;
		long vmMax = 0;
		int byteToMb = 1024*1204;
		Runtime rt = Runtime.getRuntime();
		//byteToMb;
		vmTotal = rt.totalMemory();
		//byteToMb
		vmFree = rt.freeMemory();
		//byteToMb
		vmMax = rt.maxMemory();
		vmUse = vmTotal-vmFree;
		System.out.println("jvm内存已用的空间为："+vmUse+"MB");
		System.out.println("jvm内存的空闲空间为："+vmFree+"MB");
		System.out.println("jvm总内存空间为："+vmTotal+"MB");
		System.out.println("jvm总内存空间为："+vmMax+"MB");
		System.out.println("=========================================================");
		//操作系统及内存情况查询
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
		String os = System.getProperty("os.name");

		long physicalFree = osmxb.getFreePhysicalMemorySize(); // byteToMb
		long physicalTotal = osmxb.getTotalPhysicalMemorySize();//byteToMb
		long physicalUse = physicalTotal-physicalFree;
		System.out.println("操作系统的版本：" + os);
		System.out.println("操作系统物理内存已用的空间为：" + physicalFree + " MB");
		System.out.println("操作系统物理内存的空闲空间为：" + physicalUse + " MB");
		System.out.println("操作系统总物理内存：" + physicalTotal + " MB");
		
		//获取线程总数
		ThreadGroup parentThread;
		int totalThread = 0;
		for(parentThread = Thread.currentThread().getThreadGroup();parentThread.getParent()!=null; parentThread = parentThread.getParent()) {
			totalThread = parentThread.activeCount();
		}
		System.out.println("获得线程总数"+totalThread);
	  }
}
