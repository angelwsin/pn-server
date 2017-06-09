package org.biz.event;

import java.util.concurrent.ConcurrentHashMap;

import org.biz.Context;
import org.biz.MessageType;
import org.biz.event.TaskManager.ProccessTask;

public class GroupEventHandler implements EventHandler{
    
    
    TaskManager taskManager = new TaskManager();
    
    private static ConcurrentHashMap<String, Proccessor> processors = new ConcurrentHashMap<String, Proccessor>();
    
    static{
        processors.put(MessageType.local.getType(), new GroupLocalProcessor());
    }

    @Override
    public void handler(Event event) {
        Context context =  event.getContent();
        taskManager.addTask(new ProccessTask(context,processors.get(context.getType())));
    }

}
