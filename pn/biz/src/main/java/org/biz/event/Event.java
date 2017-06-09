package org.biz.event;

import org.biz.Context;

public abstract class Event {
    
    
    private Context  content;

    public Event(Context content) {
        super();
        this.content = content;
    }

    public Context getContent() {
        return content;
    }
    
    
    
    
    
    
    

}
