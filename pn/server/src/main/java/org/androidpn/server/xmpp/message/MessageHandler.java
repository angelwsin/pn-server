package org.androidpn.server.xmpp.message;

import java.util.concurrent.ConcurrentHashMap;

import org.biz.Context;
import org.biz.MessageType;
import org.biz.event.Event;
import org.biz.event.EventHandler;
import org.biz.event.Proccessor;
import org.biz.event.TaskManager;
import org.biz.event.TaskManager.ProccessTask;

public class MessageHandler implements EventHandler{
    

    TaskManager taskManager = new TaskManager();
    
    private static ConcurrentHashMap<String, Proccessor> processors = new ConcurrentHashMap<String, Proccessor>();
    
    static{
        processors.put(MessageType.send.getType(), new SendMessageProcessor());
    }

    @Override
    public void handler(Event event) {
        Context context =  event.getContent();
        taskManager.addTask(new ProccessTask(context,processors.get(context.getType())));
    }

    

}
