package org.androidpn.client.task;

import org.androidpn.client.XmppManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.xmpp.packet.Message.Type;

import com.alibaba.fastjson.JSON;

public class MessageTask<T> implements Runnable {
	
	protected final Log log = LogFactory.getLog(getClass());

    private final XmppManager xmppManager;
    
    private final  T           data;
    
    private final  String      to;

    public MessageTask(XmppManager xmppManager,T data,String to) {
        this.xmppManager = xmppManager;
        this.data = data;
        this.to   = to;
    }
    
    public MessageTask(XmppManager xmppManager,T data,String to,Type type) {
        this.xmppManager = xmppManager;
        this.data = data;
        this.to   = to;
    }


    public void run() {
        log.debug( "MsgTask.run()...");

        if (xmppManager.isConnected()) {
        	XMPPConnection  conn = xmppManager.getConnection();
            log.debug( "username=" + conn.getUser());
          try{
            Message msg = new Message();
            msg.setBody(JSON.toJSONString(data));
            msg.setFrom(conn.getUser());
            msg.setTo(to);
            xmppManager.getConnection().sendPacket(msg);

            } catch (Exception e) {
                log.debug( "LoginTask.run()... other error");
                log.debug( "Failed to login to xmpp server. Caused by: "
                        + e.getMessage());
                xmppManager.startReconnectionThread();
            }

        } else {
            log.debug("Logged in already");
        }

    }
}
