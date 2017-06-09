package org.androidpn.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class XmmpMessageListener implements MessageListener{
    
    protected final Log log = LogFactory.getLog(getClass());

    @Override
    public void processMessage(Chat chat, Message message) {
         log.info(message.getBody());
    }

   

}
