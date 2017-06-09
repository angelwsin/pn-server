package org.biz.event;

import java.util.ArrayList;
import java.util.List;

public class Notify {
    
    private  static  List<EventListeneer> listenners = new ArrayList<EventListeneer>();
    
    static{
        listenners.add(new GroupEventListeneer());
    }
    
    public static void notify(Event event){
        for(EventListeneer listenner :listenners){
             listenner.listeneer(event);
        }
    }
    
    public static void addListeneer(EventListeneer listenner){
        listenners.add(listenner);
    }

}
