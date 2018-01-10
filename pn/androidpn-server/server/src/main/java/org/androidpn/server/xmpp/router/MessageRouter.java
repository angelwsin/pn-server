/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.xmpp.router;

import org.androidpn.server.xmpp.handler.MessageHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xmpp.packet.Message;

/** 
 * This class is to route Message packets to their corresponding handler.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class MessageRouter extends AbstractRouter<Message>{
    
    private static final Log log = LogFactory.getLog(MessageRouter.class);

    /**
     * Constucts a packet router.
     */
    
    private MessageHandler messageHandler;

    public MessageRouter() {
        //sessionManager = SessionManager.getInstance();
        messageHandler = new MessageHandler();
    }

    /**
     * Routes the Message packet.
     * 
     * @param packet the packet to route
     */
    public void route(Message packet) {
    	
    	messageHandler.handMessage(packet);
       // throw new RuntimeException("Please implement this!");
    	 /*   String body =  packet.getBody(); 
    	    JSONObject json = JSON.parseObject(body);
    	    String type  = json.getString("type");
    	    if(type==null)
    	    	throw new RuntimeException("Please type this!");
            JID from = packet.getFrom();
            JID to  = packet.getTo();
           // log.info(String.format("%s:%s", type.trim(),packet.getBody()));
            try {
                //Constructor<?>  constr = MessageType.getClassByType(type).getConstructor(Context.class);
                //Notify.notify((Event)constr.newInstance(new Context(type,from.getDomain(),to.getDomain(),body)));
            	Message msg = new Message();
            	msg.setBody(body);
            	msg.setFrom(from);
            	msg.setTo(to);
                SessionManager.getInstance().getSession(to.getDomain()).deliver(msg);
            } catch (Exception e) {
                e.printStackTrace();
            } */
            
        
        /*JID sender = packet.getTo();
        ClientSession session = sessionManager.getSession(sender.getDomain());
        if(session==null){
            log.info(packet);  
        }else{
          session.process(packet);
        }*/
       // session.deliverRawText(packet.toXML());

        
    }
    
    public static void main(String[] args) {
        MessageRouter r = new MessageRouter();
        System.out.println(r.interest());
       

    }


}
