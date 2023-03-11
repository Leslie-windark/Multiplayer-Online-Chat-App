package com.qqclient.service;

import com.qqcommon.Message;
import com.qqcommon.MessageType;
import com.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author leslie-windark
 * @version 1.0
 * <p>
 * 该类完成用户登录验证和注册等功能
 */

public class UserClientService {
    //static String IPDestination = "";   //填入目标IP地址
    //因为我们可能再其他地方使用user信息，因此做出成员属性
    private User u = new User();
    //因为Socket在其他地方也会使用，所以作出属性
    Socket socket;

    //根据useId 和 passwd 到服务器验证用户是否合法
    public boolean checkUser(String userId, String pwd) {
        boolean state = false;
        //创建User对象
        u.setUserId(userId);
        u.setPasswd(pwd);

        try {
            socket = new Socket(InetAddress.getLocalHost(), 9999);
            //得到ObjectOutputStream对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);     //发送user对象
            //读取从服务端回复的Message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) ois.readObject();

            if (message.getMesType().equals(MessageType.MESSAGE_LOGGIN_SUCCEED)) {
                //创建一个和服务器保持通信的线程 -> 创建一个ClientConnectServerThread
                ClientConnectServerThread ccs = new ClientConnectServerThread(socket);
                //启动客户端线程
                ccs.start();
                //这里为了后面客户端的扩展，将线程放入集合管理
                ManageClientConnectServerThread.addClientConnectServerThread(userId, ccs);
                state = true;   //返回判断
            } else {
                //如果登录失败，就不能启动与服务器的通信，关闭socket
                socket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return state;
    }

    //向服务器端请求在线用户列表
    public void onlineFriendList() {

        //发送一个Message，类型为 MESSAGE_GET_ONLINE_FRIEND
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId());       //向服务器发送请求，并附带ID

        //发送给服务器
        try {
            //得到当前线程的Socket 对应的ObjectOutputStream
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.
                            getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);   //向服务器端发送一个message，请求全部在线人数
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //编写方法，退出客户端，并给服务端发一个退出系统的message
    public void shutdown() {
        //因为与服务器端的通信的进程还在运行，所以关闭thread，并且发送一个message，叫他关闭socket和进程
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());

        //发送message
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.
                            getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println(u.getUserId() + "退出");
            System.exit(0);     //结束线程
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
