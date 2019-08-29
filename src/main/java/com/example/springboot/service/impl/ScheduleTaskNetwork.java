package com.example.springboot.service.impl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: spring-boot
 * @description: ip控制处理
 * @author: zsm
 * @create: 2019-08-19 17:02
 **/
public class ScheduleTaskNetwork {

    private final static ScheduleTaskNetwork instance = new ScheduleTaskNetwork();

    private Set<String> ips = new HashSet<>();

    /***
     *  可以允许获得已经复制过的ip set
      * @return
     */
    public static Set<String> getIps(){
        return new HashSet<>(instance.ips);
    }

    /**
     * 可以直接判断传入的ip是否存在本地ip中
     * @param ip
     * @return
     */
    public static boolean isExist(String ip){
        return  instance.ips.contains(ip);
    }
    
    private ScheduleTaskNetwork (){
        this.initNetworkInterface();
    }

    private void initNetworkInterface() {
        try {
            this.proceed();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void proceed() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        NetworkInterface networkInterface = null;
        Enumeration<InetAddress> inetAddresses = null;
        InetAddress inetAddress = null;
        while (networkInterfaces.hasMoreElements()){
            networkInterface = networkInterfaces.nextElement();
            inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                inetAddress = inetAddresses.nextElement();
                ips.add(inetAddress.getHostAddress());
            }
        }
    }
}
