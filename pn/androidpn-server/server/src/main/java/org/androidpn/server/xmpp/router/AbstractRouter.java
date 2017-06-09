package org.androidpn.server.xmpp.router;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.androidpn.server.xmpp.session.SessionManager;
import org.xmpp.packet.Packet;

public abstract class AbstractRouter<T extends Packet> implements Router<T>{
    
    
    protected  SessionManager sessionManager = SessionManager.getInstance();
    
    
    
    @Override
    public Class<?> interest() {
        Type type =   getClass().getGenericSuperclass();
         ParameterizedType  d = (ParameterizedType) type;
         return  (Class<?>) d.getActualTypeArguments()[0];

    }
    
    

}
