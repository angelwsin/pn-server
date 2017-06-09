package org.androidpn.server.xmpp.message;

import java.util.concurrent.ConcurrentHashMap;

import org.biz.event.Event;
import org.biz.event.EventHandler;
import org.biz.event.EventListeneer;
import org.biz.event.MessageEvent;

public class MessageListenner implements EventListeneer{
    
 private  static  ConcurrentHashMap<Class<?>, EventHandler> handlers = new ConcurrentHashMap<Class<?>, EventHandler>();
    
    
    static{
        handlers.put(MessageEvent.class, new MessageHandler());
    }

    @Override
    public void listeneer(Event event) {
        if(handlers.get(event.getClass())==null) return ;
        handlers.get(event.getClass()).handler(event);
    }

}
