package org.androidpn.server.xmpp.router;

import org.xmpp.packet.Packet;

public interface Router<T extends Packet> {
    
    
    public void route(T packet);
    
    public Class<?> interest();

    

}
