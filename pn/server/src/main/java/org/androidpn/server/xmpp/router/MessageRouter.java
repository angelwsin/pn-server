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

import java.lang.reflect.Constructor;

import org.androidpn.server.xmpp.handler.MessageHandler;
import org.androidpn.server.xmpp.message.MessageListenner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.biz.Context;
import org.biz.MessageType;
import org.biz.event.Event;
import org.biz.event.Notify;
import org.xmpp.packet.JID;
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
        Notify.addListeneer(new MessageListenner());
    }

    /**
     * Routes the Message packet.
     * 
     * @param packet the packet to route
     */
    public void route(Message packet) {
       // throw new RuntimeException("Please implement this!");
        if(packet.getBody().length()>8){
            String type =  packet.getBody().substring(0, 8);
            String body =  packet.getBody().substring(8); 
            JID jid = packet.getFrom();
            log.info(String.format("%s:%s", type.trim(),body));
            try {
                Constructor<?>  constr = MessageType.getClassByType(type).getConstructor(Context.class);
                Notify.notify((Event)constr.newInstance(new Context(type,jid.getDomain(),body)));
            } catch (Exception e) {
                e.printStackTrace();
            } 
            
        }
        
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
