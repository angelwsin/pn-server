package org.androidpn.server.xmpp.handler;

import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.SessionManager;
import org.xmpp.packet.Message;

public class MessageHandler {
	
	private static final SessionManager sessionManager =  SessionManager.getInstance();
    
    
    public void   handMessage(Message packet){
        
        switch (packet.getType()) {
		case normal:
        case chat:
        	ClientSession session = sessionManager.getSession(packet.getTo().getDomain());
        	session.deliver(packet);
			break;
        case groupchat:
			
			break;
        case headline:
			
			break;

		default:
			break;
		}
    	 
        
    }

}
