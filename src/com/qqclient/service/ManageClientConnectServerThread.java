package com.qqclient.service;

import java.util.HashMap;

/**
 * 该类管理客户端到服务器端的线程
 */
public class ManageClientConnectServerThread {
    //我们把多个线程放进一个HashMap集合，key -- 用户ID，value -- 线程
    private static HashMap<String,ClientConnectServerThread> hm = new HashMap<>();

    //将线程放进集合
    public static void addClientConnectServerThread(String userId,ClientConnectServerThread ccst){
        hm.put(userId,ccst);
    }
    //通过userId查到对应线程
    public static ClientConnectServerThread getClientConnectServerThread(String userId){
        ClientConnectServerThread ccst =  hm.get(userId);
        return ccst;
    }
}
