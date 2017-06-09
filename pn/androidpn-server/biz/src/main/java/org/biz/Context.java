package org.biz;

public class Context {
    
    private Object content;
    
    private String type;
    
    private String userName;
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
    
    

    public Context(String type, String userName,Object content) {
        super();
        this.content = content;
        this.type = type;
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Context [content=" + content + ", type=" + type + ", userName=" + userName + "]";
    }
    
    
    

}
