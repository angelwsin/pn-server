package org.biz;

import org.biz.event.DefaultEvent;
import org.biz.event.GroupEvent;
import org.biz.event.MessageEvent;

public enum MessageType {
    
    local("local   ",GroupEvent.class),
    send("send    ",MessageEvent.class);
    
    
    MessageType(String type,Class<?> eventClass){
       this.type = type; 
       this.eventClass = eventClass;
    }

    private String type;
    private Class<?> eventClass;

    public String getType() {
        return type;
    }

    public Class<?> getEventClass() {
        return eventClass;
    }
    
    public static Class<?> getClassByType(String type){
           for(MessageType msgType : values()){
               if(msgType.type.equals(type)){
                   return msgType.eventClass;
               }
           }
           return DefaultEvent.class;
    }
    
    
}
