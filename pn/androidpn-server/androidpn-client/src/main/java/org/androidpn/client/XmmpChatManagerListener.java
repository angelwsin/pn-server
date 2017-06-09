package org.androidpn.client;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;

public class XmmpChatManagerListener implements ChatManagerListener{

    @Override
    public void chatCreated(Chat chat, boolean arg1) {
        chat.addMessageListener(new XmmpMessageListener());
    }

}
