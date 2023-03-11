package com.qqclient.service;


import com.qqcommon.Message;
import com.qqcommon.MessageType;

import java.io.*;

/**
 * @author leslie-windark
 * @version 1.0
 *
 * 该类完成文件传输服务
 */
public class FileClientService {
    /**
     *
     * @param src           原文件
     * @param dest          传送到的目录
     * @param senderId      发送者
     * @param getterId      接收者
     */
    public void sendFileToOne(String src, String dest , String senderId, String getterId){
        //读取src文件  -->  message
        Message message = new Message();
        message.setGetter(getterId);
        message.setSender(senderId);
        message.setSrc(src);
        message.setDest(dest);
        message.setMesType(MessageType.MESSAGE_FILE_MESSAGE);       //设置文件类型
        //将需要的文件读取
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int) new File(src).length()];
        // 返回由此抽象路径名表示的文件的长度.

        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);    //将src文件读入到程序字节数组
            //将字节数组放进message里面去
            message.setFileBytes(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭
            if (fileInputStream != null);{
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //提示信息
        System.out.println("\n存入字节数组");

        //发送
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.
                    getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("发送message");

    }
}
