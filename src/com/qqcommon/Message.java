package com.qqcommon;

import java.io.Serializable;

/**
 * @author leslie-windark
 * @version 1.0
 *
 * 用Message对象作用于网络传输
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender;  //发送者
    private String getter;  //接收者
    private String content; //内容
    private String sendtime;//发送时间
    private String mesType; //消息类型   [可以在接口定义消息类型]

    //扩展文件类型
    private byte[] fileBytes ;
    private int fileLength = 0;
    private String dest;    //将文件放在本地的哪里
    private String src;     //发送哪一个文件

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Message(String sender, String getter, String content, String sendtime, String mesType) {
        this.sender = sender;
        this.getter = getter;
        this.content = content;
        this.sendtime = sendtime;
        this.mesType = mesType;
    }

    public Message() {

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

}
