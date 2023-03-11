package com.qqclient.service;

import com.qqcommon.Message;
import com.qqcommon.MessageType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread{
    //该线程需要socket
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        //因为Thread在后台一直和服务器通信，因此一直while循环
        while(true){

            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //如果服务器没有发来信息，就会一直阻塞这里：
                Message message = (Message) ois.readObject();
                //判断这个message类型，然后做相应的业务处理
                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    //取出在线列表，并展示
                    String[] onlineUsers = message.getContent().split("  ");
                    System.out.println("=====当前在线人数=====");
                    for(int i=0; i<onlineUsers.length;i++){
                        System.out.println("用户：  " + onlineUsers[i]);
                    }

                }else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)){
                    //接受服务器发来的普通聊天信息，直接展示即可
                    if (message.getGetter() != null){
                        System.out.print("收到" + message.getSender() + "的消息");
                        System.out.println("\n" + message.getSender() + "：" + message.getContent()+"\t\t\t"+message.getSendtime());
                    }   //是单发消息
                    else {
                        System.out.print("收到群聊消息");
                        System.out.println("\n" + message.getSender() + "：" + message.getContent()+"\t\t\t"+message.getSendtime());
                    }
                }else if(message.getMesType().equals(MessageType.MESSAGE_FILE_MESSAGE)){
                    System.out.println("\n"+message.getSender()+" 给 "+message.getGetter()+"发文件："
                            +message.getSrc()+"到我的电脑目录"+message.getDest());
                    //取出message字节数组，通过文件输出流写到磁盘
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    System.out.println("打印message的文件字节" + message.getFileBytes());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("\n 文件报存成功~~");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
