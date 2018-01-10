package org.biz;

import org.biz.event.Proccessor;

public class Context {
    
    private Object content;
    
    private String type;
    
    private String from;
    
    private String to;
    
    private Proccessor sendMsgProccessor;
    
    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Context(Object content, String type) {
        super();
        this.content = content;
        this.type = type;
    }
    
    

    public Context(String type, String from,String to ,Object content) {
        super();
        this.content = content;
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    

    @Override
    public String toString() {
        return "Context [content=" + content + ", type=" + type + ", from=" + from + "]";
    }

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Proccessor getSendMsgProccessor() {
		return sendMsgProccessor;
	}

	public void setSendMsgProccessor(Proccessor sendMsgProccessor) {
		this.sendMsgProccessor = sendMsgProccessor;
	}
    
    
    

}
