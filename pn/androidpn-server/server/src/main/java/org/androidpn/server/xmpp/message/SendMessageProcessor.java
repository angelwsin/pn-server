package org.androidpn.server.xmpp.message;

import org.androidpn.server.xmpp.session.SessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.biz.Context;
import org.biz.event.GroupLocalProcessor;
import org.biz.event.Proccessor;

public class SendMessageProcessor implements Proccessor{

    private static final Log log = LogFactory.getLog(GroupLocalProcessor.class);
    SessionManager sessionManager = SessionManager.getInstance();
    @Override
    public void proccess(Object content) {
        log.info(content); 
        Context context = (Context) content;
        // 等到
       /* Group group = GroupUserManager.getLineUser(context.getUserName());
        // 计算 最近的人
        
        Message msg = new Message();
        msg.setBody((String)context.getContent());
        msg.setFrom(context.getFrom());
        String[] s = new String[]{"haha"};
        for(String st: s){
            log.info(st);
            msg.setTo(st);
            ClientSession client =  sessionManager.getSession(st);
            if(client!=null)
            client.deliver(msg); 
        }*/
        
        
    }

}
