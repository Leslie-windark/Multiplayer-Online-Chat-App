package com.qqclient.View;

import com.qqclient.Utils.Utility;
import com.qqclient.service.FileClientService;
import com.qqclient.service.MessageClientService;
import com.qqclient.service.UserClientService;

import java.io.IOException;

public class QQView {

    private boolean loop = true;    //控制是否显示菜单
    private String key = "";        //接收用户键盘输入
    private UserClientService userClientService = new UserClientService();  //用于注册用户，检查认证
    MessageClientService messageClientService = new MessageClientService(); //用于消息互动
    private FileClientService fileClientService = new FileClientService();  //该对象用于传输文件
//    printStackTracevate
    //显示主菜单
    public static void main(String[] args) throws IOException {
        QQView qqView = new QQView();
        qqView.mainMenu();
    }
    private void mainMenu() throws IOException {
        while (loop){

            System.out.println("===============欢迎登录网络通信系统==============");
            System.out.println("\t\t\t 1 登录系统");
            System.out.println("\t\t\t 9 退出系统");

            System.out.println("请输入：");
            key = Utility.readString(1);

            //根据用户的输入，处理不同的逻辑
            switch (key){
                case "1":
                    System.out.println("请输入用户号");
                    String userId = Utility.readString(50);
                    System.out.println("请输入密 码");
                    String passwd = Utility.readString(20);
                    //现在要去服务器验证用户是否合法
                    //所以在此建一个类，UserClientService
                    if (userClientService.checkUser(userId,passwd)){
                        System.out.println("===============登录成功 欢迎(用户" + userId + "登录成功)==============");
                        //进入二级菜单
                        while (loop){
                            System.out.println("\n===============网络通信系统二级菜单(用户" + userId + ")==============");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.println("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key){
                                case "1":
//                                    System.out.println("显示在线用户列表");
                                    //写一个方法，来获取在线用户列表
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("输入想对大家说的话");
                                    String s = Utility.readString(100);
                                    //调用一个方法，将消息封装成message对象，发给服务器
                                    messageClientService.sendMessageToAll(s,userId);

                                    break;
                                case "3":
                                    System.out.println("请输入想聊天的用户(在线)");
                                    String getterId = Utility.readString(50);
                                    System.out.println("输入内容(300字内)");
                                    String content = Utility.readString(300);
                                    //编写一个方法，将消息发送给服务器端
                                    messageClientService.sendMessageToOne(content,userId,getterId);
                                    break;
                                case "4":
                                    System.out.println("请输入你想吧文件发给的在线用户：");
                                    getterId = Utility.readString(50);
                                    System.out.println("请输入发送文件路径（d\\xx.jpg");
                                    String src = Utility.readString(100);
                                    System.out.println("请输入文件发送到对应的路径");
                                    String dest = Utility.readString(100);
                                    fileClientService.sendFileToOne(src,dest,userId,getterId);
                                    break;
                                case "9":
                                    //调用一个方法，给服务器端发送一个退出的Message
                                    userClientService.shutdown();
                                    loop = false;
                                    break;
                            }
                        }
                    }else {
                        System.out.println("==============登录失败=============");
                    }
                    break;
                case "9":
                    loop = false;
            }

        }
    }
}
