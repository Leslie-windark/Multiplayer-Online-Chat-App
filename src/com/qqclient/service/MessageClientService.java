package com.qqclient.service;

import com.qqcommon.Message;
import com.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author leslie-windark
 * @version 1.0
 *
 * 该类用于提供消息相关的服务方法
 */
public class MessageClientService {
    /**
     *
     * @param content       内容
     * @param senderId      发送用户
     * @param getterId      接受用户
     */
    //编写一个方法，通过服务器向用户发送消息
    public void sendMessageToOne(String content, String senderId, String getterId){
        Message message = new Message();
        message.setSender(senderId);
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendtime(new Date().toString());     //发送时间
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(senderId + " 对 " + getterId + " 说 " + content);

        //发送给服务器端
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.
                    getClientConnectServerThread(senderId).getSocket().getOutputStream());//senderId --因为拿取的是本地创建成功的socket
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessageToAll(String content, String senderId){
        Message message = new Message();
        message.setContent(content);        //内容
        message.setSender(senderId);      //发送自己
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        message.setSendtime(ft.format(date));     //发送时间
        System.out.println(senderId + " 对大家说 " + content + "\t\t\t" + ft.format(date));   //在客户端打印发送内容及时间

        //发送给服务器端
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.
                    getClientConnectServerThread(senderId).getSocket().getOutputStream());//senderId --因为拿取的是本地创建成功的socket
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
