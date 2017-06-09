package org.biz.event;

import java.util.concurrent.ConcurrentHashMap;

public class GroupEventListeneer implements EventListeneer{
    
    
    private  static  ConcurrentHashMap<Class<?>, EventHandler> handlers = new ConcurrentHashMap<Class<?>, EventHandler>();
    
    
    static{
        handlers.put(GroupEvent.class, new GroupEventHandler());
    }

    @Override
    public void listeneer(Event event) {
        if(handlers.get(event.getClass())==null) return ;
        handlers.get(event.getClass()).handler(event); 
    }

}
