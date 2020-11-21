package com.example.forsolo.chat;

public class MessageItem {

    String name;
    String message;
    String time;
    String pofileUrl;
    String roomCode;

    public MessageItem(String name, String message, String time, String pofileUrl, String receiver) {
        this.name = name;
        this.message = message;
        this.time = time;
        this.pofileUrl = pofileUrl;
        this.roomCode = receiver;
    }

    //firebase DB에 객체로 값을 읽어올 때..
    //파라미터가 비어있는 생성자가 핑요함.
    public MessageItem() {
    }

    //Getter & Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPofileUrl() {
        return pofileUrl;
    }

    public void setPofileUrl(String pofileUrl) {
        this.pofileUrl = pofileUrl;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    @Override
    public String toString() {
        return "MessageItem{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                ", pofileUrl='" + pofileUrl + '\'' +
                ", roomName='" + roomCode + '\'' +
                '}';
    }
}

