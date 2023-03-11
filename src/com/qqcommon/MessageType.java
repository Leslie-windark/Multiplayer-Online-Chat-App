package com.qqcommon;

/**
 * @author leslie-windark
 * @version 1.0
 */
public interface MessageType  {
    //1.在接口中定义一些常量
    //2.不同常量的值表示不同消息类型

    String MESSAGE_LOGGIN_SUCCEED = "1";    //表示登录成功
    String MESSAGE_LOGIN_FAIL = "2";        //表示登录失败
    String MESSAGE_COMM_MES = "3";                  //普通信息包
    String MESSAGE_GET_ONLINE_FRIEND = "4";         //要求返回在线用户列表
    String MESSAGE_RET_ONLINE_FRIEND = "5";         //返回在线用户列表
    String MESSAGE_CLIENT_EXIT = "6";               //客户端退出
    String MESSAGE_FILE_MESSAGE = "7";               //文件消息
}
