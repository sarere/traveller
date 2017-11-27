package com.dimilionux.traveller;

/**
 * Created by sarere on 11/24/17.
 */

public class User {
    public String userName, timeChat, previewMessage;

    public User(String namePlace, String timeChat, String previewMessage){
        this.userName = namePlace;
        this.timeChat = timeChat;
        this.previewMessage = previewMessage;
    }
}
